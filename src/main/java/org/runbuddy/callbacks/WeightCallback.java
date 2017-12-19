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
public class WeightCallback extends BotCallback {

    public WeightCallback() {
        super(MEN, WOMEN);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        MessageBuilder answer = new MessageBuilder(user.getId().toString());

        try {
            if (TemporaryStorage.containsEntry(user.getId().toString())) {
                TemporaryStorage.addAnswer(user.getId().toString(), callbackQuery.getData());
                answer.addButton("Меньше 80кг", LESS_THAN_80)
                        .addButton("Больше 80кг", MORE_THAN_80)
                        .buttonsInRow(2);
                absSender.sendPhoto(answer.getPhoto(ConfigLoader.getInstace().getProperty("weight"),
                        "Твой вес"));
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
