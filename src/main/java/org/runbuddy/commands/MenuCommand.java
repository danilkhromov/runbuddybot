package org.runbuddy.commands;

import org.runbuddy.advancedbot.BotCommand;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

import static org.runbuddy.callbacks.CallbackQueries.*;

/**
 * Created by Daniil Khromov.
 */
public class MenuCommand extends BotCommand {

    public MenuCommand() {
        super("menu", "this command calls menu of the bot");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat) {
        MessageBuilder answer = new MessageBuilder(chat.getId().toString())
                .addButton("Начать", START)
                .addUrl("Интернет магазин", "striderunning.ru")
                .addButton("Адрес магазина", LOCATION)
                .addButton("Журнал Stridemag", "stridemag.ru")
                .addButton("GAIT-анализ", GAIT);
    }
}
