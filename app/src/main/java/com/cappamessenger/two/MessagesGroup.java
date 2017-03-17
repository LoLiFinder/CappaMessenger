package com.cappamessenger.two;

/**
 * Created by Dkflbc on 15.03.17.
 */

public class MessagesGroup {
    private String name;
    private String messagesGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessagesGroup() {
        return messagesGroup;
    }

    public void setMessagesGroup(String messagesGroup) {
        this.messagesGroup = messagesGroup;
    }

    public MessagesGroup() {
    }

    public MessagesGroup(String name, String messagesGroup) {
        this.name = name;
        this.messagesGroup = messagesGroup;
    }
}
