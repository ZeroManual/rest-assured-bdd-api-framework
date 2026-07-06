package com.company.api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;
import java.util.Map;

public class ApiClient {
    public Response execute(String method,
                            String endpoint,
                            Map<String, Object> pathParams,
                            Map<String, Object> queryParams,
                            Map<String, String> headers,
                            Object body) {

        RequestSpecification request = RestAssured.given()
                .spec(SpecificationFactory.requestSpec())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams);

        if (body != null) {
            request.body(body);
        }

        String normalized = method.toUpperCase(Locale.ROOT);
        return switch (normalized) {
            case "GET" -> request.when().get(endpoint);
            case "POST" -> request.when().post(endpoint);
            case "PUT" -> request.when().put(endpoint);
            case "PATCH" -> request.when().patch(endpoint);
            case "DELETE" -> request.when().delete(endpoint);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
    }
}
