package edu.wd.util;


/**
 * Json
 * @Package cn.eninesoft.jfinal.api
 * @ClassName:RecordJson
 */
@SuppressWarnings("serial")
public class StatusJson  extends BaseJson{
	
	private boolean status;
	
	public StatusJson(String code,String info) {
		super(code, info);
	}
	public StatusJson(String code,String info,boolean status) {
		super(code, info);
		this.status = status;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
