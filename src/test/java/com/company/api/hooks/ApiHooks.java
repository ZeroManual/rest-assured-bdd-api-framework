package com.company.api.hooks;

import com.company.api.config.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiHooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiHooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        LOGGER.info("Starting scenario: {} | env={} | base.uri={}",
                scenario.getName(), ConfigManager.getEnv(), System.getProperty("base.uri", ConfigManager.get("base.uri")));
    }

    @After
    public void afterScenario(Scenario scenario) {
        LOGGER.info("Finished scenario: {} | status={}", scenario.getName(), scenario.getStatus());
    }
}
