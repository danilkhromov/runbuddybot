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
public class ArchCallback extends BotCallback {

    public ArchCallback() {
        super(LESS_THAN_80, MORE_THAN_80);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        TemporaryStorage.addAnswer(user.getId().toString(), callbackQuery.getData());
        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addButton("Стопа с высоким подъемом", HIGH_FOOT_ARCH)
                .addButton("Стопа со средним подъемом", MEDIUM_FOOT_ARCH)
                .addButton("Стопа с низким подъемом", LOW_FOOT_ARCH)
                .addButton("Плоская стопа", FLAT_FOOT_ARCH)
                .buttonsInRow(2);
        try {
            absSender.sendPhoto(answer.getPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlNlM0cmdSdmxQeUk/view?usp=sharing",
                    "Тип стопы"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
