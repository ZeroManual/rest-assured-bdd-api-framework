package com.company.api.steps;

import com.company.api.context.ScenarioContext;
import com.company.api.models.PostResponse;
import io.cucumber.java.en.And;
import org.assertj.core.api.Assertions;

public class SerializationSteps {
    private final ScenarioContext context;

    public SerializationSteps(ScenarioContext context) {
        this.context = context;
    }

    @And("I deserialize response as {string} and validate required fields")
    public void iDeserializeResponseAsAndValidateRequiredFields(String modelName) {
        if ("PostResponse".equalsIgnoreCase(modelName)) {
            PostResponse postResponse = context.getResponse().as(PostResponse.class);
            Assertions.assertThat(postResponse.getTitle()).as("title").isNotBlank();
            Assertions.assertThat(postResponse.getBody()).as("body").isNotBlank();
            Assertions.assertThat(postResponse.getUserId()).as("userId").isNotNull();
            context.set("postResponse", postResponse);
            return;
        }
        throw new IllegalArgumentException("Unsupported model for deserialization: " + modelName);
    }
}
