package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.Mockito.*;
import static org.runbuddy.callbacks.CallbackQueries.MENU;
import static org.runbuddy.callbacks.CallbackQueries.RESET;

/**
 * Created by Daniil Khromov.
 */
public class AnotherShoeCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void queryTimeOut() throws TelegramApiException {
        AnotherShoeCallback anotherShoeCallback = new AnotherShoeCallback();

        when(user.getId()).thenReturn(1);

        anotherShoeCallback.execute(absSender, user, callbackQuery);

        verify(user).getId();
        verify(absSender).execute(any(SendMessage.class));
        verify(absSender, never()).sendPhoto(any(SendPhoto.class));
    }
}