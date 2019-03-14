package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.model.IntelligentAnswerRecord;
import edu.wd.model.IntelligentAnswerRecord;
import edu.wd.model.IntelligentAnswerRecord;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class IntelAnswerRecordController extends Controller{
	
	public void getAllAnswerRecord(){
		ParamUtil param = new ParamUtil(getRequest());
		Page<IntelligentAnswerRecord> page = IntelligentAnswerRecord.dao.paginate(param.getPageNumber(),
				param.getPageSize(), "select * ", "from intelligent_answer_record where del != 'delete'");
		renderJson(JsonKit.toJson(new PageJson<IntelligentAnswerRecord>("0", "", page)));
	}
	
	public void getPreferenceById(){
		IntelligentAnswerRecord model = IntelligentAnswerRecord.dao.findById(getPara("id"));
		renderJson(JsonKit.toJson(new RecordJson("200", "suc", model)));
	}
	
	public void addAnswerRecord(){
		try {
			IntelligentAnswerRecord model = getModel(IntelligentAnswerRecord.class, "", true);
			
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
	
	public void deleteAnswerRecord(){
		try {
			IntelligentAnswerRecord model = getModel(IntelligentAnswerRecord.class, "", true);
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
	
	public void deleteAnswerRecordList(){
		try {
			String[] ids = getParaValues("id");
			for (String id : ids) {
				IntelligentAnswerRecord model = IntelligentAnswerRecord.dao.findById(id);
				model.set("del", "delete");
				model.update();
			}
			renderJson(JsonKit.toJson(new StatusJson("200", "suc", true)));
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(JsonKit.toJson(new StatusJson("500", "fail", true)));
		}
	}
	
	public void updateAnswerRecord(){
		renderJson("hello");
	}
}
