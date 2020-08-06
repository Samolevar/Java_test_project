package com.triangle.smoketests;

import com.triangle.helpers.CleanActions;
import com.triangle.models.TestRequest;
import com.triangle.models.Triangle;
import com.triangle.testbase.TestBase;

import io.restassured.RestAssured;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class SmokeTestBase extends TestBase {
    protected static Triangle SimpleEquilateralTriangle;
    protected static TestRequest SetUpRequest;
    protected static String Id;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://qa-quiz.natera.com/";

        RequestSpec = given()
                .header("X-User", "put your userID here");

        SimpleEquilateralTriangle = new Triangle(4, 4,4);
        SetUpRequest = new TestRequest(";", SimpleEquilateralTriangle);

        // Prepare data for Get tests
        Id = RequestSpec
                .contentType("application/json")
                .body(SetUpRequest)
                .when()
                .post("/triangle")
                .then()
                .extract()
                .jsonPath()
                .get("id");
    }

    @AfterAll
    public static void TearDown(){
        CleanActions.CleanAll(RequestSpec);
    }
}
