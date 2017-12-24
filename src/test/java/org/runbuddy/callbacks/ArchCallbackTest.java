package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArchCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;

    SendMessage message = new SendMessage()
            .setText("Похоже результаты запроса устарели. " +
                    "Пройти тест заново?");

    @Test
    public void getArchCallback() {
        ArchCallback archCallback = mock(ArchCallback.class);
        //when(user.getId()).thenReturn(Integer.valueOf("1"));
        //when(callbackQuery.getMessage().getMessageId()).thenReturn(0);
        archCallback.execute(absSender, user, callbackQuery);
    }

}