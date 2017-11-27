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
        MessageBuilder answer = new MessageBuilder(user.getId().toString());

        try {
            if (TemporaryStorage.containsEntry(user.getId().toString())) {
                TemporaryStorage.addAnswer(user.getId().toString(), callbackQuery.getData());
                answer.addButton("Высокий подъем", HIGH_FOOT_ARCH)
                        .addButton("Средний подъем", MEDIUM_FOOT_ARCH)
                        .addButton("Низкий подъем", LOW_FOOT_ARCH)
                        .addButton("Плоская стопа", FLAT_FOOT_ARCH)
                        .buttonsInRow(2);
                absSender.sendPhoto(answer.getPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlNlM0cmdSdmxQeUk/view?usp=sharing",
                        "Тип стопы"));
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
