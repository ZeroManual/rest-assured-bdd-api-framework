package com.company.api.steps;

import com.company.api.context.ScenarioContext;
import com.company.api.db.DbClient;
import io.cucumber.java.en.And;
import org.assertj.core.api.Assertions;

public class DbValidationSteps {
    private final ScenarioContext context;

    public DbValidationSteps(ScenarioContext context) {
        this.context = context;
    }

    @And("database query {string} with response field {string} should match response field {string}")
    public void databaseQueryWithResponseFieldShouldMatchResponseField(String sql, String parameterJsonPath, String expectedJsonPath) throws Exception {
        Object parameter = context.getResponse().jsonPath().get(parameterJsonPath);
        String expected = context.getResponse().jsonPath().getString(expectedJsonPath);
        try (DbClient dbClient = new DbClient()) {
            String actual = dbClient.queryForString(sql, parameter);
            Assertions.assertThat(actual).as("DB value should match API response value").isEqualTo(expected);
        }
    }
}
