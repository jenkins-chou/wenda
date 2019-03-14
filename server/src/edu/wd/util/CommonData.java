package edu.wd.util;


public class CommonData {
    String code,data,msg;

    public CommonData() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	public CommonData(String code, String data, String msg) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}
    
}
