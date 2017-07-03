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

    public void selectFromDB(QueryBuilder queryBuilder) {
        try (Connection connection = DriverManager.getConnection("");
        Statement statement = connection.createStatement()) {
            statement.executeQuery(queryBuilder.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
