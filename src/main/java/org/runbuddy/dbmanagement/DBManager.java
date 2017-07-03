package org.runbuddy.dbmanagement;

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

    public void getResult(String result) {
        String[] conditions = result.split("\\s");
        SelectBuilder queryBuilder = new SelectBuilder("model_name, url")
                .from("shoes")
                .where(conditions[1] + " and "
                        + conditions[2] + " and "
                        + conditions[3] + " and "
                        + conditions[4] + " and "
                        + conditions[5])
                .orderBy("RANDOM()")
                .innerJoin("weight ON weight.model = shoes.model")
                .innerJoin("arch ON arch.model = shoes.model")
                .innerJoin("type ON type.model = shoes.model")
                .innerJoin("road ON road.model = shoes.mode");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:runbuddybot.db");
        Statement statement = connection.createStatement()) {
            statement.executeQuery(queryBuilder.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
