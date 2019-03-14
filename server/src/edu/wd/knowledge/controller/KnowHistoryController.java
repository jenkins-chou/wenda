package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowledgeHistoryModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowHistoryController extends Controller{
	
	public void getAllKownledgeHistory(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<KnowledgeHistoryModel> page = KnowledgeHistoryModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from knowledge_graph_history where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<KnowledgeHistoryModel>("0", "", page)));
	}
	
	public void getKownledgeHistoryById(){
		KnowledgeHistoryModel model = KnowledgeHistoryModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addKownledgeHistory(){
		try {
			KnowledgeHistoryModel model = getModel(KnowledgeHistoryModel.class, "", true);
			
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
	
	public void deleteKownledgeHistory(){
		try {
			KnowledgeHistoryModel model = getModel(KnowledgeHistoryModel.class, "", true);
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
	
	public void deleteKownledgeHistoryList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				KnowledgeHistoryModel model = KnowledgeHistoryModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateKownledgeHistory(){
		renderJson("hello");
	}
	
	public void toHtmlList(){
		render("/know_history/list_know_history.html");
	}
	
	public void toHtmlAdd(){
		render("/know_history/add_know_history.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/know_history/add_know_history.html");
	}
}
