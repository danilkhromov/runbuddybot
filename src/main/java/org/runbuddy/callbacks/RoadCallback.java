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
import static org.runbuddy.database.TemporaryStorage.getTemporaryStorage;

/**
 * Created by Daniil Khromov.
 */
public class RoadCallback extends BotCallback {

    private static final Logger logger = LogManager.getLogger();

    public RoadCallback() {
        super(DISTANCE, SPEED);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        MessageBuilder answer = new MessageBuilder(user.getId().toString());

        try {
            if (getTemporaryStorage().containsEntry(user.getId().toString())) {
                getTemporaryStorage().addAnswer(user.getId().toString(), callbackQuery.getData());
                answer.addButton("Асфальт", ROAD)
                        .addButton("Пересеченная местность", TRAIL)
                        .buttonsInRow(2);
                absSender.sendPhoto(answer.getPhoto(ConfigLoader.getInstace().getProperty("road"),
                        "Асфальт или пересеченная местность?"));
            } else {
                answer.addButton("Пройти заново", RESET)
                        .addButton("Меню", MENU)
                        .buttonsInRow(1);
                absSender.execute(answer.getMessage("Похоже результаты запроса устарели. " +
                        "Пройти тест заново?"));
            }
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            if (e.getMessage().equals("Error deleting message"))
            {
                logger.info("Error deleting message");
            } else {
                logger.error("Could not send RoadCallback", e);
            }
        }
    }
}
