package com.veteroch4k.employers.controller;

import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EmployerControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private EmployerRepository employerRepository;

    @AfterEach
    void tearDown() {
        employerRepository.deleteAll();
    }

    @Test
    void shouldGetRandomEmployer() {

        Employer employer = new Employer();

        employer.setName("Test");

        employerRepository.save(employer);

        given().
                contentType("application/json")
        .when()
                .get("/api/employers/random")
        .then()
                .statusCode(200)
                .body("id", equalTo(employer.getId().intValue()))
                .body("name", equalTo(employer.getName()));


    }
}
