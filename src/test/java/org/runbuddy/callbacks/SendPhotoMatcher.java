package org.runbuddy.callbacks;

import org.mockito.ArgumentMatcher;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

class SendPhotoMatcher implements ArgumentMatcher<SendPhoto> {

    private SendPhoto expectedPhoto;

    SendPhotoMatcher(SendPhoto expectedPhoto) {
        this.expectedPhoto = expectedPhoto;
    }

    @Override
    public boolean matches(SendPhoto sendPhoto) {
        return expectedPhoto.getCaption().equals(sendPhoto.getCaption()) &&
                expectedPhoto.getPhoto().equals(sendPhoto.getPhoto()) &&
                expectedPhoto.getChatId().equals(sendPhoto.getChatId()) &&
                sendPhoto.getCaption() != null &&
                sendPhoto.getPhoto() != null &&
                sendPhoto.getChatId() != null;
    }
}
