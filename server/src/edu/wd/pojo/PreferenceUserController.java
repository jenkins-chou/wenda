package edu.wd.pojo;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.PreferenceUserModel;
import edu.wd.model.PreferenceUserModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class PreferenceUserController extends Controller{
	
	public void getAllPreferenceUser(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<PreferenceUserModel> page = PreferenceUserModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from preference_user where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<PreferenceUserModel>("0", "", page)));
	}
	
	public void getPreferenceById(){
		PreferenceUserModel model = PreferenceUserModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addPreferenceUser(){
		try {
			PreferenceUserModel model = getModel(PreferenceUserModel.class, "", true);
			
			System.out.println("model:"+model);
			
			model.save();
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
	
	public void deletePreferenceUser(){
		try {
			PreferenceUserModel model = getModel(PreferenceUserModel.class, "", true);
			model.set("del", "delete");
			model.update();
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
	
	public void deletePreferenceUserList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				PreferenceUserModel model = PreferenceUserModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updatePreferenceUser(){
		renderJson("hello");
	}
}
