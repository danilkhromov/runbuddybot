package org.runbuddy;

import org.runbuddy.commands.Commands;
import org.runbuddy.callbackqueries.CallbackQueries;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by Daniil Khromov.
 */
class RunBuddyBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        try {
            commandHandler(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "runbuddybot";
    }

    @Override
    public String getBotToken() {
        return "443181452:AAGdpxqqJfejzLkuL3SQL8VSPZ_9rug91TM";
    }

    void commandHandler(Update update) {
        if (update.hasMessage()) {
            Message msg = update.getMessage();
            if (msg.getText().equals(Commands.START_COMMAND)) {
                InlineKeyboardCreator answer = new InlineKeyboardCreator("Привет! " +
                        "Я тебе помогу подобрать подходящие кроссовки для твоих тренировок, " +
                        "если ты ответишь на несколько вопросов.", msg.getChatId().toString(),
                        1);
                answer.addButton("Начать", CallbackQueries.START_QUERY);
                answer.addButton("Меню", CallbackQueries.MENU);
                answer.createKeyboard();
                try {
                    sendMessage(answer.getKeyboard());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            boolean delete = true;

            InlineKeyboardCreator answer;

            switch (callbackQuery) {
                case CallbackQueries.START_QUERY:
                case CallbackQueries.RESET:
                    answer = new InlineKeyboardCreator("Укажи свой пол", chatId, 2);
                    answer.addButton("Мужчина", CallbackQueries.MEN);
                    answer.addButton("Женщина", CallbackQueries.WOMEN);
                    break;
                case CallbackQueries.MEN:
                case CallbackQueries.WOMEN:
                    answer = new InlineKeyboardCreator("Твой вес", chatId, 2);
                    answer.addButton("Меньше 80кг", CallbackQueries.LESS_THAN_80);
                    answer.addButton("Больше 80кг", CallbackQueries.MORE_THAN_80);
                    break;
                case CallbackQueries.LESS_THAN_80:
                case CallbackQueries.MORE_THAN_80:
                    answer = new InlineKeyboardCreator("Тип стопы", chatId,
                            2);
                    answer.addButton("Стопа с высоким подъемом", CallbackQueries.HIGH_FOOT_ARCH);
                    answer.addButton("Стопа со средним подъемом", CallbackQueries.MEDIUM_FOOT_ARCH);
                    answer.addButton("Стопа с низким подъемом", CallbackQueries.LOW_FOOT_ARCH);
                    answer.addButton("Плоская стопа", CallbackQueries.FLAT_FOOT_ARCH);
                    break;
                case CallbackQueries.HIGH_FOOT_ARCH:
                case CallbackQueries.MEDIUM_FOOT_ARCH:
                case CallbackQueries.LOW_FOOT_ARCH:
                case CallbackQueries.FLAT_FOOT_ARCH:
                    answer = new InlineKeyboardCreator("Расстояние или скорость?", chatId, 2);
                    answer.addButton("Расстояние", CallbackQueries.DISTANCE);
                    answer.addButton("Скорость", CallbackQueries.SPEED);
                    break;
                case CallbackQueries.DISTANCE:
                case CallbackQueries.SPEED:
                    answer = new InlineKeyboardCreator("Асфальт или пересеченная местность?", chatId,
                            2);
                    answer.addButton("Асфальт", CallbackQueries.ROAD);
                    answer.addButton("Пересеченная местность", CallbackQueries.OFF_ROAD);
                    break;
                case CallbackQueries.ROAD:
                case CallbackQueries.OFF_ROAD:
                case CallbackQueries.ANOTHER:
                    answer = new InlineKeyboardCreator("Здесь должен быть кроссовок" +
                            "(далее кнопки не работают)", chatId,
                            1);
                    answer.addButton("Другой кроссовок", CallbackQueries.ANOTHER);
                    answer.addButton("Посмотреть в магазине", CallbackQueries.WATCH_IN_STORE);
                    answer.addButton("Пройти заново", CallbackQueries.RESET);
                    answer.addButton("Меню", CallbackQueries.MENU);
                    break;
                default:
                    answer = new InlineKeyboardCreator("Что-то пошло не так, попробуйте снова", chatId);
                    delete = false;
                    break;

            }

            answer.createKeyboard();

            if (delete) {
                try {
                    sendMessage(answer.getKeyboard());
                    deleteMessage(answer.deleteMessage(chatId, messageId));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    sendMessage(answer.getKeyboard());
                    deleteMessage(answer.deleteMessage(chatId, messageId));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
