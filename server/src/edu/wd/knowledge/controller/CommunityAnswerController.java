package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;

import edu.wd.model.CommunityAnswerModel;
import edu.wd.model.CommunityQuestionModel;
import edu.wd.pojo.his;

public class CommunityAnswerController extends Controller {
	public void addAnswer(){
		try {
			getModel(CommunityAnswerModel.class, "", true).save();
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
	
	public void getAnswerByQuestion(){
		try {
			renderJson(CommunityAnswerModel.dao.find("select * from community_answer where question_id=?",
					getPara("question_id")));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			JSONObject js = new JSONObject();
			js.put("code", 500);
			js.put("msg", e.toString());
			renderJson(js.toJSONString());
		}
	}
}
