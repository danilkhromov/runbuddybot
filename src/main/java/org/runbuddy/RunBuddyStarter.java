package org.runbuddy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.runbuddy.database.DBManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniil Khromov.
 */
class RunBuddyStarter {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        DBManager.getInstance().createTables();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> future = executorService.scheduleWithFixedDelay(DBManager.getInstance()::cleanTempTable,
                30, 30, TimeUnit.MINUTES);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            future.cancel(false);
            executorService.shutdown();
        }));

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new RunBuddyBot(DBManager.getInstance()));
        } catch (TelegramApiRequestException e) {
            logger.fatal("Failed to register bot", e);
            System.exit(1);
        }
    }
}
