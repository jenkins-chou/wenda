package com.w.wenda.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MessageModel {

    //这里可以自己自行添加消息类型
    public static final int MineMsgText = 1001;
    public static final int MineMsgEmoji = 1002;

    public static final int ServerMsgText = 2001;
    public static final int ServerMsgUrl = 2002;
    public static final int ServerMsgImage = 2003;
    public static final int ServerMsgEmoji = 2004;


    @Id(autoincrement = true)
    public Long id;
    public int type = MineMsgText;//默认为自己发的消息
    public String code;
    public String message;
    public int emojiUnicode;
    public String webUrl;
    public String imageUrl;
    public String user_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getEmojiUnicode() {
        return emojiUnicode;
    }

    public void setEmojiUnicode(int emojiUnicode) {
        this.emojiUnicode = emojiUnicode;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MessageModel(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageModel(int type, int emojiUnicode) {
        this.type = type;
        this.emojiUnicode = emojiUnicode;
    }

    @Generated(hash = 1359317287)
    public MessageModel(Long id, int type, String code, String message,
            int emojiUnicode, String webUrl, String imageUrl, String user_id) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.message = message;
        this.emojiUnicode = emojiUnicode;
        this.webUrl = webUrl;
        this.imageUrl = imageUrl;
        this.user_id = user_id;
    }

    @Generated(hash = 1699352037)
    public MessageModel() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "id=" + id +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", emojiUnicode=" + emojiUnicode +
                ", webUrl='" + webUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
