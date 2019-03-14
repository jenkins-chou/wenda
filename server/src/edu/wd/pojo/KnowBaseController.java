package edu.wd.pojo;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.KnowBaseModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowBaseController extends Controller{
	
	public void getAllKownledge(){
//		System.out.println(KnowBaseModel.dao.find("select * from knowledge_base "));
//	
//		renderJson(KnowBaseModel.dao.find("select * from knowledge_base "));
//		
		ParamUtil param = new ParamUtil(getRequest());
		Page<KnowBaseModel> page = KnowBaseModel.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from knowledge_base where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<KnowBaseModel>("0", "", page)));
	}
	
	public void getKownledgeById(){
		KnowBaseModel model = KnowBaseModel.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addKownledge(){
		try {
			KnowBaseModel model = getModel(KnowBaseModel.class, "", true);
			
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
	
	public void deleteKownledge(){
		try {
			KnowBaseModel model = getModel(KnowBaseModel.class, "", true);
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
	
	public void deleteKownledgeList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				KnowBaseModel model = KnowBaseModel.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateKownledge(){
		renderJson("hello");
	}
	
	public void toHtmlList(){
		render("/know/list.html");
	}
	
	public void toHtmlAdd(){
		System.out.println("-------");
		render("/know/add.html");
	}
	
	public void toHtmlModify(){
		setAttr("id", getPara("id"));
		render("/know/add.html");
	}
}
