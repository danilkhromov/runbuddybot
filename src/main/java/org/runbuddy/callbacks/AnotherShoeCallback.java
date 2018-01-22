package org.runbuddy.callbacks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.database.Manager;
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

    private static final Logger logger = LogManager.getLogger();

    private Manager dbManager;

    public AnotherShoeCallback(Manager dbManager) {
        super(ANOTHER);
        this.dbManager = dbManager;
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        String userId = user.getId().toString();

        MessageBuilder answer = new MessageBuilder(userId);

        try {
            if (getTemporaryStorage().containsEntry(userId)) {
                String result = getTemporaryStorage().getAnswers(userId);
                String shoe[] = dbManager.getShoe(user.getId().toString(), result);

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
                        .buttonsInRow(1);
                absSender.execute(answer.getMessage("Похоже результаты запроса устарели. " +
                        "Пройти тест заново?"));
            }
        } catch (TelegramApiException e) {
            if (e.getMessage().equals("Error deleting message"))
            {
                logger.info("Error deleting message");
            } else {
                logger.error("Could not send AnotherShoeCallback",e);
            }
        }
    }
}
