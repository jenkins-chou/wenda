package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowledgeMappingModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowMappingController extends Controller{
	
	public void getAllKownledgeMapping(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<KnowledgeMappingModel> page = KnowledgeMappingModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from knowledge_mapping where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<KnowledgeMappingModel>("0", "", page)));
	}
	
	public void getKownledgeMappingById(){
		KnowledgeMappingModel model = KnowledgeMappingModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addKownledgeMapping(){
		try {
			KnowledgeMappingModel model = getModel(KnowledgeMappingModel.class, "", true);
			
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
	
	public void deleteKownledgeMapping(){
		try {
			KnowledgeMappingModel model = getModel(KnowledgeMappingModel.class, "", true);
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
	
	public void deleteKownledgeMappingList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				KnowledgeMappingModel model = KnowledgeMappingModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateKownledgeMapping(){
		try {
			KnowledgeMappingModel model = getModel(KnowledgeMappingModel.class, "", true);
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
		render("/know_mapping/list_know_mapping.html");
	}
	
	public void toHtmlAdd(){
		render("/know_mapping/add_know_mapping.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/know_mapping/add_know_mapping.html");
	}

}
