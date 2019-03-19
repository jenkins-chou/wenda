package edu.wd.model;

public class MessageModel {
	
	public static final int MineMsgText = 1001;
    public static final int MineMsgEmoji = 1002;

    public static final int ServerMsgText = 2001;
    public static final int ServerMsgUrl = 2002;
    public static final int ServerMsgImage = 2003;
    public static final int ServerMsgEmoji = 2004;
    public static final int ServerMsgTextList = 2005;
    
    public static final String key_type = "type";
    public static final String key_message = "message";
    public static final String key_webUrl = "webUrl";
    public static final String key_imageUrl = "imageUrl";
    public static final String key_emojiUnicode = "emojiUnicode";
    public static final String key_message_list = "messageList";
    
    public static final String answer_default = "您在说什么呢？";//默认作答内容
    public static final String answer_default_ask_again = "我不明白呢，请问可以重新说一遍吗?";//默认重新询问语句
    
    public static final String answer_default_comprehensive = "很抱歉，我不太理解呢，请问可以重新说一遍吗?";//默认重新询问语句
    public static final String answer_default_humanity = "很抱歉，这个我不太理解呢，请问可以重新说一遍吗?";//默认重新询问语句
    public static final String answer_default_society = "很抱歉，这个不太理解呢，请问可以重新说一遍吗?";//默认重新询问语句
    public static final String answer_default_history= "很抱歉，这个我不太理解呢，请问可以重新说一遍吗?";//默认重新询问语句
    public static final String answer_default_natural = "很抱歉，这个我不太理解呢，请问可以重新说一遍吗?";//默认重新询问语句
    
    public static final String answer_error = "系统出错啦 -_-";//系统出错作答内容
    
}
