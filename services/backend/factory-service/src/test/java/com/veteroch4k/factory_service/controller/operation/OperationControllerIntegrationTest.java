package com.veteroch4k.factory_service.controller.operation;

import com.veteroch4k.factory_service.BaseIntegrationTest;
import com.veteroch4k.factory_service.dto.operation.OperationRequest;
import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import com.veteroch4k.factory_service.repository.OperationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OperationControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private FactoryRepository factoryRepository;

    @AfterEach
    void tearDown() {

        operationRepository.deleteAll();
        factoryRepository.deleteAll();


    }

    @Test
    void shouldCreateOperation() {

        Factory f = new Factory();
        f.setName("factory");
        factoryRepository.save(f);

        String name = "test";

        OperationRequest op = new OperationRequest(
                name,
                1L,
                f.getId()
        );

        given()
                .header("Authorization", "Bearer dummy-token")
                .contentType("application/json")
                .body(op)
        .when()
                .post("/api/operation")
        .then()
                .statusCode(201)
                .body("name", equalTo(name))
                .body("factory.id", equalTo(f.getId().intValue()));

    }
}
