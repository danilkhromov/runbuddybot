package org.runbuddy.database;

public class TableCleaner implements Runnable {

    @Override
    public void run() {
        DBManager dbManager = new DBManager();
        dbManager.cleanTempTable();
    }
}
