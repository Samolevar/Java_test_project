package com.triangle.functionaltests.gettests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import com.triangle.helpers.CreateActions;
import com.triangle.helpers.MathActions;
import com.triangle.models.Triangle;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAreaTests extends FunctionalTestBase {
    //Tests for checking how get area of triangles api works

    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @ParameterizedTest(name="{index}: calculate {3} area")
    @CsvSource({
            "4, 5, 6, SatisfyFormulaTriangle",
            "3, 3, 3, EquilateralTriangle",
            "5, 5, 6, IsoscelesTriangle",
            "3, 4, 5, RightTriangle",
            "5, 6, 9, ObtuseTriangle",
            "-4, 5, 6, SatisfyFormulaTriangleWithNegativeNumber",
            "-4, -5, -6, SatisfyFormulaTriangleWithAllNegatives",
            "3.3, 4.4, 5.5, FloatValuesTriangle"
    })
    void CalculatedArea_Correct(float aSide, float bSide, float cSide, String nameOfTriangle){
        Triangle triangle = new Triangle(aSide, bSide, cSide);
        String id = CreateActions.CreateTriangleAndGetId(RequestSpec, triangle);

        float area = RequestSpec.when()
                .get(String.format("/triangle/%s/area", id))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .extract()
                .path("result");

        assertEquals(MathActions.RoundOff(triangle.GetArea()), MathActions.RoundOff(area));
    }

    @Test
    void NotExistedTriangle_Failed(){
        RequestSpec.when()
                .get("/triangle/436c6693-3a82-464f-8235-bb385d3d2ce8/area")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }

    @Test
    void ManyTrianglesInRequest_Failed(){
        String firstTriangle = CreateActions.CreateTriangleAndGetId(RequestSpec);
        String secondTriangle = CreateActions.CreateTriangleAndGetId(RequestSpec);

        RequestSpec.when()
                .get(String.format("/triangle/%s&%s/area", firstTriangle, secondTriangle))
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(404);
    }
}
