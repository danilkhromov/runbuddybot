package org.runbuddy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Danil Khromov.
 */
public class DBManager {
    //private Connection connection;
    //private Statement statement;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getResult(String result) {
        SelectBuilder queryBuilder = new SelectBuilder("model_name, url")
                .from("shoes")
                .where(result)
                .orderBy("RANDOM()")
                .innerJoin("weight").on("weight.model = shoes.model")
                .innerJoin("arch").on("arch.model = shoes.model")
                .innerJoin("type").on("type.model = shoes.model")
                .innerJoin("road").on("road.model = shoes.model");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:runbuddybot.db");
        Statement statement = connection.createStatement()) {
            statement.executeQuery(queryBuilder.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
