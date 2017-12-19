package org.runbuddy;

import org.runbuddy.database.DBManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniil Khromov.
 */
class RunBuddyStarter {
    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        DBManager.createTables();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(DBManager::cleanTempTable, 30, 30, TimeUnit.MINUTES);

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
