package com.w.wenda.adapater;

public class MessageModel {
    public int type = MineMsg;//默认为自己发的消息
    public static final int MineMsg = 0;
    public static final int ServerMsgText = 1;
    public static final int ServerMsgUrl = 2;
    public static final int ServerMsgImage = 3;
    //这里可以自己自行添加消息类型

    public String message;
    public String webUrl;
    public String imageUrl;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageModel(){

    }

    public MessageModel(int type,String message){
        this.type = type;
        this.message = message;
    }

    public MessageModel(int type,String message,String webUrl,String imageUrl){
        this.type = type;
        this.message = message;
        this.webUrl = webUrl;
        this.imageUrl = imageUrl;
    }
    @Override
    public String toString() {
        return "MessageModel{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
