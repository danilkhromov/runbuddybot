package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.database.DBManager;
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
public class ResultCallback extends BotCallback {

    public ResultCallback() {
        super(ROAD, OFF_ROAD);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        TemporaryStorage.addAnswer(user.getId().toString(), callbackQuery.getData());
        String result = TemporaryStorage.getAnswers(user.getId().toString());

        DBManager dbManager = new DBManager();
        dbManager.getResult(user.getId().toString(), result);

        String shoe[] = new DBManager().getShoe(user.getId().toString());

        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addButton("Другой кроссовок", ANOTHER)
                .addUrl("Посмотреть в магазине", shoe[2])
                .addButton("Пройти заново", RESET)
                .addButton("Меню", MENU)
                .buttonsInRow(1);
        try {
            absSender.sendPhoto(answer.getPhoto(shoe[1], shoe[0]));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
