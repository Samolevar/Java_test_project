package com.triangle.functionaltests.createtests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import com.triangle.models.TestRequest;
import com.triangle.models.Triangle;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class CreateVariousTypesOfTriangleTests extends FunctionalTestBase {
    //Tests for checking creation various types of triangle:
    //Want to check how triangle's validation works

    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @Test
    void SatisfyTriangleFormula_Success(){
        Triangle satisfyFormulaTriangle = new Triangle(4,5,6);
        TestRequest request = new TestRequest(";", satisfyFormulaTriangle);

        RequestSpec
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void EquilateralTriangle_Success(){
        Triangle equilateralTriangle = new Triangle(3,3,3);
        TestRequest request = new TestRequest(";", equilateralTriangle);
        RequestSpec
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void IsoscelesTriangle_Success(){
        Triangle isoscelesTriangle = new Triangle(5,5,6);
        TestRequest request = new TestRequest(";", isoscelesTriangle);
        RequestSpec
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void RightTriangle_Success(){
        Triangle rightTriangle = new Triangle(3,4,5);
        TestRequest request = new TestRequest(";", rightTriangle);
        RequestSpec
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 118, 1000",
            "0, 1, 1",
            "0, 0, 1",
            "0, 1, 0",
            "1, 0, 0",
            "1, 1, 0",
            "1, 0, 1"
    })
    void NotSatisfyTriangleFormula_Failed(int aSide, int bSide, int cSide){
        Triangle notSatisfyFormulaTriangle = new Triangle(aSide,bSide, cSide);
        TestRequest request = new TestRequest(";", notSatisfyFormulaTriangle);
        RequestSpec
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void Square_Failed(){
        RequestSpec
                .contentType("application/json")
                .body("{\"separator\": \";\", \"input\": \"4;4;4;4\"}")
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void TwoSameTriangle_Success(){
        Triangle simpleTriangle = new Triangle(3,4,5);
        TestRequest request = new TestRequest(";", simpleTriangle);

        String id = RequestSpec
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id");

        RequestSpec
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .body("id", not(equalTo(id)));
    }
}
