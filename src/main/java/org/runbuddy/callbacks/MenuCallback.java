package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.*;

/**
 * Created by Daniil Khromov.
 */
public class MenuCallback extends BotCallback {

    public MenuCallback() {
        super(MENU);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                .addButton("Пройти тест", START)
                .addUrl("Интернет магазин", "striderunning.ru")
                //this shit must be done properly
                .addUrl("Адрес магазина", "www.google.ru/maps/place/Stride+Running+Store/@55.7232466,37.584498,17z/data=!3m1!4b1!4m5!3m4!1s0x46b54b75a015a25f:0xc26503188a713001!8m2!3d55.7232466!4d37.5866867")
                .addUrl("Журнал Stridemag", "stridemag.ru")
                .addButton("GAIT-анализ", GAIT)
                .addUrl("Расписание тренировок", "stridemag.ru/striderunningclub/the-schedule-runs");
        try {
            absSender.execute(answer.getMessage("Меню бота:"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
