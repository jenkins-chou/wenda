package edu.wd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;

import edu.wd.model.CommunityQuestionModel;
import edu.wd.model.KnowBaseModel;
import edu.wd.pojo.fenlei;
import edu.wd.pojo.user;
import edu.wd.util.CommonData;

public class CommunityQuestionController  extends Controller{
	
	public void getAllQuestion(){
		try {
			System.out.println(CommunityQuestionModel.dao.find("select * from community_question").size());
			System.out.println(CommunityQuestionModel.dao.find("select * from community_question").toString());
			renderJson(CommunityQuestionModel.dao.find("select * from community_question"));
		} catch (Exception e) {
			renderJson(new CommonData("500", "", e.toString()));
		}
	}
	
	public void getMyQuestion(){
		try {
			renderJson(CommunityQuestionModel.dao.find("select * from community_question where creator_id = ?",getPara("creator_id")));
		} catch (Exception e) {
			renderJson(new CommonData("500", "", e.toString()));
		}
	}
	
	public void addQuestion(){
		try {
			getModel(CommunityQuestionModel.class, "", true).save();
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
}
