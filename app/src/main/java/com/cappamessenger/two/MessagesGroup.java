package com.cappamessenger.two;

import com.google.firebase.database.Exclude;

/**
 * Created by Dkflbc on 15.03.17.
 */

public class MessagesGroup {
    private String message;
    private String sender;
    private String recipient;

    private int mRecipientOrSenderStatus;

    public MessagesGroup() {
    }

    public MessagesGroup(String message, String sender, String recipient) {
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
    }


    public void setRecipientOrSenderStatus(int recipientOrSenderStatus) {
        this.mRecipientOrSenderStatus = recipientOrSenderStatus;
    }


    public String getMessage() {
        return message;
    }

    public String getRecipient(){
        return recipient;
    }

    public String getSender(){
        return sender;
    }

    @Exclude
    public int getRecipientOrSenderStatus() {
        return mRecipientOrSenderStatus;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
