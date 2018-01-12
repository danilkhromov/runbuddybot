package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.database.TemporaryStorage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Daniil Khromov.
 */
public class RoadCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Message message;

    private RoadCallback roadCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        roadCallback= new RoadCallback();
    }

    @Test
    public void getRoadCallback() throws TelegramApiException {
        String photoUrl = "AgADAgADKKkxG44WwEmdz4WTXIPAw3DMDw4ABBHuerDWu7LUCh4EAAEC";
        String photoCaption = "Асфальт или пересеченная местность?";

        ArgumentCaptor<SendPhoto> sendPhotoArgumentCaptor = ArgumentCaptor.forClass(SendPhoto.class);

        TemporaryStorage.getTemporaryStorage().addEntry("1");

        when(user.getId()).thenReturn(1);
        when(callbackQuery.getMessage()).thenReturn(message);

        roadCallback.execute(absSender, user, callbackQuery);

        verify(user, times(3)).getId();
        verify(absSender).sendPhoto(sendPhotoArgumentCaptor.capture());
        verify(absSender).execute(any(DeleteMessage.class));

        assertEquals(photoUrl, sendPhotoArgumentCaptor.getValue().getPhoto());
        assertEquals(photoCaption, sendPhotoArgumentCaptor.getValue().getCaption());
    }

    @Test
    public void getQueryTimeOut() throws TelegramApiException {
        when(user.getId()).thenReturn(2);
        when(callbackQuery.getMessage()).thenReturn(message);

        roadCallback.execute(absSender, user, callbackQuery);

        verify(user, times(2)).getId();
        verify(absSender).execute(any(SendMessage.class));
        verify(absSender).execute(any(DeleteMessage.class));
    }
}