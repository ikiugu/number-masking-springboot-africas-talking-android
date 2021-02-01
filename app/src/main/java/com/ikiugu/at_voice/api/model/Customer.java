package com.ikiugu.at_voice.api.model;

public class Customer {
    private String name;
    private String phoneNumber;
    private boolean contacted = false;
    private boolean calling = false;
    private String comment;

    public Customer() {
    }

    public Customer(String name, String phoneNumber, boolean contacted, boolean calling, String comment) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.contacted = contacted;
        this.calling = calling;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isContacted() {
        return contacted;
    }

    public void setContacted(boolean contacted) {
        this.contacted = contacted;
    }

    public boolean isCalling() {
        return calling;
    }

    public void setCalling(boolean calling) {
        this.calling = calling;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
