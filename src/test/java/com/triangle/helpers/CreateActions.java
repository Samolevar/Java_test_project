package com.triangle.helpers;

import com.triangle.models.TestRequest;
import com.triangle.models.Triangle;
import io.restassured.specification.RequestSpecification;

public class CreateActions {
    public static void CreateTriangles(RequestSpecification requestSpecification, int count) {
        Triangle triangle = new Triangle(3, 4, 5);
        TestRequest request = new TestRequest(";", triangle);
        for (int i = 0; i < count; i++) {
            requestSpecification
                    .contentType("application/json")
                    .body(request)
                    .when()
                    .post("/triangle")
                    .then()
                    .statusCode(200);
        }
    }

    public static String CreateTriangleAndGetId(RequestSpecification requestSpecification){
        Triangle triangle = new Triangle(3,4,5);

        return CreateTriangleAndGetId(requestSpecification, triangle);
    }

    public static String CreateTriangleAndGetId(RequestSpecification requestSpecification, Triangle triangle){
        TestRequest request = new TestRequest(";", triangle);

        return requestSpecification
                .contentType("application/json")
                .body(request)
                .when()
                .post("/triangle")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id");
    }
}
