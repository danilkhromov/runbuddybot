package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.database.Manager;
import org.runbuddy.matchers.SendPhotoMatcher;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class GenderCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Message message;
    @Mock private Manager dbManager;

    private GenderCallback genderCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        genderCallback = new GenderCallback(dbManager);
    }

    @Test
    public void getGenderCallback() throws TelegramApiException{
        SendPhoto expectedPhoto = new MessageBuilder("1")
                .getPhoto("AgADAgADJKkxG44WwEn0GKWcXpNei6TfDw4ABKPFsEYGsZZ8QCYEAAEC", "Укажи свой пол");

        when(user.getId()).thenReturn(1);
        when(callbackQuery.getMessage()).thenReturn(message);

        genderCallback.execute(absSender, user, callbackQuery);

        verify(user, times(3)).getId();
        verify(absSender).sendPhoto(argThat(new SendPhotoMatcher(expectedPhoto)));
        verify(absSender).execute(any(DeleteMessage.class));
    }
}