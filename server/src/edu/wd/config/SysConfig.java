package edu.wd.config;

import java.util.HashMap;

import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

import edu.wd.index.IndexController;
import edu.wd.knowledge.controller.AnswerController;
import edu.wd.knowledge.controller.AnswerStatisticsController;
import edu.wd.knowledge.controller.CommunityAnswerController;
import edu.wd.knowledge.controller.CommunityQuestionController;
import edu.wd.knowledge.controller.KnowComprehensiveController;
import edu.wd.knowledge.controller.KnowHistoryController;
import edu.wd.knowledge.controller.KnowHumanityController;
import edu.wd.knowledge.controller.KnowMappingController;
import edu.wd.knowledge.controller.KnowNaturalController;
import edu.wd.knowledge.controller.KnowSocietyController;
import edu.wd.model.CommunityAnswerModel;
import edu.wd.model.CommunityQuestionModel;
import edu.wd.model.IntelligentAnswerRecord;
import edu.wd.model.KnowBaseModel;
import edu.wd.model.KnowledgeComprehensiveModel;
import edu.wd.model.KnowledgeHistoryModel;
import edu.wd.model.KnowledgeHumanityModel;
import edu.wd.model.KnowledgeMappingModel;
import edu.wd.model.KnowledgeNaturalModel;
import edu.wd.model.KnowledgeSocietyModel;
import edu.wd.model.PreferenceBaseModel;
import edu.wd.model.PreferenceUserModel;
import edu.wd.pojo.CommonController;
import edu.wd.pojo.KnowBaseController;
import edu.wd.pojo.PreferenceBaseController;
import edu.wd.pojo.UserController;
import edu.wd.pojo.fenlei;
import edu.wd.pojo.fenleiController;
import edu.wd.pojo.his;
import edu.wd.pojo.hisController;
import edu.wd.pojo.manager;
import edu.wd.pojo.managerController;
import edu.wd.pojo.qsController;
import edu.wd.pojo.question;
import edu.wd.pojo.user;

/**
 * API引导式配
 */
//driverClass_backup=net.sf.log4jdbc.DriverSpy
public class SysConfig extends JFinalConfig {

	/**
	 * 配置常量，连接数据库，数据库的映射
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
		JFinal3BeetlRenderFactory rf = new JFinal3BeetlRenderFactory();
		rf.config();
		me.setRenderFactory(rf);
		GroupTemplate gt = rf.groupTemplate;
		Map<String, Object> shard = new HashMap<String, Object>();// 共享变量
		shard.put("ctx", JFinal.me().getContextPath());// 添加共享变量上下文路�?
		gt.setSharedVars(shard);// 设置共享变量
		me.setMaxPostSize(1200000000);
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class); //
		me.add("/user", UserController.class);
		me.add("/manager", managerController.class);
		me.add("/common", CommonController.class);
		me.add("/fenlei", fenleiController.class);
		me.add("/question", qsController.class);
		me.add("/his", hisController.class);
		
		me.add("/know", KnowBaseController.class);
		me.add("/preference", PreferenceBaseController.class);
		
		me.add("/know_comprehensive", KnowComprehensiveController.class);
		me.add("/know_humanity", KnowHumanityController.class);
		me.add("/know_natural", KnowNaturalController.class);
		me.add("/know_society", KnowSocietyController.class);
		me.add("/know_history", KnowHistoryController.class);
		me.add("/know_mapping", KnowMappingController.class);
		me.add("/know_statistics", AnswerStatisticsController.class);
		
		me.add("/answer", AnswerController.class);
		
		me.add("/community_question", CommunityQuestionController.class);
		me.add("/community_answer", CommunityAnswerController.class);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = 
				new C3p0Plugin("jdbc:mysql://localhost:3306/wenda?3useUnicode=true&characterEncoding=utf8",
				"root",
				"root",
				"com.mysql.jdbc.Driver");
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("user", user.class);
		arp.addMapping("manager", manager.class);
		arp.addMapping("fenlei", fenlei.class);
		arp.addMapping("his", his.class);
		arp.addMapping("question", question.class);
		
		arp.addMapping("knowledge_base", KnowBaseModel.class);
		
		arp.addMapping("preference_base", PreferenceBaseModel.class);
		arp.addMapping("preference_user", PreferenceUserModel.class);
		
		arp.addMapping("intelligent_answer_record", IntelligentAnswerRecord.class);
		arp.addMapping("knowledge_graph_comprehensive", KnowledgeComprehensiveModel.class);
		arp.addMapping("knowledge_graph_natural", KnowledgeNaturalModel.class);
		arp.addMapping("knowledge_graph_history", KnowledgeHistoryModel.class);
		arp.addMapping("knowledge_graph_society", KnowledgeSocietyModel.class);
		arp.addMapping("knowledge_graph_humanity", KnowledgeHumanityModel.class);
		arp.addMapping("knowledge_mapping", KnowledgeMappingModel.class);
		
		//问答社区model映射
		arp.addMapping("community_question", CommunityQuestionModel.class);
		arp.addMapping("community_answer", CommunityAnswerModel.class);
	}

	/**
	 * 配置全局拦截
	 */
	public void configInterceptor(Interceptors me) {

	}

	/**
	 * 配置处理
	 */
	public void configHandler(Handlers me) {
		// me.add(new CustomHandler());
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项�? 运行
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8007, "/", 5);
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub

	}
}
