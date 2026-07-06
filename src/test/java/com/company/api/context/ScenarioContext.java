package com.company.api.context;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private String endpoint;
    private Object requestBody;
    private Response response;
    private final Map<String, Object> pathParams = new HashMap<>();
    private final Map<String, Object> queryParams = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> data = new HashMap<>();

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Map<String, Object> getPathParams() {
        return pathParams;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void clearAuthHeaders() {
        headers.remove("Authorization");
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = data.get(key);
        if (value == null) {
            throw new IllegalArgumentException("No value found in ScenarioContext for key: " + key);
        }
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Value for key '" + key + "' is not of type " + type.getSimpleName());
        }
        return (T) value;
    }
}
