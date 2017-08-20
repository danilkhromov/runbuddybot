package org.runbuddy.commands;

import org.runbuddy.advancedbot.BotCommand;
import org.runbuddy.database.DBManager;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.*;

/**
 * Created by Danil Khromov.
 */
public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "this command starts bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat) {
        MessageBuilder answer = new MessageBuilder(chat.getId().toString())
                .addButton("Начать", START)
                .addButton("Меню", MENU)
                .setText("Привет! " +
                        "Я тебе помогу подобрать подходящие кроссовки для твоих тренировок, " +
                        "если ты ответишь на несколько вопросов.");
        DBManager dbManager = new DBManager();
        dbManager.addUser(user.getId().toString());
        try {
            absSender.execute(answer.getMessage());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
