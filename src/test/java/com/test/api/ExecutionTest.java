package com.test.api;

import com.test.api.model.Order;
import com.test.api.model.Trade;
import com.test.common.Server;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExecutionTest {

    static String getCurrentTimestamp() {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    @Before
    public void setup() {
        RestAssured.baseURI = Server.getHTTPEndpoint().getHost();
        RestAssured.port = Server.getHTTPEndpoint().getPort();
    }

    @Test
    public void trade_bw_two_orders() {

        final String instrumentCode = "6702.T";

        final String[] sides = {"S", "B"};
        List<Order> orders = new LinkedList<>();

        // place a pair of matching orders
        for(final String side: sides) {
            final String orderNotes = getCurrentTimestamp();
            Order order = new Order("", instrumentCode, side, 1000., 150., orderNotes);
            given().contentType("application/json").body(order).when().post("/order/add").then().statusCode(200);
            // verify that resulting order has been added to the order list
            Order[] registeredOrders = given().when().get("/order").as(Order[].class);
            assertTrue(registeredOrders.length > 0);
            Order lastOrder = registeredOrders[registeredOrders.length - 1];
            assertEquals(lastOrder.getNotes(), orderNotes);
            orders.add(lastOrder);
        }

        final Order firstOrder = orders.get(0);
        final Order secondOrder = orders.get(1);

        // verify that orders got matched
        Trade[] trades = given().when().get("/trade").as(Trade[].class);
        assertTrue(trades.length > 0);
        Trade lastTrade = trades[trades.length - 1];
        assertEquals(lastTrade.getRestingOrderID(), firstOrder.getOrderID());
        assertEquals(lastTrade.getIncomingOrderID(), secondOrder.getOrderID());

        assertEquals(lastTrade.getPrice(), firstOrder.getPrice());
        assertEquals(lastTrade.getQuantity(), firstOrder.getQuantity());
    }

}
