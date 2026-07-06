package com.company.api.assertions;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class ResponseAssertions {
    private ResponseAssertions() {
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    public static void assertContentTypeContains(Response response, String expectedContentType) {
        assertThat("Content-Type header mismatch",
                response.getContentType(), containsString(expectedContentType));
    }

    public static void assertResponseTimeBelow(Response response, long maxMillis) {
        response.then().time(lessThan(maxMillis));
    }

    public static void assertJsonPathEqualsString(Response response, String jsonPath, String expected) {
        String actual = response.jsonPath().getString(jsonPath);
        Assertions.assertThat(actual)
                .as("JSONPath '%s' value mismatch", jsonPath)
                .isEqualTo(expected);
    }

    public static void assertJsonPathEqualsNumber(Response response, String jsonPath, int expected) {
        Integer actual = response.jsonPath().getInt(jsonPath);
        Assertions.assertThat(actual)
                .as("JSONPath '%s' number mismatch", jsonPath)
                .isEqualTo(expected);
    }

    public static void assertJsonPathNotNull(Response response, String jsonPath) {
        Object actual = response.jsonPath().get(jsonPath);
        Assertions.assertThat(actual)
                .as("JSONPath '%s' should not be null", jsonPath)
                .isNotNull();
    }

    public static void assertJsonPathMatchesRegex(Response response, String jsonPath, String regex) {
        String actual = response.jsonPath().getString(jsonPath);
        Assertions.assertThat(actual)
                .as("JSONPath '%s' should match regex '%s'", jsonPath, regex)
                .matches(regex);
    }

    public static void assertSchema(Response response, String schemaFileName) {
        String path = "schemas/" + schemaFileName;
        InputStream schema = ResponseAssertions.class.getClassLoader().getResourceAsStream(path);
        if (schema == null) {
            throw new IllegalArgumentException("Schema file not found: " + path);
        }
        response.then().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }

    public static void assertArraySizeGreaterThan(Response response, int minSize) {
        List<Object> list = response.jsonPath().getList("$");
        Assertions.assertThat(list)
                .as("Response root array size")
                .hasSizeGreaterThan(minSize);
    }

    public static void assertEachItemContainsField(Response response, String fieldName) {
        List<Map<String, Object>> list = response.jsonPath().getList("$");
        Assertions.assertThat(list)
                .as("Response root should be an array")
                .isNotEmpty();
        Assertions.assertThat(list)
                .as("Every item should contain field '%s'", fieldName)
                .allSatisfy(item -> Assertions.assertThat(item).containsKey(fieldName));
    }

    public static void assertNoServerError(Response response) {
        Assertions.assertThat(response.statusCode())
                .as("API should not return 5xx server error")
                .isLessThan(500);
    }

    public static void assertHeaderEquals(Response response, String headerName, String expectedValue) {
        response.then().header(headerName, Matchers.equalTo(expectedValue));
    }
}
