package org.runbuddy;

import org.runbuddy.database.DBManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniil Khromov.
 * TODO admin access
 * TODO message sending method
 * TODO unit tests for builder classes and DBManager
 * TODO all exception must be handled properly
 */
class RunBuddyStarter {
    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        DBManager dbManager = new DBManager();
        dbManager.createTables();

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new RunBuddyBot());
        } catch (TelegramApiRequestException e) {
            System.err.println("Failed to register bot: " + e.getMessage());
            System.exit(1);
        }
    }
}
