package org.runbuddy.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager connectionManager;
    private ComboPooledDataSource cpds;

    private ConnectionManager() throws PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.sqlite.JDBC");
        cpds.setJdbcUrl("jdbc:sqlite:runbuddybot.db");
    }

    public static ConnectionManager getInstance() throws PropertyVetoException {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
            return connectionManager;
        } else {
            return connectionManager;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}
