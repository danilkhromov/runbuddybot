package org.runbuddy.commands;

import org.runbuddy.advancedbot.BotCommand;
import org.runbuddy.database.Manager;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.MENU;
import static org.runbuddy.callbacks.CallbackQueries.START;

/**
 * Created by Danil Khromov.
 */
public class StartCommand extends BotCommand {

    private Manager dbManager;

    public StartCommand(Manager dbManager) {
        super("start", "this command starts bot");
        this.dbManager = dbManager;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat) {
        MessageBuilder answer = new MessageBuilder(chat.getId().toString())
                .addButton("Начать", START)
                .addButton("Меню", MENU);
        dbManager.addUser(user.getId().toString());
        try {
            absSender.execute(answer.getMessage("Привет! " +
                    "Я тебе помогу подобрать подходящие кроссовки для твоих тренировок, " +
                    "если ты ответишь на несколько вопросов."));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
