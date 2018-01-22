package org.runbuddy.callbacks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.config.ConfigLoader;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.*;

/**
 * Created by Daniil Khromov.
 */
public class MenuCallback extends BotCallback {

    private static final Logger logger = LogManager.getLogger();

    public MenuCallback() {
        super(MENU);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addButton("Пройти тест", START)
                .addUrl("Интернет магазин", ConfigLoader.getInstace().getProperty("store"))
                .addUrl("Адрес магазина", ConfigLoader.getInstace().getProperty("location"))
                .addUrl("Журнал Stridemag", ConfigLoader.getInstace().getProperty("magazine"))
                .addButton("GAIT-анализ", GAIT)
                .addUrl("Расписание тренировок", ConfigLoader.getInstace().getProperty("schedule"));
        try {
            absSender.execute(answer.getMessage("Меню бота:"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            if (e.getMessage().equals("Error deleting message"))
            {
                logger.info("Error deleting message");
            } else {
                logger.error("Could not send MenuCallback", e);
            }
        }
    }
}
