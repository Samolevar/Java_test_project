package com.triangle.functionaltests.createtests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.equalTo;

public class InputParameterTests extends FunctionalTestBase {
    //Tests for checking if we sent different input parameters:
    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @Test
    void SeparatorIsLastElement_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"-\", \"input\": \"3-4-5-\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void EmptyInput_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"-\", \"input\": \"\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void TwoSeparatorsInARow_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"-\", \"input\": \"3-4--5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @ParameterizedTest
    @ValueSource(strings = {"3;4", "3,3;4,4;5,5" })
    void IncorrectInput_Failed(String input){
        RequestSpec.when()
                .contentType("application/json")
                .body(String.format("{\"input\": \"%s\"}", input))
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void MoreThanOneTriangle_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \";\", \"input\": [\"3;4;5\", \"3;4;5\"]}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(400);
    }

    @Test
    void NegativeNumbers_Success(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \";\", \"input\": \"-3;-4;-5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .body("firstSide", equalTo(3.0f),
                        "secondSide", equalTo(4.0f),
                        "thirdSide", equalTo(5.0f)
                     );
    }

    @Test
    void FloatInInput_Success(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \";\", \"input\": \"3.3;4.4;5.5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .body("firstSide", equalTo(3.3f),
                        "secondSide", equalTo(4.4f),
                        "thirdSide", equalTo(5.5f)
                );
    }
}

