package dev.nottek.voidBot.core.sql.utils;

import dev.nottek.voidBot.core.sql.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLUtils {

    public static void setGuildSettings(long guildId, String DEFAULT_PREFIX) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO guild_settings(guild_id, prefix, moderated) VALUES (?, ?, ?)")) {
            insertStatement.setString(1, String.valueOf(guildId));
            insertStatement.setString(2, DEFAULT_PREFIX);
            insertStatement.setString(3, "false");
            insertStatement.executeUpdate();
            insertStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeGuildSettings(long guildId) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement("DELETE FROM guild_settings WHERE guild_id = ?")) {
            insertStatement.setString(1, String.valueOf(guildId));
            insertStatement.executeUpdate();
            insertStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPrefix(long guildId, String DEFAULT_PREFIX) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")) {
            prepareStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet rs = prepareStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("prefix");
                }
            }

            prepareStatement.close();

            try (final Connection connection2 = SQLConnection.getConnection();
                 PreparedStatement insertStatement = connection2.prepareStatement("INSERT INTO guild_settings(guild_id, prefix) VALUES (?, ?)")) {
                insertStatement.setString(1, String.valueOf(guildId));
                insertStatement.setString(2, DEFAULT_PREFIX);
                insertStatement.executeUpdate();
                insertStatement.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return DEFAULT_PREFIX;
    }

    public static String getModerated(long guildId) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("SELECT moderated FROM guild_settings WHERE guild_id = ?")) {
            prepareStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet rs = prepareStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("moderated");
                }
            }

            prepareStatement.close();

            try (final Connection connection2 = SQLConnection.getConnection();
                 PreparedStatement insertStatement = connection2.prepareStatement("INSERT INTO guild_settings(guild_id, moderated) VALUES (?, ?)")) {
                insertStatement.setString(1, String.valueOf(guildId));
                insertStatement.setBoolean(2, false);
                insertStatement.executeUpdate();
                insertStatement.close();
            }


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return "false";
    }

    public static ArrayList<String> getCurseWords(long guildId) {
        ArrayList<String> res = new ArrayList<>();
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("SELECT word FROM curse_words WHERE guild_id = ?")) {
            prepareStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet rs = prepareStatement.executeQuery()) {
                while (rs.next()) {
                    res.add(rs.getString("word"));
                }
            }

            prepareStatement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return res;
    }

    public static void updateModeration(long guildId, String mod) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE guild_settings SET moderated = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, mod);
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCurseWord(long guildId, String curseWord) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO curse_words (guild_id, word) VALUES (?, ?)")) {

            preparedStatement.setString(1, String.valueOf(guildId));
            preparedStatement.setString(2, curseWord);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeCurseWord(long guildId, String word) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM curse_words WHERE guild_id = ? AND word = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));
            preparedStatement.setString(2, word);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLoggerChannel(long guildId, long channelId) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE guild_settings SET channelId = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(channelId));
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeLoggerChannel(long guildId) {
        try (final Connection connection = SQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE guild_settings SET channelId = null WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
