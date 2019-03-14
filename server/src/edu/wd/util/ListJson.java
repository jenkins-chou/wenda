package edu.wd.util;


import java.util.List;

/**
 * 集合Json
 */
@SuppressWarnings("serial")
public class ListJson extends BaseJson {
	
	private List<?> list;
	
	public ListJson() {
	}
	public ListJson(String code,String info) {
		super(code, info);
	}
	public ListJson(String code,String info,List<?> list) {
		super(code, info);
		this.list = list;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
}
