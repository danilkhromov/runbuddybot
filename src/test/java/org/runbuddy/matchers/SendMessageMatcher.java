package org.runbuddy.matchers;

import org.mockito.ArgumentMatcher;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class SendMessageMatcher implements ArgumentMatcher<SendMessage> {

    private SendMessage expectedMessage;

    public SendMessageMatcher(SendMessage expectedMessage) {
        this.expectedMessage = expectedMessage;
    }

    @Override
    public boolean matches(SendMessage sendMessage) {
        return expectedMessage.getChatId().equals(sendMessage.getChatId()) &&
                expectedMessage.getText().equals(sendMessage.getText()) &&
                sendMessage.getChatId() != null &&
                sendMessage.getText() != null;
    }
}
