package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowledgeNaturalModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowNaturalController extends Controller{
	
	public void getAllKownledgeNatural(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<KnowledgeNaturalModel> page = KnowledgeNaturalModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from knowledge_graph_natural where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<KnowledgeNaturalModel>("0", "", page)));
	}
	
	public void getKownledgeNaturalById(){
		KnowledgeNaturalModel model = KnowledgeNaturalModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addKownledgeNatural(){
		try {
			KnowledgeNaturalModel model = getModel(KnowledgeNaturalModel.class, "", true);
			
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
	
	public void deleteKownledgeNatural(){
		try {
			KnowledgeNaturalModel model = getModel(KnowledgeNaturalModel.class, "", true);
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
	
	public void deleteKownledgeNaturalList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				KnowledgeNaturalModel model = KnowledgeNaturalModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateKownledgeNatural(){
		renderJson("hello");
	}
	
	public void toHtmlList(){
		render("/know_natural/list_know_natural.html");
	}
	
	public void toHtmlAdd(){
		render("/know_natural/add_know_natural.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/know_natural/add_know_natural.html");
	}
}
