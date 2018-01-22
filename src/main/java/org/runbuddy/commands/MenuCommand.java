package org.runbuddy.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.runbuddy.advancedbot.BotCommand;
import org.runbuddy.config.ConfigLoader;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.*;

/**
 * Created by Daniil Khromov.
 */
public class MenuCommand extends BotCommand {

    private static final Logger logger = LogManager.getLogger();

    public MenuCommand() {
        super("menu", "this command calls menu of the bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat) {
        MessageBuilder answer = new MessageBuilder(chat.getId().toString())
                .addButton("Пройти тест", START)
                .addUrl("Интернет магазин", ConfigLoader.getInstace().getProperty("store"))
                .addUrl("Адрес магазина", ConfigLoader.getInstace().getProperty("location"))
                .addUrl("Журнал Stridemag", ConfigLoader.getInstace().getProperty("magazine"))
                .addButton("GAIT-анализ", GAIT)
                .addUrl("Расписание тренировок", ConfigLoader.getInstace().getProperty("schedule"));
        try {
            absSender.execute(answer.getMessage("Меню бота:"));
        } catch (TelegramApiException e) {
            logger.error("Could not send MenuCommand", e);
        }
    }
}
