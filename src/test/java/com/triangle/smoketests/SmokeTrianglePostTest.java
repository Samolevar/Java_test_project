package com.triangle.smoketests;

import com.triangle.models.TestRequest;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SmokeTrianglePostTest extends SmokeTestBase {
    private static final TestRequest request = new TestRequest(";", SimpleEquilateralTriangle);
    // Quick checks that Post method works
    @Test
    void CreateTriangle_Success() {
        RequestSpec.when()
                .contentType("application/json")
                .body(request)
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void ValidateResponse_Correct(){
        RequestSpec.when()
                .contentType("application/json")
                .body(request)
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    void InvalidJson_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"test\" : 1}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(500);
    }

    @Test
    void EmptyRequest_Failed(){
        RequestSpec.when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(500);
    }
}
