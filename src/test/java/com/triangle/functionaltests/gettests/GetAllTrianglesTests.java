package com.triangle.functionaltests.gettests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import com.triangle.helpers.CreateActions;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllTrianglesTests extends FunctionalTestBase {
    //Tests for checking how get all triangles api works

    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @Test
    void NoTriangles_ShouldReturnZero(){
        int countOfRecords = RequestSpec.given()
                .get("/triangle/all")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .extract()
                .jsonPath()
                .getList("id")
                .size();

        assertEquals(0, countOfRecords);
    }

    @Test
    void MaximumTrianglesExist_ShouldReturnAll(){
        CreateActions.CreateTriangles(RequestSpec, 10);
        int countOfRecords = RequestSpec.given()
                .get("/triangle/all")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .extract()
                .jsonPath()
                .getList("id")
                .size();

        assertEquals(10, countOfRecords);
    }

    @Test
    void OneTriangleExists_ShouldReturnOne(){
        CreateActions.CreateTriangles(RequestSpec, 1);

        int countOfRecords = RequestSpec.given()
                .get("/triangle/all")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .extract()
                .jsonPath()
                .getList("id")
                .size();

        assertEquals(1, countOfRecords);
    }
}
