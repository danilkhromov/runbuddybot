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
public class WeightCallback extends BotCallback {

    public WeightCallback() {
        super(MEN, WOMEN);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        TemporaryStorage.addAnswer(user.getId().toString(), callbackQuery.getData());
        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addButton("Меньше 80кг", LESS_THAN_80)
                .addButton("Больше 80кг", MORE_THAN_80)
                .buttonsInRow(2);
        try {
            absSender.sendPhoto(answer.getPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlMVFIOElrWldEdWM/view?usp=sharing"
                    ,"Твой вес"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
