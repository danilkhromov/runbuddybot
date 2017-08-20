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
 * Created by Danil Khromov.
 */
public class StartCallback extends BotCallback {

    public StartCallback() {
        super("start", "reset");
    }
    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        TemporaryStorage.addEntry(user.getId().toString());
        MessageBuilder answer = new MessageBuilder(user.getId().toString(), callbackQuery.getMessage().getMessageId())
                .addButton("Мужчина", MEN)
                .addButton("Женщина", WOMEN)
                .buttonsInRow(2)
                .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlT2g0QTlYNE41SFE/view?usp=sharing",
                        "Укажи свой пол");
        try {
            absSender.sendPhoto(answer.getPhoto());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
