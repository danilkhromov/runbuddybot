package org.runbuddy.commands;

import org.runbuddy.advancedbot.BotCommand;
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

    public MenuCommand() {
        super("menu", "this command calls menu of the bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat) {
        MessageBuilder answer = new MessageBuilder(chat.getId().toString())
                .addButton("Пройти тест", START)
                .addUrl("Интернет магазин", "striderunning.ru")
                .addUrl("Адрес магазина", "www.google.ru/maps/place/Stride+Running+Store/@55.7232466,37.584498,17z/data=!3m1!4b1!4m5!3m4!1s0x46b54b75a015a25f:0xc26503188a713001!8m2!3d55.7232466!4d37.5866867")
                .addUrl("Журнал Stridemag", "stridemag.ru")
                .addButton("GAIT-анализ", GAIT)
                .addUrl("Расписание тренировок", "stridemag.ru/striderunningclub/the-schedule-runs");
        try {
            absSender.execute(answer.getMessage("Меню бота:"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
