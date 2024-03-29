package com.example.praca_dyplomowa;

public class Message {

    String displayName;
    String messageTime;
    String message;
    String avatar;

    public String getAvatar() {
        return avatar;
    }

    public Message(String displayName, String messageTime, String message, String avatar) {
        this.displayName = displayName;
        this.messageTime = messageTime;
        this.message = message;
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessage() {
        return message;
    }

    public Message(String displayName, String messageTime, String message) {
        this.displayName = displayName;
        this.messageTime = messageTime;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
