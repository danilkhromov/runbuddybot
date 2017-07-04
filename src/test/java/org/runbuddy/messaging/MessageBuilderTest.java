package org.runbuddy.messaging;

import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniil Khromov.
 * 1) усложнить и дописать тест фото
 * 2) написать тест текста
 */
public class MessageBuilderTest {

    @Test
    public void testPhoto() {
        String chatId = "1";
        String label1 = "label1";
        String label2 = "label2";
        SendPhoto photo = new MessageBuilder(chatId)
                .addButton(label1, "result1")
                .addButton(label2, "result2")
                .createKeyboard()
                .setPhoto("url", "caption")
                .getPhoto();
        Assert.assertEquals(chatId, photo.getChatId());
        List<List<InlineKeyboardButton>> keyboard = ((InlineKeyboardMarkup) photo.getReplyMarkup()).getKeyboard();
        assertEquals("2 rows of buttons expected", 2, keyboard.size());
        assertEquals(1, keyboard.get(0).size());
        assertEquals(label1, keyboard.get(0).get(0).getText());
        assertEquals(1, keyboard.get(1).size());
        assertEquals(label2, keyboard.get(1).get(0).getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeRowSize() {
        new MessageBuilder("")
                .buttonsInRow(-1)
                .createKeyboard()
                .getPhoto();
    }
}