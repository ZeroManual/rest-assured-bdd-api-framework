package com.company.api.steps;

import com.company.api.assertions.ResponseAssertions;
import com.company.api.context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class AssertionSteps {
    private final ScenarioContext context;

    public AssertionSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("response status code should be {int}")
    public void responseStatusCodeShouldBe(int expectedStatusCode) {
        ResponseAssertions.assertStatusCode(context.getResponse(), expectedStatusCode);
    }

    @And("response content type should contain {string}")
    public void responseContentTypeShouldContain(String expectedContentType) {
        ResponseAssertions.assertContentTypeContains(context.getResponse(), expectedContentType);
    }

    @And("response time should be below {long} ms")
    public void responseTimeShouldBeBelow(long maxMillis) {
        ResponseAssertions.assertResponseTimeBelow(context.getResponse(), maxMillis);
    }

    @And("response field {string} should equal {string}")
    public void responseFieldShouldEqual(String jsonPath, String expectedValue) {
        ResponseAssertions.assertJsonPathEqualsString(context.getResponse(), jsonPath, expectedValue);
    }

    @And("response field {string} should equal number {int}")
    public void responseFieldShouldEqualNumber(String jsonPath, int expectedValue) {
        ResponseAssertions.assertJsonPathEqualsNumber(context.getResponse(), jsonPath, expectedValue);
    }

    @And("response field {string} should not be null")
    public void responseFieldShouldNotBeNull(String jsonPath) {
        ResponseAssertions.assertJsonPathNotNull(context.getResponse(), jsonPath);
    }

    @And("response field {string} should match regex {string}")
    public void responseFieldShouldMatchRegex(String jsonPath, String regex) {
        ResponseAssertions.assertJsonPathMatchesRegex(context.getResponse(), jsonPath, regex);
    }

    @And("response schema should match {string}")
    public void responseSchemaShouldMatch(String schemaFileName) {
        ResponseAssertions.assertSchema(context.getResponse(), schemaFileName);
    }

    @And("response array size should be greater than {int}")
    public void responseArraySizeShouldBeGreaterThan(int minSize) {
        ResponseAssertions.assertArraySizeGreaterThan(context.getResponse(), minSize);
    }

    @And("each item should contain field {string}")
    public void eachItemShouldContainField(String fieldName) {
        ResponseAssertions.assertEachItemContainsField(context.getResponse(), fieldName);
    }

    @And("response should not contain server error")
    public void responseShouldNotContainServerError() {
        ResponseAssertions.assertNoServerError(context.getResponse());
    }

    @And("response header {string} should equal {string}")
    public void responseHeaderShouldEqual(String headerName, String expectedValue) {
        ResponseAssertions.assertHeaderEquals(context.getResponse(), headerName, expectedValue);
    }
}
