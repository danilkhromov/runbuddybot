package org.runbuddy.database;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.*;

/**
 * Created by Danil Khromov.
 */
public class DBManager {
    private static final String connectionUrl = "jdbc:sqlite:runbuddybot.db";

    public void createTables() {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement()) {
            String query = CreationQueries.CREATE_SHOES_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_WEIGHT_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_ARCH_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_TYPE_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_ROAD_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_USERS_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_TEMP_TABLE;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String chatId, String userId) {
        String insert = new QueryBuilder()
                .insertInto("users", "chat_id", "user_id")
                .values(chatId, userId)
                .create();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(insert);
        } catch (SQLiteException e) {
            if (e.getResultCode().equals(SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY)) {
                System.out.println("Record already exists");
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTempResult(String userId, String model, String name, String photoUrl, String url) {
        String insert = new QueryBuilder()
                .insertInto("temp",
                        "chat_id", "user_id", "model", "name", "photo_url", "url", "timestamp")
                .values(userId, model, name, photoUrl, url, "datetime('now')")
                .create();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(insert);
        } catch (SQLiteException e) {
            if (e.getResultCode().equals(SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY)) {
                System.out.println("Record already exists");
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getResult(String userId, String result) {
        String[] conditions = result.split("\\s");
        String select = new QueryBuilder()
                .select("model", "name", "photo_url", "url")
                .from("shoes")
                .where("gender").eq(conditions[0])
                .and("weight").eq(conditions[1])
                .and("arch").eq(conditions[2])
                .and("type").eq(conditions[3])
                .and("road").eq(conditions[4])
                .orderBy("RANDOM()")
                .innerJoin("weight").on("weight.model = shoes.model")
                .innerJoin("arch").on("arch.model = shoes.model")
                .innerJoin("type").on("type.model = shoes.model")
                .innerJoin("road").on("road.model = shoes.model")
                .create();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(select)) {
                while(resultSet.next()) {
                    String model = resultSet.getString("model");
                    String name = resultSet.getString("name");
                    String photoUrl = resultSet.getString("photo_url");
                    String url = resultSet.getString("url");
                    addTempResult(userId, model, name, photoUrl, url);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getShoe(String userId) {
        String[] shoe = new String[0];
        String query = new QueryBuilder()
                .select("model", "name", "photo_url", "url")
                .from("temp")
                .where("user_id").eq(userId)
                .limit(1)
                .create();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                shoe[0] = resultSet.getString("model");
                shoe[1] = resultSet.getString("name");
                shoe[3] = resultSet.getString("photo_url");
                shoe[4] = resultSet.getString("url");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoe;
    }
}
