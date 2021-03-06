package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.matchers.SendMessageMatcher;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GaitCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Message message;

    private GaitCallback gaitCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gaitCallback = new GaitCallback();
    }

    @Test
    public void getGaitCallback() throws TelegramApiException{
        SendMessage expectedMessage = new MessageBuilder("1")
                .getMessage("Вы можете совершено бесплатно пройти GAIT-анализ в нашем магазине по адресу:\n" +
                                " г.Москва, Фрунзенская набережная, 32");

        when(user.getId()).thenReturn(1);
        when(callbackQuery.getMessage()).thenReturn(message);

        gaitCallback.execute(absSender, user, callbackQuery);

        verify(user).getId();
        verify(absSender).execute(argThat(new SendMessageMatcher(expectedMessage)));
        verify(absSender).execute(any(DeleteMessage.class));
    }
}