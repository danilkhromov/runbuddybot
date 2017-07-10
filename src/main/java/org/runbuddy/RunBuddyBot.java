package org.runbuddy;

import org.runbuddy.commands.Commands;
import org.runbuddy.database.DBManager;
import org.runbuddy.database.TemporaryStorage;
import org.runbuddy.messaging.MessageBuilder;
import org.runbuddy.messaging.MessageHandler;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.runbuddy.callbacks.CallbackQueries.*;

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
                        .addButton("Начать", START_QUERY)
                        .addButton("Меню", MENU)
                        .setText("Привет! " +
                                        "Я тебе помогу подобрать подходящие кроссовки для твоих тренировок, " +
                                        "если ты ответишь на несколько вопросов.");
                DBManager dbManager = new DBManager();
                dbManager.addUser(msg.getChatId().toString(), msg.getFrom().getId().toString());
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
                case START_QUERY:
                    TemporaryStorage.addEntry(String.valueOf(update.getMessage().getFrom().getId()));
                case RESET:
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Мужчина", MEN)
                            .addButton("Женщина", WOMEN)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlT2g0QTlYNE41SFE/view?usp=sharing",
                                    "Укажи свой пол");
                    break;
                case MEN:
                case WOMEN:
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Меньше 80кг", LESS_THAN_80)
                            .addButton("Больше 80кг", MORE_THAN_80)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlMVFIOElrWldEdWM/view?usp=sharing"
                                    ,"Твой вес");
                    break;
                case LESS_THAN_80:
                case MORE_THAN_80:
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Стопа с высоким подъемом", HIGH_FOOT_ARCH)
                            .addButton("Стопа со средним подъемом", MEDIUM_FOOT_ARCH)
                            .addButton("Стопа с низким подъемом", LOW_FOOT_ARCH)
                            .addButton("Плоская стопа", FLAT_FOOT_ARCH)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlNlM0cmdSdmxQeUk/view?usp=sharing",
                                    "Тип стопы");
                    break;
                case HIGH_FOOT_ARCH:
                case MEDIUM_FOOT_ARCH:
                case LOW_FOOT_ARCH:
                case FLAT_FOOT_ARCH:
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Расстояние", DISTANCE)
                            .addButton("Скорость", SPEED)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlRWVGcXdhQk5NUmc/view?usp=sharing",
                                    "Расстояние или скорость?");
                    break;
                case DISTANCE:
                case SPEED:
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Асфальт", ROAD)
                            .addButton("Пересеченная местность", OFF_ROAD)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlbkpGTVcwZDlUOUE/view?usp=sharing",
                                    "Асфальт или пересеченная местность?");
                    break;
                case ROAD:
                case OFF_ROAD:
                case ANOTHER:
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Другой кроссовок", ANOTHER)
                            .addButton("Посмотреть в магазине", WATCH_IN_STORE)
                            .addButton("Пройти заново", RESET)
                            .addButton("Меню", MENU)
                            .buttonsInRow(1)
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
