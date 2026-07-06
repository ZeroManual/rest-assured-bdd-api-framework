package com.company.api.payloads;

import com.company.api.models.PostRequest;
import com.company.api.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;

public final class PayloadFactory {
    private PayloadFactory() {
    }

    public static Object createPayload(String payloadName) {
        JsonNode node = JsonUtils.readJsonFromClasspath("testdata/payloads.json").get(payloadName);
        if (node == null) {
            throw new IllegalArgumentException("Payload not found in testdata/payloads.json: " + payloadName);
        }

        return switch (payloadName) {
            case "createPost", "updatePost" -> JsonUtils.convert(node, PostRequest.class);
            default -> node;
        };
    }
}
