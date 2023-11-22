package dev.nottek.voidBot.core.sql;

import dev.nottek.voidBot.core.handlers.ConfigHandler;

import java.io.ObjectInputFilter;
import java.sql.SQLException;
import java.sql.Statement;

import static dev.nottek.voidBot.core.sql.SQLConnection.getConnection;

public class SQLCreateStatement {
    public void createStatement() {
        try (final Statement statement = getConnection().createStatement()) {
            final String defaultPrefix = ConfigHandler.get("prefix");

            // language=SQLite
            statement.execute("CREATE TABLE IF NOT EXISTS guild_settings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "guild_id VARCHAR(20) NOT NULL," +
                    "prefix VARCHAR(255) NOT NULL DEFAULT '" + defaultPrefix + "'," +
                    "moderated VARCHAR(10) NOT NULL" + ");");

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
