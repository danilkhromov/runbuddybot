package org.runbuddy.callbacks;

import org.runbuddy.advancedbot.BotCallback;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.GAIT;
import static org.runbuddy.callbacks.CallbackQueries.MENU;

/**
 * Created by Daniil Khromov.
 */
public class GaitCallback extends BotCallback {

    public GaitCallback() {
        super(GAIT);
    }

    @Override
    public void execute(AbsSender absSender, User user, CallbackQuery callbackQuery) {
        MessageBuilder answer = new MessageBuilder(user.getId().toString())
                //this shit is also should be done properly
                .addUrl("Показать на карте", "www.google.ru/maps/place/Stride+Running+Store/@55.7232466,37.584498,17z/data=!3m1!4b1!4m5!3m4!1s0x46b54b75a015a25f:0xc26503188a713001!8m2!3d55.7232466!4d37.5866867")
                .addButton("Меню", MENU);
        try {
            absSender.execute(answer.getMessage(
                    "Вы можете совершено бесплатно пройти GAIT-анализ в нашем магазине по адресу:\n" +
                    " г.Москва, Фрунзенская набережная, 32"));
            absSender.execute(answer.getDelete(callbackQuery.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
