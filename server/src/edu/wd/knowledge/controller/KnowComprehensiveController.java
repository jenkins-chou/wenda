package edu.wd.knowledge.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowledgeComprehensiveModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowComprehensiveController extends Controller{
	
	public void getAllKownledgeComprehensive(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<KnowledgeComprehensiveModel> page = KnowledgeComprehensiveModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", " from knowledge_graph_comprehensive where del != 'delete'");
		System.out.println(page.toString());
		renderJson(JsonKit.toJson(new PageJson<KnowledgeComprehensiveModel>("0", "", page)));
	}
	
	public void getKownledgeComprehensiveById(){
		KnowledgeComprehensiveModel model = KnowledgeComprehensiveModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addKownledgeComprehensive(){
		try {
			KnowledgeComprehensiveModel model = getModel(KnowledgeComprehensiveModel.class, "", true);
			
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
	
	public void deleteKownledgeComprehensive(){
		try {
			KnowledgeComprehensiveModel model = getModel(KnowledgeComprehensiveModel.class, "", true);
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
	
	public void deleteKownledgeComprehensiveList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				KnowledgeComprehensiveModel model = KnowledgeComprehensiveModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateKownledgeComprehensive(){
		renderJson("hello");
	}
	
	public void toHtmlList(){
		render("/know_comprehensive/list_know_comprehensive.html");
	}
	
	public void toHtmlAdd(){
		render("/know_comprehensive/add_know_comprehensive.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/know_comprehensive/add_know_comprehensive.html");
	}
}