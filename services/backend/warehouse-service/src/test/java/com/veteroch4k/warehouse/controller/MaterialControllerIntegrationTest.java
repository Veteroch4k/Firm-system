package com.veteroch4k.warehouse.controller;

import com.veteroch4k.warehouse.BaseIntegrationTest;
import com.veteroch4k.warehouse.dto.MaterialRequest;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaterialControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MaterialRepository materialRepository;


    @AfterEach
    void tearDown() {
        materialRepository.deleteAll();
    }

    /**
     * Контроллер реализует простой CRUD, так что по факту тест здесь чтобы проверить, нормально ли накатываются скрипты Liquibase
     */

    @Test
    void shouldCreateNewMaterial() {

        MaterialRequest request = new MaterialRequest("Test");

        given().
                contentType(ContentType.JSON)
                .body(request)
        .when()
                .post("/api/material")
        .then()
                .statusCode(201)
                .body("name", equalTo(request.name()));

    }


}
