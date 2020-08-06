package com.triangle.functionaltests.deletetests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import com.triangle.helpers.CreateActions;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteTriangleTests extends FunctionalTestBase {
    // Tests for checking how deleting works

    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @Test
    void ExistingTriangle_Success(){
        String id = CreateActions.CreateTriangleAndGetId(RequestSpec);

        RequestSpec.when()
                .delete(String.format("/triangle/%s", id));

        RequestSpec.when()
                .get(String.format("/triangle/%s", id))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }

    @Test
    void NotExistingTriangle_Failed(){
        CreateActions.CreateTriangles(RequestSpec, 3);

        RequestSpec.when()
                .delete("/triangle/aaaa-aaaa-aaaa-aaaa1")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }

    @Test
    void NoTriangleAtAll_Failed(){
        RequestSpec.when()
                .delete("/triangle/9b8bf0e7-d4f5-4632-8c6e-632f4064c867")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }

    @Test
    void ReDelete_Failed(){
        String id = CreateActions.CreateTriangleAndGetId(RequestSpec);

        RequestSpec.when()
                .delete(String.format("/triangle/%s", id))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);

        RequestSpec.when()
                .delete(String.format("/triangle/%s", id))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }
}
