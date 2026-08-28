package com.veteroch4k.product.product.controllers;

import com.veteroch4k.product.BaseIntegrationTest;
import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.models.Product;
import com.veteroch4k.product.repositories.DrawingRepository;
import com.veteroch4k.product.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DrawingRepository drawingRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void shouldGetManufacturingInfo() {


        Drawing drawing = new Drawing();
        drawing.setFactoryId(1L);
        drawing.setOperationId(1L);

        Product product = new Product();
        product.setDescription("Product Description");
        product.setDrawing(drawing);

        drawingRepository.save(drawing);
        productRepository.save(product);


        given()
                .contentType("application/json")
        .when()
                .get("/api/product/{id}/manufacturing-info", product.getId())
        .then()
                .statusCode(200)
                .body("productId", equalTo(product.getId().intValue()))
                .body("drawingId", equalTo(drawing.getId().intValue()));

    }


}
