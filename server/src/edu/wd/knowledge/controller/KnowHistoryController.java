package edu.wd.knowledge.controller;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import edu.wd.model.KnowledgeComprehensiveModel;
import edu.wd.model.KnowledgeHistoryModel;
import edu.wd.util.PageJson;
import edu.wd.util.ParamUtil;
import edu.wd.util.RecordJson;
import edu.wd.util.StatusJson;

public class KnowHistoryController extends Controller{
	
	public void importData() {
		UploadFile f = getFile();
//		ExcelReader reader = ExcelUtil.getReader(f.getUploadPath());
		ExcelReader reader = ExcelUtil.getReader(f.getUploadPath()+"\\"+f.getFileName(),0);//excel调用
		List<List<Object>> readAll = reader.read();
		for (int i = 1; i < readAll.size(); i++) {
			new KnowledgeHistoryModel()
			.set("input_value", readAll.get(i).get(0).toString())
			.set("answer", readAll.get(i).get(1).toString())
			.set("probably_answer", readAll.get(i).get(2).toString())
			.set("label", readAll.get(i).get(3).toString())
			.set("first_classification","历史")
			.set("second_classification", readAll.get(i).get(5).toString())
			.set("remark", readAll.get(i).get(6).toString())
			.set("del","normal")
			.save();
		}
		JSONObject js=new JSONObject();
		js.put("code",0);
		renderJson(js.toJSONString());
	}
	
	public void exportData() {
		List<KnowledgeHistoryModel> ulist = KnowledgeHistoryModel.dao.find("select * from knowledge_graph_history");
		List<List<String>> rows = CollUtil.newArrayList();
		List<String> ld = new CollUtil().newArrayList("输入关键字", "参考答案", "相似参考答案",
				"标签", "第一分类", "第二分类", "备注","创建时间","删除状态");
		rows.add(ld);
		for (int i = 0; i < ulist.size(); i++) {
			List<String> l = new CollUtil().newArrayList(
					ulist.get(i).getStr("input_value"),
					ulist.get(i).getStr("answer"), 
					ulist.get(i).getStr("probably_answer"), 
					ulist.get(i).getStr("label"),
					ulist.get(i).getStr("first_classification"),
					ulist.get(i).getStr("second_classification"),
					ulist.get(i).getStr("remark"),
					ulist.get(i).getStr("create_time"),
					ulist.get(i).getStr("del"));
			rows.add(l);
		}
		ExcelWriter writer = ExcelUtil.getWriter("d:/" + "历史类问答题库" + ".xls");
		// 通过构造方法创建writer
		// ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

		// 跳过当前行，既第一行，非必须，在此演示用
		writer.passCurrentRow();

		// 合并单元格后的标题行，使用默认标题样式
		// 一次性写出内容
		writer.write(rows);
		// 关闭writer，释放内存
		writer.close();
		renderFile(new File("d:/" + "历史类问答题库" + ".xls"));
	}
	
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
		try {
			KnowledgeHistoryModel model = getModel(KnowledgeHistoryModel.class, "", true);
			System.out.println(model.toJson());
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
