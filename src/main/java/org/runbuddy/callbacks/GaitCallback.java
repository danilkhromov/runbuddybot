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

import static org.runbuddy.callbacks.CallbackQueries.GAIT;
import static org.runbuddy.callbacks.CallbackQueries.MENU;

/**
 * Created by Daniil Khromov.
 */
public class GaitCallback extends BotCallback {

    private static final Logger logger = LogManager.getLogger();

    public GaitCallback() {
        super(GAIT);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addUrl("Показать на карте", ConfigLoader.getInstace().getProperty("location"))
                .addButton("Меню", MENU);
        try {
            absSender.execute(answer.getMessage(
                    "Вы можете совершено бесплатно пройти GAIT-анализ в нашем магазине по адресу:\n" +
                    " г.Москва, Фрунзенская набережная, 32"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            if (e.getMessage().equals("Error deleting message"))
            {
                logger.info("Error deleting message");
            } else {
                logger.error("Could not send GaitCallback", e);
            }
        }
    }
}
