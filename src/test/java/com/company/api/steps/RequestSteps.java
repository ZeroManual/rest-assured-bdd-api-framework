package com.company.api.steps;

import com.company.api.clients.ApiClient;
import com.company.api.config.ConfigManager;
import com.company.api.context.ScenarioContext;
import com.company.api.payloads.PayloadFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class RequestSteps {
    private final ScenarioContext context;
    private final ApiClient apiClient;

    public RequestSteps(ScenarioContext context) {
        this.context = context;
        this.apiClient = new ApiClient();
    }

    @Given("I set API endpoint {string}")
    public void iSetApiEndpoint(String endpoint) {
        context.setEndpoint(endpoint);
    }

    @And("I set path parameter {string} as {string}")
    public void iSetPathParameter(String key, String value) {
        context.getPathParams().put(key, parseValue(value));
    }

    @And("I set query parameter {string} as {string}")
    public void iSetQueryParameter(String key, String value) {
        context.getQueryParams().put(key, parseValue(value));
    }

    @And("I set header {string} as {string}")
    public void iSetHeader(String key, String value) {
        context.getHeaders().put(key, value);
    }

    @And("I set bearer token from config")
    public void iSetBearerTokenFromConfig() {
        String token = ConfigManager.getOrDefault("auth.token", "");
        if (token.isBlank()) {
            throw new IllegalStateException("auth.token is empty. Pass token from Jenkins credentials using -Dauth.token=...");
        }
        context.getHeaders().put("Authorization", "Bearer " + token);
    }

    @And("I clear auth headers")
    public void iClearAuthHeaders() {
        context.clearAuthHeaders();
    }

    @And("I prepare {string} request payload")
    public void iPrepareRequestPayload(String payloadName) {
        context.setRequestBody(PayloadFactory.createPayload(payloadName));
    }

    @When("I send {string} request")
    public void iSendRequest(String method) {
        context.setResponse(apiClient.execute(
                method,
                context.getEndpoint(),
                context.getPathParams(),
                context.getQueryParams(),
                context.getHeaders(),
                context.getRequestBody()
        ));
    }

    private Object parseValue(String value) {
        if (value.matches("^-?\\d+$")) {
            return Integer.parseInt(value);
        }
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }
}
