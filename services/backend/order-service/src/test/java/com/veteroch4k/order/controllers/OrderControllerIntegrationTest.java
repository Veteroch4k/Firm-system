package com.veteroch4k.order.controllers;

import com.veteroch4k.order.dto.orderDTO.OrderRequestDTO;
import com.veteroch4k.order.model.Order;
import com.veteroch4k.order.repository.OrderRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void shouldCreateOrderViaHttp() {

        Long productId = 1L;
        Long productQuantity = 10L;

        OrderRequestDTO requestDTO = new OrderRequestDTO(productId, productQuantity);

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
        .when()
                .post("/api/order")
        .then()
                .statusCode(201);

        assertEquals(1, orderRepository.count());
    }

    @Test
    void shouldGetOrderByDatesBetween() {

        Long productId = 5L;
        Long productQuantity = 10L;

        Order oldOrder = new Order();
        oldOrder.setProductId(1L);
        oldOrder.setProductQuantity(productQuantity);
        oldOrder.setOrderDate(LocalDate.now().minusDays(10));
        oldOrder.setFinishDate(LocalDate.now());


        Order validOrder = new Order();
        validOrder.setProductId(productId);
        validOrder.setProductQuantity(productQuantity);
        validOrder.setOrderDate(LocalDate.now().minusDays(1));
        validOrder.setFinishDate(LocalDate.now());


        Order validYounger = new Order();
        validYounger.setProductId(2L);
        validYounger.setProductQuantity(productQuantity);
        validYounger.setOrderDate(LocalDate.now().plusDays(10));
        validYounger.setFinishDate(LocalDate.now().plusDays(11));


        String startDate = LocalDate.now().minusDays(5).toString();
        String endDate = LocalDate.now().plusDays(5).toString();

        orderRepository.save(oldOrder);
        orderRepository.save(validOrder);
        orderRepository.save(validYounger);


        given()
                .contentType(ContentType.JSON)
                .queryParam("start", startDate)
                .queryParam("end", endDate)
        .when()
                .get("/api/order/between-dates")
        .then()
                .statusCode(200)
                .body("content.size()", equalTo(1))
                .body("content[0].productId", equalTo(productId.intValue()));
    }
}
