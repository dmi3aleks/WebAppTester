package com.test.api;

import com.test.api.model.Order;
import com.test.common.Server;
import com.test.util.Generator;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderEntryTest {


    @Before
    public void setup() {
        RestAssured.baseURI = Server.getHTTPEndpoint().getHost();
        RestAssured.port = Server.getHTTPEndpoint().getPort();
    }

    @Test
    public void newOrderCreation() {

        final Double orderPrice = Generator.getOrderPrice();
        Order order = new Order("","6758.T", "S", 1000., orderPrice, "Auto Test", "A");

        // place a new order
        given().contentType("application/json").body(order).when().post("/order/add").then().statusCode(200);

        // verify that resulting order has been added to the order list
        Order[] orders = given().when().get("/order").as(Order[].class);

        assertTrue(orders.length > 0);
        Order lastOrder = orders[0];
        assertEquals(orderPrice, lastOrder.getPrice());
    }

}
