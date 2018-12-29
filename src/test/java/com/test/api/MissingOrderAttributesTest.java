package com.test.api;

import com.test.api.model.Order;
import com.test.common.Server;
import com.test.util.Generator;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class MissingOrderAttributesTest {


    @Before
    public void setup() {
        RestAssured.baseURI = Server.getHTTPEndpoint().getHost();
        RestAssured.port = Server.getHTTPEndpoint().getPort();
    }

    @Test
    public void newOrderWithMissingPrice() {

        Order order = new Order("","6758.T", "S", 1000., null, "Auto Test", "A");

        // place a new order with price attribute missing
        Integer message =
                given().
                    contentType("application/json").body(order)
                .when().
                    post("/api/order/add")
                .then()
                    .statusCode(400)
                    .extract().path("message");

        assertEquals(message, "Price is missing");
    }

    @Test
    public void newOrderWithMissingQuantity() {

        final Double orderPrice = Generator.getOrderPrice();
        Order order = new Order("","6758.T", "S", null, orderPrice, "Auto Test", "A");

        // place a new order with price attribute missing
        Integer message =
                given().
                        contentType("application/json").body(order)
                        .when().
                        post("/api/order/add")
                        .then()
                        .statusCode(400)
                        .extract().path("message");

        assertEquals(message, "Quantity is missing");
    }

}
