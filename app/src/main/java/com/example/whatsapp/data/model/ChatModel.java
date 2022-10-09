package com.example.whatsapp.data.model;

import java.util.PrimitiveIterator;

public class ChatModel {

    private String Msg;
    private Boolean meOrNot;

    public ChatModel() {
    }

    public ChatModel(String msg , Boolean meOrNot) {
        this.Msg = msg;
        this.meOrNot=meOrNot;
    }

    public Boolean getMeOrNot() {
        return meOrNot;
    }

    public void setMeOrNot(Boolean meOrNot) {
        this.meOrNot = meOrNot;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
