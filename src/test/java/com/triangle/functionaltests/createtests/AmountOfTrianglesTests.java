package com.triangle.functionaltests.createtests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import com.triangle.helpers.CreateActions;
import com.triangle.models.TestRequest;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountOfTrianglesTests extends FunctionalTestBase {
    private static final TestRequest request = new TestRequest(";", SimpleEquilateralTriangle);
    // Tests for checking how can we create some amount of triangles
    // Include test for maximum count of triangles
    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @ParameterizedTest(name = "{index}: {0} triangle(s) already exist")
    @CsvSource({
            "0, 1",
            "9, 10",
            "10, 10"
    })
    void CreateNewTriangle_ifSomeAlreadyExist(int existedTriangles, int expectedCount){
        CreateActions.CreateTriangles(RequestSpec, existedTriangles);

        RequestSpec.when()
                .contentType("application/json")
                .body(request)
                .post("/triangle");

        int countOfRecords = RequestSpec
                .get("/triangle/all")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .extract()
                .jsonPath()
                .getList("id")
                .size();

        assertEquals(expectedCount, countOfRecords);
    }
}
