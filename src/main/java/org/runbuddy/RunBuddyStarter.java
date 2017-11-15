package org.runbuddy;

import org.runbuddy.database.DBManager;
import org.runbuddy.database.TableCleaner;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniil Khromov.
 * TODO admin access
 * TODO unit tests for all this new sweet classes
 * TODO all exceptions must be handled properly
 */
class RunBuddyStarter {
    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        DBManager dbManager = new DBManager();
        dbManager.createTables();

        TableCleaner tableCleaner = new TableCleaner();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleWithFixedDelay(tableCleaner, 5, 5, TimeUnit.MINUTES);

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
