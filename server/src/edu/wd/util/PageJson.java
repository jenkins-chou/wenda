package edu.wd.util;


import java.util.List;
import java.util.Map;
import com.jfinal.plugin.activerecord.Page;

/**
 * 分页Json
 * @Package cn.ensoft.jfinal.callback
 * @ClassName:PageJson
 */
@SuppressWarnings("serial")
public class PageJson<T> extends BaseJson {
	/**
	 * 总共多少记录
	 */
	private int count;
	/**
	 * 当前第几
	 */
	private int page;
	/**
	 * 总共多少
	 */
	private int totalPage;
	/**
	 * 多少记录
	 */
	private int limit;
	private List<T> data;
	private Map<String, Object> otherParam;
	
	public PageJson(String code,String info,Page<T> page) {
		super(code, info);
		this.count = page.getTotalRow();
		this.totalPage = page.getTotalPage();
		this.page = page.getPageNumber();
		this.limit = page.getPageSize();
		this.data = page.getList();
	}
	
	public PageJson(String code,String info,Page<T> page,Map<String, Object> otherParam) {
		super(code, info);
		this.count = page.getTotalRow();
		this.totalPage = page.getTotalPage();
		this.page = page.getPageNumber();
		this.limit = page.getPageSize();
		this.data = page.getList();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Map<String, Object> getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(Map<String, Object> otherParam) {
		this.otherParam = otherParam;
	}
	
}
