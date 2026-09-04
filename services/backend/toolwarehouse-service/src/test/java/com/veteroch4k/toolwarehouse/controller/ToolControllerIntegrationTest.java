package com.veteroch4k.toolwarehouse.controller;

import com.veteroch4k.toolwarehouse.BaseIntegrationTest;
import com.veteroch4k.toolwarehouse.dto.ToolRequest;
import com.veteroch4k.toolwarehouse.models.Tool;
import com.veteroch4k.toolwarehouse.models.ToolType;
import com.veteroch4k.toolwarehouse.repositories.ToolRepository;
import com.veteroch4k.toolwarehouse.repositories.ToolTypeRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ToolControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ToolTypeRepository toolTypeRepository;


    @AfterEach
    void tearDown() {
        toolRepository.deleteAll();
        toolTypeRepository.deleteAll();
    }

    @Test
    void shouldGetToolsByTypeName() {
        String expectedName = "testTool";
        String notExpectedName = "Not expected";

        ToolType expectedType = new ToolType();
        expectedType.setName(expectedName);

        ToolType notExpectedType = new ToolType();
        notExpectedType.setName(notExpectedName);

        Tool tool1 = new Tool();
        tool1.setToolType(expectedType);
        Tool tool2 = new Tool();
        tool2.setToolType(expectedType);

        Tool tool3 = new Tool();
        tool3.setToolType(notExpectedType);

        toolTypeRepository.save(expectedType);
        toolTypeRepository.save(notExpectedType);

        toolRepository.save(tool1);
        toolRepository.save(tool2);
        toolRepository.save(tool3);

        given()
                .contentType(ContentType.JSON)
                .param("typeName", expectedName)
        .when()
                .get("/api/tool/by-type-name")
        .then()
                .statusCode(200)
                .body("content.size()", equalTo(2))
                .body("content[0].toolType.name", equalTo(expectedName))
                .body("content[1].toolType.name", equalTo(expectedName));
    }

    @Test
    void shouldExecuteFullToolLifecycle() {

        ToolType hammerType = new ToolType();
        String name1 = "Молоток";
        hammerType.setName(name1);
        toolTypeRepository.save(hammerType);

        ToolType drillType = new ToolType();
        String name2 = "Дрель";
        drillType.setName(name2);
        toolTypeRepository.save(drillType);

        ToolRequest createRequest = new ToolRequest(hammerType.getId());

        Integer createdToolId = given()
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/api/tool/create-tool")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .when()
                .get("/api/tool/" + createdToolId)
                .then()
                .statusCode(200)
                .body("toolType.name", equalTo(name1));

        ToolRequest updateRequest = new ToolRequest(drillType.getId());

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/api/tool/" + createdToolId)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/api/tool/" + createdToolId)
                .then()
                .statusCode(200)
                .body("toolType.name", equalTo(name2));

        given()
                .when()
                .delete("/api/tool/" + createdToolId)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/api/tool/" + createdToolId)
                .then()
                .statusCode(404);
    }

}
