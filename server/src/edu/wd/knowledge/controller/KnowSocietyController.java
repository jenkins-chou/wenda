package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowledgeSocietyModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowSocietyController extends Controller{
	
	public void getAllKownledgeSociety(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<KnowledgeSocietyModel> page = KnowledgeSocietyModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from knowledge_graph_society where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<KnowledgeSocietyModel>("0", "", page)));
	}
	
	public void getKownledgeSocietyById(){
		KnowledgeSocietyModel model = KnowledgeSocietyModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addKownledgeSociety(){
		try {
			KnowledgeSocietyModel model = getModel(KnowledgeSocietyModel.class, "", true);
			
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
	
	public void deleteKownledgeSociety(){
		try {
			KnowledgeSocietyModel model = getModel(KnowledgeSocietyModel.class, "", true);
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
	
	public void deleteKownledgeSocietyList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				KnowledgeSocietyModel model = KnowledgeSocietyModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateKownledgeSociety(){
		renderJson("hello");
	}
	
	public void toHtmlList(){
		render("/know_society/list_know_society.html");
	}
	
	public void toHtmlAdd(){
		render("/know_society/add_know_society.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/know_society/add_know_society.html");
	}
}
