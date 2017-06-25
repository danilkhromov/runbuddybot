package org.runbuddy;

import org.runbuddy.commands.Commands;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniil Khromov.
 */
class RunBuddyBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        try {
            commadHandler(update);
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

    void commadHandler(Update update) {
        if (update.hasMessage()) {
            Message msg = update.getMessage();
            if (msg.getText().equals(Commands.START_COMMAND)) {
                MessageCreator answer = new MessageCreator("Привет! " +
                        "Я тебе помогу подобрать подходящие кроссовки для твоих тренировок, " +
                        "если ты ответишь на несколько вопросов.", msg.getChatId());
                InlineKeyboardCreator keyboard = new InlineKeyboardCreator(1, 2);
                keyboard.addButton("Начать", "start_test");
                keyboard.addButton("Меню", "menu");
                answer.addInlineKeyboard(keyboard.createKeyboard());
                try {
                    sendMessage(answer.getAnswer());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            if (update.getCallbackQuery().getData().equals("start_test")) {
                SendMessage answer = new SendMessage();
                answer.setText("success")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId());
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(chatId)
                        .setMessageId(messageId);
                try {
                    sendMessage(answer);
                    deleteMessage(deleteMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
