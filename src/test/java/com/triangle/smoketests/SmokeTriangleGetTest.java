package com.triangle.smoketests;

import com.triangle.helpers.CreateActions;
import com.triangle.models.TestRequest;
import com.triangle.models.Triangle;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SmokeTriangleGetTest extends SmokeTestBase{
    // Quick check that all Get methods work
    @ParameterizedTest
    @ValueSource(strings = {"/triangle/all", "/triangle/{triangleId}",
            "/triangle/{triangleId}/perimeter",
            "/triangle/{triangleId}/area"})
    void UserCanSendGetRequestTest(String path) {
        String actualPath = path.replace("{triangleId}", Id);
        RequestSpec.when()
                .get(actualPath)
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void ValidateResponse_Correct(){
        RequestSpec.when()
                .get("/triangle/" + Id)
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema.json"));
    }
}
