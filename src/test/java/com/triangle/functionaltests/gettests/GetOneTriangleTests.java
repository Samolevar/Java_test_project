package com.triangle.functionaltests.gettests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import com.triangle.helpers.CreateActions;
import com.triangle.models.Triangle;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetOneTriangleTests extends FunctionalTestBase {
    //Tests for checking how get triangle api works
    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @Test
    void NoTrianglesAtAll_Failed(){
        RequestSpec.when()
                .get("/triangle/436c6693-3a82-464f-8235-bb385d3d2ce8")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }

    @Test
    void OnlyOneTriangleExists_Success(){
        String id = CreateActions.CreateTriangleAndGetId(RequestSpec);

        RequestSpec.when()
                .get(String.format("/triangle/%s", id))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void ManyTrianglesExist_Success(){
        CreateActions.CreateTriangles(RequestSpec, 5);
        String id = CreateActions.CreateTriangleAndGetId(RequestSpec);

        RequestSpec.when()
                .get(String.format("/triangle/%s", id))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void GetNotLastAddedTriangle_Success(){
        CreateActions.CreateTriangleAndGetId(RequestSpec);
        Triangle triangle = new Triangle(3,4,5);
        String secondTriangleId = CreateActions.CreateTriangleAndGetId(RequestSpec, triangle);
        CreateActions.CreateTriangleAndGetId(RequestSpec);

        String returnedId = RequestSpec.when()
                .get(String.format("/triangle/%s", secondTriangleId))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .body("firstSide", equalTo(triangle.getaSide()),
                        "secondSide", equalTo(triangle.getbSIde()),
                        "thirdSide", equalTo(triangle.getcSide()))
                .and()
                .extract()
                .path("id");


        assertEquals(returnedId, secondTriangleId);
    }

    @Test
    void ManyTrianglesInRequest_Failed(){
        String firstTriangleId = CreateActions.CreateTriangleAndGetId(RequestSpec);
        String secondTriangleId = CreateActions.CreateTriangleAndGetId(RequestSpec);

        RequestSpec.when()
                .get(String.format("/triangle/%s&%s", firstTriangleId, secondTriangleId))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }
}
