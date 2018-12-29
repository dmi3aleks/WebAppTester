package com.test.api;

import com.test.api.model.Order;
import com.test.api.model.Trade;
import com.test.common.Server;
import com.test.util.Generator;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExecutionTest {

    @Before
    public void setup() {
        RestAssured.baseURI = Server.getHTTPEndpoint().getHost();
        RestAssured.port = Server.getHTTPEndpoint().getPort();
    }

    private void cancelOutstandingOrders(final String instrumentCode) {
        // cancel all outstanding orders for a given instrument
        List<Order> outstandingOrders = Arrays.asList(given().when().get("/api/order").as(Order[].class));
        List<Order> relevantOrders = outstandingOrders.stream().
                filter(ord -> ord.getInstCode().equals(instrumentCode) && ord.getStatus().equals("A")).
                collect(Collectors.toList());
        relevantOrders.forEach(order -> {
            given().contentType("application/json").body(order).when().post("/api/order/delete").then().statusCode(200);
        });
    }

    @Test
    public void tradeBetweenTwoOrders() {

        final String instrumentCode = "6758.T";

        final String[] sides = {"S", "B"};

        cancelOutstandingOrders(instrumentCode);

        final long maxIterations = 8;
        final long maxRateIterSec = 5;

        final long minTimeBetweenIterationsMS = 1000/maxRateIterSec;
        final long maxItemsToFetch = 1;
        long previousIterationTime = 0;

        for(long i = 0; i < maxIterations; i++) {

            long currentIterationTime = new Date().getTime();
            if (currentIterationTime - previousIterationTime < minTimeBetweenIterationsMS) {
                try {
                    Thread.sleep(previousIterationTime + minTimeBetweenIterationsMS - currentIterationTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            previousIterationTime = new Date().getTime();

            List<Order> orders = new LinkedList<>();
            final Double orderPrice = Generator.getOrderPrice();

            // place a pair of matching orders
            for (final String side : sides) {
                Order order = new Order("", instrumentCode, side, 1000., orderPrice, "Auto Test", "A");

                // place a new order
                Integer orderId =
                        given().
                                contentType("application/json").body(order)
                                .when().
                                post("/api/order/add")
                                .then()
                                .statusCode(200)
                                .extract().path("OrderID");

                // verify that resulting order has been added to the order list
                Order[] registeredOrders = given().when().get(String.format("/api/order?limit=%d", maxItemsToFetch)).as(Order[].class);
                assertTrue(registeredOrders.length > 0);
                Order lastOrder = registeredOrders[0];
                assertEquals(lastOrder.getOrderID(), String.valueOf(orderId));
                assertEquals(lastOrder.getPrice(), orderPrice);
                orders.add(lastOrder);
            }

            final Order firstOrder = orders.get(0);
            final Order secondOrder = orders.get(1);

            // verify that orders got matched
            Trade[] trades = given().when().get(String.format("/api/trade", maxItemsToFetch)).as(Trade[].class);
            assertTrue(trades.length > 0);
            Trade lastTrade = trades[0];
            assertEquals(firstOrder.getOrderID(), lastTrade.getRestingOrderID());
            assertEquals(secondOrder.getOrderID(), lastTrade.getIncomingOrderID());

            assertEquals(lastTrade.getPrice(), firstOrder.getPrice());
            assertEquals(lastTrade.getQuantity(), firstOrder.getQuantity());
        }
    }

}
