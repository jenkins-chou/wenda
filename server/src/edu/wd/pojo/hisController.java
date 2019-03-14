package edu.wd.pojo;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.io.file.FileWriter;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import edu.wd.util.ParamUtil;
import edu.wd.util.Time;

public class hisController extends Controller {

	public void xz() {
		FileWriter writer = new FileWriter("data.txt");
		writer.write(JsonKit.toJson(his.dao.find("select * from his")));
		//数据下载
		renderFile(writer.getFile(), "数据包.txt");
	}

	public void tjfx() {
		List<fenlei> flist = fenlei.dao.find("select * from fenlei ");

		List<Integer> rc = new ArrayList<Integer>();
		String[] ss = new String[flist.size()];
		for (int i = 0; i < flist.size(); i++) {
			rc.add(his.dao.find("select * from his where type=?",
					flist.get(i).getStr("name")).size());
			ss[i] = flist.get(i).getStr("name");
		}
		setAttr("type", JSON.toJSONString(ss));
		setAttr("data", JSON.toJSONString(rc));
		System.out.println(JSON.toJSONString(rc));
		render("/his/tjfx.html");
	}

	public void getmyhis() {
		renderJson(his.dao.find("select * from his where uid=? ",
				getPara("uid")));
	}
	
	public void gethisbypage(){
		Page<his> page = his.dao.paginate(getParaToInt("pos")+1,
				10, "select * ", "from his where uid=?",getPara("uid"));
		renderJson(page.getList());
	}

	public void getmy() {
		if(getPara("type").equals("全部")){
		renderJson(his.dao.find("select * from his where uid=?",
				getPara("uid")));
		}else{
			renderJson(his.dao.find("select * from his where uid=? and type=?",
					getPara("uid"), getPara("type")));
		}
	}

	public void wd() {
		String msg = getPara("msg");
		String type = getPara("type");
		if(type.equals("全部")){
			List<question> list = question.dao.find(
					"select * from question ");
			question q = null;
			for (int i = 0; i < list.size(); i++) {
				String[] gjc = list.get(i).getStr("gjc").split("#");
				int a = 0;
				for (int j = 0; j < gjc.length; j++) {
					if (msg.contains(gjc[j]))
						a = a + 1;
				}
				if (a == gjc.length)
					q = list.get(i);
			}
			if (q != null) {
				new his().set("uid", getPara("uid")).set("uname", getPara("uname"))
						.set("qid", q.get("id").toString())
						.set("time", Time.getTime()).set("myquestion", msg)
						.set("qname", q.getStr("title"))
						.set("answer", q.getStr("answer"))
						.set("type",q.getStr("type")).save();
			} else {
				List<question> qlist = question.dao
						.find("select * from question  order by rand() LIMIT 1");
				if (qlist.size() > 0) {
					q = qlist.get(0);
					new his().set("uid", getPara("uid"))
							.set("uname", getPara("uname"))
							.set("qid", q.get("id").toString())
							.set("time", Time.getTime()).set("myquestion", msg)
							.set("qname", q.getStr("title"))
							.set("answer", q.getStr("answer"))
							.set("type",q.getStr("type")).save();
				}

			}
		}else{
			List<question> list = question.dao.find(
					"select * from question where type=?", type);
			question q = null;
			for (int i = 0; i < list.size(); i++) {
				String[] gjc = list.get(i).getStr("gjc").split("#");//关键词
				int a = 0;
				for (int j = 0; j < gjc.length; j++) {
					if (msg.contains(gjc[j]))
						a = a + 1;
				}
				if (a == gjc.length)
					q = list.get(i);
			}
			if (q != null) {
				new his().set("uid", getPara("uid")).set("uname", getPara("uname"))
						.set("qid", q.get("id").toString())
						.set("time", Time.getTime()).set("myquestion", msg)
						.set("qname", q.getStr("title"))
						.set("answer", q.getStr("answer"))
						.set("type",q.getStr("type")).save();
			} else {
				List<question> qlist = question.dao
						.find("select * from question where type='其他' order by rand() LIMIT 1");
				if (qlist.size() > 0) {
					q = qlist.get(0);
					new his().set("uid", getPara("uid"))
							.set("uname", getPara("uname"))
							.set("qid", q.get("id").toString())
							.set("time", Time.getTime()).set("myquestion", msg)
							.set("qname", q.getStr("title"))
							.set("answer", q.getStr("answer"))
							.set("type", "其他").save();
				}

			}
		}
		
		renderJson("1");
	}
	
	
	public void del(){
		his.dao.deleteById(getPara("id"));
		renderJson("1");
	}

}
