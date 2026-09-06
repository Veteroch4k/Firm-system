package com.veteroch4k.factory_service.controller;

import com.veteroch4k.factory_service.BaseIntegrationTest;
import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FactoryControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private FactoryRepository factoryRepository;

    @AfterEach
    void tearDown() {
        factoryRepository.deleteAll();

    }

    @Test
    void shouldGetFactories() {

        Factory f1 = new Factory();
        f1.setName("factory1");

        Factory f2 = new Factory();
        f2.setName("factory2");

        Factory f3 = new Factory();
        f3.setName("factory3");

        Factory f4 = new Factory();
        f4.setName("factory4");

        Factory f5 = new Factory();
        f5.setName("factory5");
        factoryRepository.save(f1);
        factoryRepository.save(f2);
        factoryRepository.save(f3);
        factoryRepository.save(f4);
        factoryRepository.save(f5);

        String page = "0";
        String size = "3";
        given()
                .contentType("application/json")
                .param("page", page)
                .param("size", size)
        .when()
                .get("/api/factory/all")

        .then()
                .statusCode(200)
                .body("content.size()", equalTo(Integer.valueOf(size)));

    }
}