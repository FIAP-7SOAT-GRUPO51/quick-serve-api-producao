package br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.api.controller;

import br.com.fiap.tech_challenge.quick_serve_api_producao.adapter.framework.spring.model.Order;
import br.com.fiap.tech_challenge.quick_serve_api_producao.domain.model.OrderStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    void testListOrders() {
        given()
                .contentType(ContentType.JSON)
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/v1/order")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", notNullValue())
                .body("content.size()", greaterThanOrEqualTo(0));
    }

    @Test
    void testCreateOrder() {
        // Prepare order input
        String orderInput = """
        {
            "orderId": 26
        }
        """;

        Long orderId =
                given()
                        .contentType(ContentType.JSON)
                        .body(orderInput)
                        .when()
                        .post("/v1/order")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .body("orderId", notNullValue())
                        .body("status", equalTo("RECEBIDO"))
                        .extract().body().jsonPath().getObject("orderId", Long.class);

        // Additional validation can be added here if needed
        assertNotNull(orderId);
    }

    @Test
    void testStartOrderPreparation() {
        // First, create an order
        Long orderId = createTestOrder(20L);

        // Then start preparation
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/v1/order/" + orderId + "/start")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void testOrderDone() {
        // First, create an order and start preparation
        Long orderId = createTestOrder(21L);
        startOrderPreparation(orderId);

        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/v1/order/" + orderId + "/done")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void testOrderFinished() {
        // Create, start, and complete order
        Long orderId = createTestOrder(22L);
        startOrderPreparation(orderId);
        markOrderDone(orderId);

        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/v1/order/" + orderId + "/finished")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    // Helper methods to support test scenarios
    private Long createTestOrder(Long orderId) {
        String orderInput = """
        {
            "orderId": %s
        }
        """.formatted(orderId);

        return given()
                .contentType(ContentType.JSON)
                .body(orderInput)
                .when()
                .post("/v1/order")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().body().jsonPath().getObject("orderId", Long.class);
    }

    private void startOrderPreparation(Long orderId) {
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/v1/order/" + orderId + "/start")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    private void markOrderDone(Long orderId) {
        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/v1/order/" + orderId + "/done")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    private static Order generateOrder(Long orderId) {
        return Order.builder()
                .orderId(orderId)
                .status(OrderStatus.RECEBIDO)
                .id(UUID.randomUUID())
                .build();
    }

}
