package org.runbuddy.database;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.*;

/**
 * Created by Danil Khromov.
 */
public class DBManager {

    public void createTables() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:runbuddybot.db");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String chatId, String userId) {
        InsertBuilder insertUser = new InsertBuilder("users", "chat_id", "user_id")
                .values(chatId, userId);
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:runbuddybot.db");
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertUser.getInsert());
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

    public void getResult(String result) {
        SelectBuilder queryBuilder = new SelectBuilder("model, name, photo_url, url")
                .from("shoes")
                .where(result)
                .orderBy("RANDOM()")
                .innerJoin("weight").on("weight.model = shoes.model")
                .innerJoin("arch").on("arch.model = shoes.model")
                .innerJoin("type").on("type.model = shoes.model")
                .innerJoin("road").on("road.model = shoes.model");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:runbuddybot.db");
        Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(queryBuilder.getQuery())) {
                while(resultSet.next()) {

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
