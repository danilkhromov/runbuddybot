package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.config.ConfigLoader;
import org.runbuddy.database.TemporaryStorage;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.*;

/**
 * Created by Daniil Khromov.
 */
public class RoadCallback extends BotCallback {

    public RoadCallback() {
        super(DISTANCE, SPEED);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        MessageBuilder answer = new MessageBuilder(user.getId().toString());

        try {
            if (TemporaryStorage.containsEntry(user.getId().toString())) {
                TemporaryStorage.addAnswer(user.getId().toString(), callbackQuery.getData());
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
            e.printStackTrace();
        }
    }
}
