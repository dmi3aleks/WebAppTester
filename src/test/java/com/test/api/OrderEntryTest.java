package com.test.api;

import com.test.api.model.Order;
import com.test.common.Server;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderEntryTest {

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
    public void new_order_creation() {

        final String orderNotes = getCurrentTimestamp();

        Order order = new Order("","6758.T", "S", 1000., 150., orderNotes);

        // place a new order
        given().contentType("application/json").body(order).when().post("/order/add").then().statusCode(200);

        // verify that resulting order has been added to the order list

        Order[] orders = given().when().get("/order").as(Order[].class);

        assertTrue(orders.length > 0);
        Order lastOrder = orders[orders.length - 1];
        assertEquals(lastOrder.getNotes(), orderNotes);
    }

}
