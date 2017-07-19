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
                dbManager.addUser(msg.getFrom().getId().toString());
                try {
                    MessageHandler messageHandler = new MessageHandler(answer);
                    messageHandler.sendAnswer(answer);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            String userId = String.valueOf(update.getCallbackQuery().getFrom().getId());
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            MessageBuilder answer;

            switch (callbackQuery) {
                case START_QUERY:
                case RESET:
                    TemporaryStorage.addEntry(userId);
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Мужчина", MEN)
                            .addButton("Женщина", WOMEN)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlT2g0QTlYNE41SFE/view?usp=sharing",
                                    "Укажи свой пол");
                    break;
                case MEN:
                case WOMEN:
                    TemporaryStorage.addAnswer(userId, callbackQuery);
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Меньше 80кг", LESS_THAN_80)
                            .addButton("Больше 80кг", MORE_THAN_80)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlMVFIOElrWldEdWM/view?usp=sharing"
                                    ,"Твой вес");
                    break;
                case LESS_THAN_80:
                case MORE_THAN_80:
                    TemporaryStorage.addAnswer(userId, callbackQuery);
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
                    TemporaryStorage.addAnswer(userId, callbackQuery);
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Расстояние", DISTANCE)
                            .addButton("Скорость", SPEED)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlRWVGcXdhQk5NUmc/view?usp=sharing",
                                    "Расстояние или скорость?");
                    break;
                case DISTANCE:
                case SPEED:
                    TemporaryStorage.addAnswer(userId, callbackQuery);
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Асфальт", ROAD)
                            .addButton("Пересеченная местность", OFF_ROAD)
                            .buttonsInRow(2)
                            .setPhoto("https://drive.google.com/file/d/0B-cUz7XDzfvlbkpGTVcwZDlUOUE/view?usp=sharing",
                                    "Асфальт или пересеченная местность?");
                    break;
                case ROAD:
                case OFF_ROAD:
                    TemporaryStorage.addAnswer(userId, callbackQuery);
                    String result = TemporaryStorage.getAnswers(userId);
                    DBManager dbManager = new DBManager();
                    dbManager.getResult(userId, result);
                case ANOTHER:
                    String shoe[] = new DBManager().getShoe(userId);
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Другой кроссовок", ANOTHER)
                            .addUrl("Посмотреть в магазине", shoe[2])
                            .addButton("Пройти заново", RESET)
                            .addButton("Меню", MENU)
                            .buttonsInRow(1)
                            .setPhoto(shoe[1], shoe[0]);
                    break;
                case MENU:
                    answer = new MessageBuilder(chatId, messageId)
                            .addButton("Пройти тест", RESET)
                            .addUrl("Интернет магазин", "striderunning.ru")
                            .addButton("Адрес магазина", LOCATION)
                            .addUrl("Журнал StrideMag", "stridemag.ru")
                            .addButton("GAIT-анализ", GAIT)
                            .addButton("Расписание тренировок", SCHEDULE)
                            .buttonsInRow(1)
                            .setText("Меню бота:");
                    break;
                case SCHEDULE:
                    answer = new MessageBuilder(chatId, messageId)
                            .addUrl("Посмотреть", "blog.striderunning.ru/расписание-пробежек")
                            .addButton("Меню", MENU)
                            .setText("Актуальное расписание на эту неделю:");
                    break;
                case LOCATION:
                    answer = new MessageBuilder(chatId, messageId)
                            .addUrl("Показать на карте", "www.google.ru/maps/place/Stride+Running+Store/@55.7232466,37.584498,17z/data=!3m1!4b1!4m5!3m4!1s0x46b54b75a015a25f:0xc26503188a713001!8m2!3d55.7232466!4d37.5866867")
                            .addButton("Меню", MENU)
                            .setText("Наш магазин:\nг.Москва, Фрунзенская набережная, 32");
                    break;
                case GAIT:
                    answer = new MessageBuilder(chatId, messageId)
                            .addUrl("Показать на карте", "www.google.ru/maps/place/Stride+Running+Store/@55.7232466,37.584498,17z/data=!3m1!4b1!4m5!3m4!1s0x46b54b75a015a25f:0xc26503188a713001!8m2!3d55.7232466!4d37.5866867")
                            .addButton("Меню", MENU)
                            .setText("Вы можете совершено бесплатно пройти GAIT-анализ в нашем магазине по адресу:\n" +
                                    "г. Москва, Фрунзенская набережная, 32");
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
