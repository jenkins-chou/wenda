package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowledgeHumanityModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowHumanityController extends Controller{
	
	public void getAllKownledgeHumanity(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<KnowledgeHumanityModel> page = KnowledgeHumanityModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from knowledge_graph_humanity where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<KnowledgeHumanityModel>("0", "", page)));
	}
	
	public void getKownledgeHumanityById(){
		KnowledgeHumanityModel model = KnowledgeHumanityModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addKownledgeHumanity(){
		try {
			KnowledgeHumanityModel model = getModel(KnowledgeHumanityModel.class, "", true);
			
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
	
	public void deleteKownledgeHumanity(){
		try {
			KnowledgeHumanityModel model = getModel(KnowledgeHumanityModel.class, "", true);
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
	
	public void deleteKownledgeHumanityList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				KnowledgeHumanityModel model = KnowledgeHumanityModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateKownledgeHumanity(){
		renderJson("hello");
	}
	
	public void toHtmlList(){
		render("/know_humanity/list_know_humanity.html");
	}
	
	public void toHtmlAdd(){
		render("/know_humanity/add_know_humanity.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/know_humanity/add_know_humanity.html");
	}
}
