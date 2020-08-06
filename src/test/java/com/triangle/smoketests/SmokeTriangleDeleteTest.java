package com.triangle.smoketests;

import com.triangle.helpers.CreateActions;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;

public class SmokeTriangleDeleteTest extends SmokeTestBase{

    // Quick check that DELETE request works
    @Test
    void UserCanSendDeleteRequestTest() {
        String id = CreateActions.CreateTriangleAndGetId(RequestSpec);

        RequestSpec.when()
                .delete("/triangle/" + id)
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }
}
