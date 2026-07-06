package com.company.api.db;

import com.company.api.config.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbClient implements AutoCloseable {
    private final Connection connection;

    public DbClient() {
        if (!ConfigManager.getBoolean("db.enabled", false)) {
            throw new IllegalStateException("DB validation is disabled. Set db.enabled=true and configure DB credentials.");
        }
        try {
            this.connection = DriverManager.getConnection(
                    ConfigManager.get("db.url"),
                    ConfigManager.get("db.username"),
                    ConfigManager.get("db.password")
            );
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to connect to database", e);
        }
    }

    public String queryForString(String sql, Object parameter) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, parameter);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("DB query failed: " + sql, e);
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
