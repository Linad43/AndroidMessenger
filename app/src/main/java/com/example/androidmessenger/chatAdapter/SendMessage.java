package com.example.androidmessenger.chatAdapter;

import androidx.annotation.NonNull;

public class SendMessage implements ElementChat {
    private String textChat;

    public SendMessage(String textChat) {
        this.textChat = textChat;
    }

    public SendMessage() {
    }

    @NonNull
    public String getTextChat() {
        return textChat;
    }

    public void setTextChat(String textChat) {
        this.textChat = textChat;
    }
}
