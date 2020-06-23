package com.frame.myrxjava_retrofit_okhttps.bean;

public class BaseReply {

    private int MsgCode;
    private String Message;

    public int getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(int msgCode) {
        MsgCode = msgCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
