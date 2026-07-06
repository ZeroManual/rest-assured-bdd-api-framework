package com.company.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private static final Properties PROPERTIES = new Properties();
    private static final String ENV = System.getProperty("env", "qa").toLowerCase();

    static {
        loadProperties();
        overrideFromSystemProperties();
    }

    private ConfigManager() {
    }

    private static void loadProperties() {
        String path = "config/" + ENV + ".properties";
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new IllegalStateException("Config file not found: " + path);
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config file for env: " + ENV, e);
        }
    }

    private static void overrideFromSystemProperties() {
        System.getProperties().forEach((key, value) -> {
            String stringKey = String.valueOf(key);
            if (stringKey.startsWith("base.") || stringKey.startsWith("auth.") || stringKey.startsWith("api.") || stringKey.startsWith("db.")) {
                PROPERTIES.setProperty(stringKey, String.valueOf(value));
            }
        });
    }

    public static String getEnv() {
        return ENV;
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String value = PROPERTIES.getProperty(key);
        return value == null || value.isBlank() ? defaultValue : Integer.parseInt(value);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = PROPERTIES.getProperty(key);
        return value == null || value.isBlank() ? defaultValue : Boolean.parseBoolean(value);
    }
}
