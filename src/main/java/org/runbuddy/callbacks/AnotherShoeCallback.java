package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.database.DBManager;
import org.runbuddy.database.TemporaryStorage;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.*;

/**
 * Created by Daniil Khromov.
 */
public class AnotherShoeCallback extends BotCallback {

    public AnotherShoeCallback() {
        super(ANOTHER);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        String userId = user.getId().toString();

        MessageBuilder answer = new MessageBuilder(userId);

        try {
            if (TemporaryStorage.containsEntry(userId)) {
                String result = TemporaryStorage.getAnswers(userId);
                String shoe[] = DBManager.getShoe(user.getId().toString(), result);

                if (shoe[1] != null) {
                    answer.addButton("Другой кроссовок", ANOTHER)
                            .addUrl("Посмотреть в магазине", shoe[2])
                            .addButton("Пройти заново", RESET)
                            .addButton("Меню", MENU)
                            .buttonsInRow(1);
                    absSender.sendPhoto(answer.getPhoto(shoe[1], shoe[0]));
                } else {
                    answer.addButton("Пройти заново", RESET)
                            .addButton("Меню", MENU)
                            .buttonsInRow(1);
                    absSender.execute(answer.getMessage("Похоже у нас закончились подходящие модели. " +
                            "Пройти тест заново?"));
                }
            } else {
                absSender.execute(getExpiredMessage(answer));
            }
            //absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    SendMessage getExpiredMessage(MessageBuilder answer) {
        return answer.addButton("Пройти заново", RESET)
                .addButton("Меню", MENU)
                .buttonsInRow(1)
                .getMessage("Похоже результаты запроса устарели. " +
                        "Пройти тест заново?");
    }
}
