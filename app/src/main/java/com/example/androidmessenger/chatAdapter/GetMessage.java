package com.example.androidmessenger.chatAdapter;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class GetMessage implements ElementChat {
    public GetMessage(String textChat) {
        this.textChat = textChat;
    }

    private String textChat;

    public GetMessage() {
    }

    @NonNull
    public String getTextChat() {
        return textChat;
    }

    public void setTextChat(String textChat) {
        this.textChat = textChat;
    }
}
