package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
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
public class TypeCallback extends BotCallback {

    public TypeCallback() {
        super(HIGH_FOOT_ARCH, MEDIUM_FOOT_ARCH, LOW_FOOT_ARCH, FLAT_FOOT_ARCH);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        TemporaryStorage.addAnswer(user.getId().toString(), callbackQuery.getData());
        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addButton("Расстояние", DISTANCE)
                .addButton("Скорость", SPEED)
                .buttonsInRow(2);
        try {
            absSender.sendPhoto(answer.getPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlRWVGcXdhQk5NUmc/view?usp=sharing",
                    "Расстояние или скорость?"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
