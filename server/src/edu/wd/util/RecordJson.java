package edu.wd.util;


import java.util.Map;

/**
 * 对象Json
 * @Package cn.eninesoft.jfinal.api
 * @ClassName:RecordJson
 */
@SuppressWarnings("serial")
public class RecordJson extends BaseJson {
	
	private Object rd;
	private Map<String, Object> otherParams;
	
	public Object getRd() {
		return rd;
	}
	public void setRd(Object rd) {
		this.rd = rd;
	}
	public Map<String, Object> getOtherParams() {
		return otherParams;
	}
	public void setOtherParams(Map<String, Object> otherParams) {
		this.otherParams = otherParams;
	}
	
	public RecordJson() {
	}
	public RecordJson(String code,String info) {
		super(code, info);
	}
	public RecordJson(String code,String info,Object rd) {
		super(code, info);
		this.rd = rd;
	}
	
	public RecordJson(String code,String info,Object rd, Map<String, Object> otherParams) {
		super(code, info);
		this.rd = rd;
		this.otherParams = otherParams;
	}
}
