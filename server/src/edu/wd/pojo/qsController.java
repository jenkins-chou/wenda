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

public class qsController extends Controller {
	// 添加
	public void add() {

		try {
			getModel(question.class, "", true).save();
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
		setAttr("list", fenlei.dao.find("select * from fenlei"));
		render("/question/add.html");
	}

	// 列表
	public void tolist() {
		render("/question/list.html");
	}

	public void list() {
		ParamUtil param = new ParamUtil(getRequest());
		Page<question> page = question.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from question ");
		renderJson(JsonKit.toJson(new PageJson<question>("0", "", page)));
	}

	public void alllist() {
		try {
			renderJson(new CommonData("200", JsonKit.toJson(question.dao
					.find("select * from question ")), "成功"));
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
				question.dao.deleteById(id);
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}

	// 查看
	public void toget() {
		setAttr("list", fenlei.dao.find("select * from fenlei"));
		setAttr("id", getPara("id"));
		render("/question/add.html");
	}

	public void get() {
		question info = question.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", info)));
	}

	// 更新
	public void update() {
		try {
			getModel(question.class, "", true).update();
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
