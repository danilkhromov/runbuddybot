package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.config.ConfigLoader;
import org.runbuddy.database.Manager;
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
public class GenderCallback extends BotCallback {

    private Manager dbManager;

    public GenderCallback(Manager dbManager) {
        super(START, RESET);
        this.dbManager = dbManager;
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        TemporaryStorage.getTemporaryStorage().addEntry(user.getId().toString());
        dbManager.deleteViewedShoes(user.getId().toString());

        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addButton("Мужчина", MEN)
                .addButton("Женщина", WOMEN)
                .buttonsInRow(2);
        try {
            absSender.sendPhoto(answer.getPhoto(ConfigLoader.getInstace().getProperty("gender"),
                    "Укажи свой пол"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
