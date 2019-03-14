package edu.wd.util;


import com.alibaba.fastjson.annotation.JSONField;

@SuppressWarnings("serial")
public class BaseJson implements  java.io.Serializable {
	
	private String  code;
    @JSONField(name="msg") 
	private String  msg;
	
	public BaseJson() {
	}
	public BaseJson(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
