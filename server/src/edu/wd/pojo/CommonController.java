package edu.wd.pojo;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.upload.UploadFile;

import edu.wd.util.CommonData;

/**
 * 通用文件上传
 * @author Administrator
 *
 */
public class CommonController extends Controller{

	//文件上传
	public void upload(){
		try {
			UploadFile f=getFile();
			JSONObject js=new JSONObject();
			js.put("code",0);
			js.put("url", f.getFileName());
			renderJson(js.toJSONString());
		} catch (Exception e) {
			// TODO: handle exception
			JSONObject js=new JSONObject();
			js.put("code",500);
			renderJson(js.toJSONString());
		}
		
	}
	
	//文件上传
	public void mobileupload(){
		try {
			UploadFile f=getFile();
			renderJson(JsonKit.toJson(new CommonData("200",f.getFileName(),"成功")));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			renderJson(JsonKit.toJson(new CommonData("500",null,e.toString())));
		}
	}
	
}
