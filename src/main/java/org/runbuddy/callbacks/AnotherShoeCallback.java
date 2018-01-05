package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.database.DBManager;
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
public class AnotherShoeCallback extends BotCallback {

    public AnotherShoeCallback() {
        super(ANOTHER);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        String userId = user.getId().toString();

        MessageBuilder answer = new MessageBuilder(userId);

        try {
            if (getTemporaryStorage().containsEntry(userId)) {
                String result = getTemporaryStorage().getAnswers(userId);
                String shoe[] = DBManager.getInstance().getShoe(user.getId().toString(), result);

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
                answer.addButton("Пройти заново", RESET)
                        .addButton("Меню", MENU)
                        .buttonsInRow(1)
                        .getMessage("Похоже результаты запроса устарели. " +
                                "Пройти тест заново?");
                absSender.execute(answer.getMessage("Похоже результаты запроса устарели. " +
                        "Пройти тест заново?"));
            }
            //absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
