package com.triangle.functionaltests.createtests;

import com.triangle.functionaltests.FunctionalTestBase;
import com.triangle.helpers.CleanActions;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SeparatorParameterTests extends FunctionalTestBase {
    // Tests for checking if separator works correctly

    @BeforeEach
    void SetUp()
    {
        CleanActions.CleanAll(RequestSpec);
    }

    @Test
    void EmptySeparatorAndCorrectInput_Success(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"input\": \"3;4;5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void EmptySeparatorAndIncorrectSeparatorInInput_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"input\": \"3,4,5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void EmptySeparatorAndIncorrectInput_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"input\": \"test\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void SeparatorDoesNotMatchInput_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"-\", \"input\": \"3;4;5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @Test
    void SeparatorMatchInput_Success(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"-\", \"input\": \"3-4-5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void SeparatorIsChar_Success(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"a\", \"input\": \"3a4a5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void SeparatorLengthMoreThanOne_Success(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"abc\", \"input\": \"3abc4abc5\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void SeparatorIsNumber_Failed(){
        RequestSpec.when()
                .contentType("application/json")
                .body("{\"separator\": \"3\", \"input\": \"53536\"}")
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .assertThat()
                .statusCode(422);
    }

    @ParameterizedTest
    @ValueSource(chars = {'*', '^', '$'})
    void SeparatorIsSpecialSymbol_Success(char separator){
        RequestSpec.when()
                .contentType("application/json")
                .body(String.format("{\"separator\": \"%c\", \"input\": \"3%c4%c5\"}", separator, separator, separator))
                .post("/triangle")
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(200);
    }
}
