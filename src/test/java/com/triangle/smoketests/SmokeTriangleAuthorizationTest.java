package com.triangle.smoketests;

import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SmokeTriangleAuthorizationTest extends SmokeTestBase{
    // Quick check that Authorization works
    @Test
    void UserMustBeAuthorizedTest() {
        given()
                .header("X-User", "some incorrect apiKey")
                .when()
                .get("/triangle/all")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(401);
    }
}
