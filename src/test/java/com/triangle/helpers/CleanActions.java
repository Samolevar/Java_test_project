package com.triangle.helpers;

import io.restassured.specification.RequestSpecification;

import java.util.List;

public class CleanActions {
    public static void CleanAll(RequestSpecification requestSpecification) {
        List<String> ids = requestSpecification.when()
                .get("/triangle/all")
                .then()
                .extract()
                .jsonPath()
                .getList("id");

        for (String id : ids) {
            requestSpecification.given()
                    .delete(String.format("/triangle/%s", id))
                    .then()
                    .statusCode(200);
        }
    }
}
