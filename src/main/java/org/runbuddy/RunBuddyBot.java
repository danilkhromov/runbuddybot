package org.runbuddy;

import org.runbuddy.commands.Commands;
import org.runbuddy.callbackqueries.CallbackQueries;
import org.runbuddy.messagehandling.MessageBuilder;
import org.runbuddy.messagehandling.MessageHandler;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by Daniil Khromov.
 */
public class RunBuddyBot extends TelegramLongPollingBot {

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

    void commandHandler(Update update) throws TelegramApiException {
        if (update.hasMessage()) {
            Message msg = update.getMessage();
            if (msg.getText().equals(Commands.START_COMMAND)) {
                MessageBuilder answer = new MessageBuilder(msg.getChatId().toString())
                        .addButton("Начать", CallbackQueries.START_QUERY)
                        .addButton("Меню", CallbackQueries.MENU)
                        .createKeyboard()
                        .setText("Привет! " +
                                        "Я тебе помогу подобрать подходящие кроссовки для твоих тренировок, " +
                                        "если ты ответишь на несколько вопросов.");
                try {
                    MessageHandler messageHandler = new MessageHandler(answer);
                    messageHandler.sendAnswer(answer);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            MessageBuilder answer;

            switch (callbackQuery) {
                case CallbackQueries.START_QUERY:
                case CallbackQueries.RESET:
                    answer = new MessageBuilder(chatId)
                            .addButton("Мужчина", CallbackQueries.MEN)
                            .addButton("Женщина", CallbackQueries.WOMEN)
                            .buttonsInRow(2)
                            .createKeyboard()
                            .setText("Укажи свой пол");
                    break;
                case CallbackQueries.MEN:
                case CallbackQueries.WOMEN:
                    answer = new MessageBuilder(chatId)
                            .addButton("Меньше 80кг", CallbackQueries.LESS_THAN_80)
                            .addButton("Больше 80кг", CallbackQueries.MORE_THAN_80)
                            .buttonsInRow(2)
                            .createKeyboard()
                            .setText("Твой вес");
                    break;
                case CallbackQueries.LESS_THAN_80:
                case CallbackQueries.MORE_THAN_80:
                    answer = new MessageBuilder(chatId)
                            .addButton("Стопа с высоким подъемом", CallbackQueries.HIGH_FOOT_ARCH)
                            .addButton("Стопа со средним подъемом", CallbackQueries.MEDIUM_FOOT_ARCH)
                            .addButton("Стопа с низким подъемом", CallbackQueries.LOW_FOOT_ARCH)
                            .addButton("Плоская стопа", CallbackQueries.FLAT_FOOT_ARCH)
                            .buttonsInRow(2)
                            .createKeyboard()
                            .setText("Тип стопы");
                    break;
                case CallbackQueries.HIGH_FOOT_ARCH:
                case CallbackQueries.MEDIUM_FOOT_ARCH:
                case CallbackQueries.LOW_FOOT_ARCH:
                case CallbackQueries.FLAT_FOOT_ARCH:
                    answer = new MessageBuilder(chatId)
                            .addButton("Расстояние", CallbackQueries.DISTANCE)
                            .addButton("Скорость", CallbackQueries.SPEED)
                            .buttonsInRow(2)
                            .createKeyboard()
                            .setText("Расстояние или скорость?");
                    break;
                case CallbackQueries.DISTANCE:
                case CallbackQueries.SPEED:
                    answer = new MessageBuilder(chatId)
                            .addButton("Асфальт", CallbackQueries.ROAD)
                            .addButton("Пересеченная местность", CallbackQueries.OFF_ROAD)
                            .buttonsInRow(2)
                            .createKeyboard()
                            .setText("Асфальт или пересеченная местность?");
                    break;
                case CallbackQueries.ROAD:
                case CallbackQueries.OFF_ROAD:
                case CallbackQueries.ANOTHER:
                    answer = new MessageBuilder(chatId)
                            .addButton("Другой кроссовок", CallbackQueries.ANOTHER)
                            .addButton("Посмотреть в магазине", CallbackQueries.WATCH_IN_STORE)
                            .addButton("Пройти заново", CallbackQueries.RESET)
                            .addButton("Меню", CallbackQueries.MENU)
                            .buttonsInRow(1)
                            .createKeyboard()
                            .setText("Здесь должен быть кроссовок далее кнопки не работают");
                    break;
                default:
                    answer = new MessageBuilder(chatId)
                            .setText("Что-то пошло не так, попробуйте снова");
                    break;
            }

            try {
                MessageHandler messageHandler = new MessageHandler(answer);
                messageHandler.sendAnswer(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
