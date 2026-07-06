package com.company.api.clients;

import com.company.api.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class SpecificationFactory {
    private SpecificationFactory() {
    }

    public static RequestSpecification requestSpec() {
        int timeout = ConfigManager.getInt("request.timeout.ms", 10000);
        RestAssuredConfig config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeout)
                        .setParam("http.socket.timeout", timeout)
                        .setParam("http.connection-manager.timeout", timeout));

        String baseUri = System.getProperty("base.uri", ConfigManager.get("base.uri"));

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setConfig(config)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());

        String token = ConfigManager.getOrDefault("auth.token", "");
        if (!token.isBlank()) {
            builder.addHeader("Authorization", "Bearer " + token);
        }

        String apiKey = ConfigManager.getOrDefault("api.key", "");
        String apiKeyHeader = ConfigManager.getOrDefault("api.key.header", "x-api-key");
        if (!apiKey.isBlank()) {
            builder.addHeader(apiKeyHeader, apiKey);
        }

        return builder.build();
    }
}
