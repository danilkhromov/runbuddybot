package org.runbuddy.database;

import java.sql.*;
import java.util.Queue;

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
