package com.triangle.functionaltests;

import com.triangle.helpers.CleanActions;
import com.triangle.models.Triangle;
import com.triangle.testbase.TestBase;

import io.restassured.RestAssured;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


import static io.restassured.RestAssured.given;

public class FunctionalTestBase extends TestBase {
    protected static Triangle SimpleEquilateralTriangle;

    @BeforeAll
    public static void OneTimeSetUp() {
        RestAssured.baseURI = "https://qa-quiz.natera.com/";

        RequestSpec = given()
                .header("X-User", "put your userID here");

        SimpleEquilateralTriangle = new Triangle(4, 4,4);
    }

    @AfterAll
    public static void TearDown(){
        CleanActions.CleanAll(RequestSpec);
    }
}
