package edu.wd.pojo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import edu.wd.util.CommonData;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

/**
 */
public class UserController extends Controller {
	

	public void dr() {
		UploadFile f = getFile();
//		ExcelReader reader = ExcelUtil.getReader(f.getUploadPath());
		ExcelReader reader = ExcelUtil.getReader(f.getUploadPath()+"\\"+f.getFileName(),0);//excel调用
		List<List<Object>> readAll = reader.read();
		for (int i = 1; i < readAll.size(); i++) {
			user u = user.te.findFirst("select * from user where username=?",
					readAll.get(i).get(1).toString());
			if (u == null)
				new user().set("name", readAll.get(i).get(0).toString())
						.set("username", readAll.get(i).get(1).toString())
						.set("pass", readAll.get(i).get(2).toString())
						.set("sjh", readAll.get(i).get(3).toString())
						.set("state", readAll.get(i).get(4).toString()).save();
		}
		JSONObject js=new JSONObject();
		js.put("code",0);
		renderJson(js.toJSONString());
	}

	public void dc() {
		List<user> ulist = user.te.find("select * from user");
		List<List<String>> rows = CollUtil.newArrayList();
		List<String> ld = new CollUtil().newArrayList("姓名", "用户名", "密码",
				"手机号", "状态");
		rows.add(ld);
		for (int i = 0; i < ulist.size(); i++) {
			List<String> l = new CollUtil().newArrayList(
					ulist.get(i).getStr("name"),
					ulist.get(i).getStr("username"), 
					ulist.get(i).getStr("pass"), 
					ulist.get(i).getStr("sjh"),
					ulist.get(i).getStr("state"));
			rows.add(l);
		}
		// 通过工具类创建writer
		String name = UUID.randomUUID().toString().replace("-", "");
		ExcelWriter writer = ExcelUtil.getWriter("d:/" + name + ".xls");
		// 通过构造方法创建writer
		// ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

		// 跳过当前行，既第一行，非必须，在此演示用
		writer.passCurrentRow();

		// 合并单元格后的标题行，使用默认标题样式
		// 一次性写出内容
		writer.write(rows);
		// 关闭writer，释放内存
		writer.close();
		renderFile(new File("d:/" + name + ".xls"));
	}

	// 登录
	public void login() {
		user m = user.te.findManagerByusername(getPara("username"));
		if (m == null)
			renderJson(JsonKit.toJson(new CommonData("500", null, "用户不存在")));
		else {
			if (m.getStr("pass").equals(getPara("password"))) {
				setSessionAttr("u", m);
				renderJson(m);
			} else
				renderJson(new CommonData("500", null, "密码不正确"));
		}
	}

	public void cz() {
		user u = user.te.findById(getPara("uid"));
		u.set("jf", u.getInt("jf") + Integer.parseInt(getPara("je"))).update();
		renderJson(new CommonData("200", JsonKit.toJson(u), "111"));
	}

	// 更新密码
	public void updateU() throws UnsupportedEncodingException {
		user.te.findById(getPara(0))
				.set("nickname", URLDecoder.decode(getPara(1), "utf-8"))
				.set("pass", getPara(2)).update();
		renderJson("success");
	}

	public void getalluser() {
		renderJson(user.te.find("select * from user "));
	}

	public void findall() {
		setAttr("list", user.te.find("select * from user "));
		render("/user_list.jsp");
	}

	public void findalls() {
		setAttr("list", user.te.find("select * from user "));
		render("/user_lists.jsp");
	}

	public void toup() {
		setAttr("u", user.te.findById(getPara("id")));
		render("/user_update.jsp");
	}

	public void updateUser() {
		getModel(user.class).update();
		findall();
	}

	// 更新
	public void updates() {
		try {
			getModel(user.class, "", true).update();
			renderJson(new CommonData("500", JsonKit.toJson(user.te
					.findById(getPara("id"))), ""));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(new CommonData("500", "", e.toString()));
		}
	}

	// 添加
	public void add() {

		try {
			getModel(user.class, "", true).save();
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
		render("/user/add.html");
	}

	// 列表
	public void tolist() {
		render("/user/list.html");
	}

	public void glytx() {
		try {
			JPushClient jpushClient = new JPushClient(
					"c668cc144f0142d4a41e2b3c", "450fb771bbf4606a624ec157",
					null, ClientConfig.getInstance());

			jpushClient.sendPush(PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(getPara("id")))
					.setNotification(Notification.alert("管理员提醒")).build());
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}

	public void list() {
		ParamUtil param = new ParamUtil(getRequest());
		Page<user> page;
		if (getPara("key") == null || getPara("key").equals(""))
			page = user.te.paginate(param.getPageNumber(), param.getPageSize(),
					"select * ", "from user ");
		else
			page = user.te.paginate(param.getPageNumber(), param.getPageSize(),
					"select * ", "from user where name=? ", getPara("key"));
		renderJson(JsonKit.toJson(new PageJson<user>("0", "", page)));
	}

	public void alllist() {
		try {
			renderJson(user.te.find("select * from user "));
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
				user.te.deleteById(id);
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
		render("/user/add.html");
	}

	public void get() {
		user info = user.te.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", info)));
	}

	// 更新
	public void update() {
		try {
			getModel(user.class, "", true).update();
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
