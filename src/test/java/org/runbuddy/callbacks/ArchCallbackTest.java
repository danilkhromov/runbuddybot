package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.database.TemporaryStorage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ArchCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Message message;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getQueryTimeOut() throws TelegramApiException {
        ArchCallback archCallback = new ArchCallback();

        when(user.getId()).thenReturn(1);
        when(callbackQuery.getMessage()).thenReturn(message);

        archCallback.execute(absSender, user, callbackQuery);

        verify(user, atLeastOnce()).getId();
        verify(absSender).execute(any(SendMessage.class));
    }

}