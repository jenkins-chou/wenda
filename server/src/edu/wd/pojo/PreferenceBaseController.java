package edu.wd.pojo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowledgeComprehensiveModel;
import edu.wd.model.PreferenceBaseModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class PreferenceBaseController extends Controller{
	
	public void getAllPreference(){
		List<PreferenceBaseModel> list = PreferenceBaseModel.dao.find("select * from preference_base where del != 'delete'");
		ParamUtil param = new ParamUtil(getRequest());
		Page<PreferenceBaseModel> page = PreferenceBaseModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from preference_base where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<PreferenceBaseModel>("0", "", page)));
	}
	
	public void getAllPreferenceToMobile(){
		List<PreferenceBaseModel> list = PreferenceBaseModel.dao.find("select * from preference_base where del != 'delete'");
		renderJson(JsonKit.toJson(list));
	}
	
	public void getPreferenceById(){
		PreferenceBaseModel model = PreferenceBaseModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addPreference(){
		try {
			PreferenceBaseModel model = getModel(PreferenceBaseModel.class, "", true);
			
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
	
	public void deletePreference(){
		try {
			PreferenceBaseModel model = getModel(PreferenceBaseModel.class, "", true);
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
	
	public void deletePreferenceList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				PreferenceBaseModel model = PreferenceBaseModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updatePreference(){
		try {
			PreferenceBaseModel model = getModel(PreferenceBaseModel.class, "", true);
			System.out.println(model.toJson());
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
	
	public void toHtmlList(){
		render("/preference/list_preference.html");
	}
	
	public void toHtmlAdd(){
		System.out.println("-------");
		render("/preference/add_preference.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/preference/add_preference.html");
	}
}
