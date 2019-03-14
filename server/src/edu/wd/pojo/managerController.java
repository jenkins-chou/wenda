package edu.wd.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.util.CommonData;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

/**
 * BlogController  sql 与业务辑写Model Service 中，不要写在 Controller
 */
public class managerController extends Controller {

	// 登录
	public void login() {
		manager m = manager.te.findManagerByusername(getPara("username"));
		if (m.getStr("password").equals(getPara("password"))) {
			setSessionAttr("m", m);
			if (m.getStr("state").equals("超级管理员"))
				render("/ht/index.html");
			else
				render("/ht/index2.html");
		} else
			render("/ht/login.html");
	}

	// 更新密码
	public void update() {
		getModel(manager.class, "").update();
		render("/");

	}

	// �?��
	public void exit() {
		removeSessionAttr("m");
		render("/login.html");
	}

	// 添加
	public void add() {

		try {
			getModel(manager.class, "", true).save();
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
		render("/manager/add.html");
	}

	// 列表
	public void tolist() {
		render("/manager/list.html");
	}

	public void list() {
		ParamUtil param = new ParamUtil(getRequest());
		Page<manager> page = manager.te.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from manager ");
		renderJson(JsonKit.toJson(new PageJson<manager>("0", "", page)));
	}

	public void alllist() {
		try {
			renderJson(manager.te.find("select * from manager "));
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
				manager.te.deleteById(id);
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
		render("/manager/add.html");
	}

	public void get() {
		manager info = manager.te.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", info)));
	}

	// 更新
	public void updates() {
		try {
			getModel(manager.class, "", true).update();
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

	public void toup() {
		setAttr("m", getSessionAttr("m"));
		render("/update.html");
	}

}
