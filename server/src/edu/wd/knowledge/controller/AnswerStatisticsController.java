package edu.wd.knowledge.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

import edu.wd.model.CommunityAnswerModel;
import edu.wd.model.KnowledgeComprehensiveModel;

public class AnswerStatisticsController  extends Controller{
	public void toHtml(){
		render("/know_statistics/statistics.html");
	}
	
	public void getData(){
		try {
			
			Integer comprehensive_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_comprehensive");//综合类问题总数
			Integer natural_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_natural");//自然类问题总数
			Integer society_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_society");//社会类问题总数
			Integer history_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_history");//历史类问题总数
			
			Integer comprehensive_text_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_comprehensive where second_classification like '文本'");
			Integer comprehensive_http_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_comprehensive where second_classification like '网址'");
			Integer comprehensive_image_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_comprehensive where second_classification like '图片'");
			List<Classify> comprehensiveList = new ArrayList<>();
			comprehensiveList.add(new Classify("文本",comprehensive_text_count));
			comprehensiveList.add(new Classify("网址",comprehensive_http_count));
			comprehensiveList.add(new Classify("图片",comprehensive_image_count));
			
			Integer natural_text_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_natural where second_classification like '文本'");
			Integer natural_http_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_natural where second_classification like '网址'");
			Integer natural_image_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_natural where second_classification like '图片'");
			List<Classify> naturalList = new ArrayList<>();
			naturalList.add(new Classify("文本",natural_text_count));
			naturalList.add(new Classify("网址",natural_http_count));
			naturalList.add(new Classify("图片",natural_image_count));
			
			Integer history_text_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_history where second_classification like '文本'");
			Integer history_http_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_history where second_classification like '网址'");
			Integer history_image_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_history where second_classification like '图片'");
			List<Classify> historyList = new ArrayList<>();
			historyList.add(new Classify("文本",history_text_count));
			historyList.add(new Classify("网址",history_http_count));
			historyList.add(new Classify("图片",history_image_count));
			
			Integer society_text_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_society where second_classification like '文本'");
			Integer society_http_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_society where second_classification like '网址'");
			Integer society_image_count = Db.queryInt("SELECT count(*) FROM knowledge_graph_society where second_classification like '图片'");
			List<Classify> societyList = new ArrayList<>();
			societyList.add(new Classify("文本",society_text_count));
			societyList.add(new Classify("网址",society_http_count));
			societyList.add(new Classify("图片",society_image_count));
			
			Integer record_text_count = Db.queryInt("SELECT count(*) FROM intelligent_answer_record where mapping_record like '文本'");
			Integer record_http_count = Db.queryInt("SELECT count(*) FROM intelligent_answer_record where mapping_record like '网址'");
			Integer record_image_count = Db.queryInt("SELECT count(*) FROM intelligent_answer_record where mapping_record like '图片'");
			Integer record_default_count = Db.queryInt("SELECT count(*) FROM intelligent_answer_record where mapping_record like '无匹配'");
			
			JSONObject js = new JSONObject();
			js.put("code", "200");
			js.put("data_set", new int[]{comprehensive_count, natural_count, society_count, history_count});//各类问题总数
			js.put("comprehensive_data",comprehensiveList);//综合类问题统计
			js.put("natural_data",naturalList);//自然类问题统计
			js.put("history_data",historyList);//历史类问题统计
			js.put("society_data",societyList);//社会类问题统计
			js.put("record_data", new int[]{record_text_count, record_http_count, record_image_count, record_default_count});//各类问题总数
			
			
			
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
	
	public class Classify{
		private String name;
		private int value;
		
		public Classify(String name,int value){
			this.name = name;
			this.value = value;
		}
		public void setName(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public void setValue(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
		public String toString(){
			return "name:"+name+"value:"+value;
		}
	}
}
