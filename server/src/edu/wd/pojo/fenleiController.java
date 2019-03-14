package edu.wd.pojo;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.util.CommonData;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class fenleiController extends Controller {
	// 添加
	public void add() {
		try {
			fenlei fenlei = getModel(fenlei.class, "", true);
			
			System.out.println("fenlei:"+fenlei);
			
			getModel(fenlei.class, "", true).save();
			JSONObject js = new JSONObject();
			js.put("code", "200");
			renderJson(js.toJSONString());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			JSONObject js = new JSONObject();
			js.put("code", 500);
			js.put("msg", e.toString());
			renderJson(js.toJSONString());
		}
	}

	public void toadd() {
		render("/fenlei/add.html");
	}

	// 列表
	public void tolist() {
		render("/fenlei/list.html");
	}

	public void list() {
		ParamUtil param = new ParamUtil(getRequest());
		Page<fenlei> page = fenlei.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from fenlei ");
		renderJson(JsonKit.toJson(new PageJson<fenlei>("0", "", page)));
	}

	public void alllist() {
		try {
			renderJson(fenlei.dao.find("select * from fenlei "));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(new CommonData("500", "", e.toString()));
		}
	}

	// 删除
	public void del() {
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				fenlei.dao.deleteById(id);
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}

	// 查看
	public void toget() {
		setAttr("id", getPara("id"));
		render("/fenlei/add.html");
	}

	public void get() {
		fenlei info = fenlei.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", info)));
	}

	// 更新
	public void update() {
		try {
			getModel(fenlei.class, "", true).update();
			JSONObject js = new JSONObject();
			js.put("code", "200");
			renderJson(js.toJSONString());
		} catch (Exception e) {
			// TODO: handle exception
			JSONObject js = new JSONObject();
			js.put("code", 500);
			js.put("msg", e.toString());
			renderJson(js.toJSONString());
		}
	}

}
