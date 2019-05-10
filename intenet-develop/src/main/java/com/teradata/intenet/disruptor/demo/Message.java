package com.teradata.intenet.disruptor.demo;

public class Message {

    private String id;

    private String msg;

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
