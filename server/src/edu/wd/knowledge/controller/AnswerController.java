package edu.wd.knowledge.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.eclipse.jetty.util.log.Log;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;

import cn.hutool.core.date.SystemClock;
import edu.wd.model.IntelligentAnswerRecord;
import edu.wd.model.KeyMapModel;
import edu.wd.model.KnowledgeComprehensiveModel;
import edu.wd.model.KnowledgeHistoryModel;
import edu.wd.model.KnowledgeHumanityModel;
import edu.wd.model.KnowledgeMappingModel;
import edu.wd.model.KnowledgeNaturalModel;
import edu.wd.model.KnowledgeSocietyModel;
import edu.wd.model.MessageModel;

public class AnswerController extends Controller{
	
	private String user_id;
	private String question;
	
	//app获取答案接口
	public void getAnswer(){
		
		user_id = getPara("user_id");
		String question_type = getPara("question_type");
		question = getPara("question");
		if(question != null && !question.equals("")){
			
			List<KnowledgeMappingModel> mappingModels = KnowledgeMappingModel.dao.find("select * from knowledge_mapping where input_value like '%"+question+"%'");
			if(mappingModels!=null){
				log("mappingModels not null");
				//打印一级问题分类
				for(KnowledgeMappingModel model:mappingModels){
					System.out.println(model.toString());
				}
				if(mappingModels.size()==1){
					String key = (String)mappingModels.get(0).get("key_first", "综合");//一级分类
					String selectTable = getKnowMapTable(key);//二级分类表名
					String input_value = (String)mappingModels.get(0).get("input_value", "");//用户输入值
					String mapping = (String)mappingModels.get(0).get("mapping", "文本");//二级分类
					
					//二级查询、获取作答答案
					getAnswerFromDatabase(question,key,mapping,selectTable);
				}else if(mappingModels.size()>1){
					String messageList = "";
					//当匹配到多个答案时
					for(int i =0;i<mappingModels.size();i++){
						KnowledgeMappingModel model= mappingModels.get(i);
						if(i==mappingModels.size()-1){
							messageList +=model.get("input_value","");
						}else{
							messageList +=model.get("input_value","")+",";
						}
					}
					System.out.println(messageList);
					sendMessageToClient(MessageModel.ServerMsgTextList,"可能感兴趣的话题","","",messageList,"无匹配","无匹配");
				}else{
					//联想作答
					getProbablyAnswer();
				}
			}else{
				
				//发送默认作答内容
				sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default,"","","","无匹配","无匹配");
			}
		}else{
			//发送系统出错的默认回答
			sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_error,"","","","无匹配","无匹配");
		}
	}
	
	/**
	 * 分割文本，去掉字符串中的字母和 斜杠/
	 * @return List
	 */
	public List<String> getSplitString(String str){
		List<String> data = new ArrayList();
		if(str!=null&&!str.equals("")){
			Result result = ToAnalysis.parse(str);
			if(result!=null){
				String temp = result.toString().replaceAll("[a-zA-Z]","" ).replaceAll("/", "");
				String[] sourceArray = temp.split(",");
				data = Arrays.asList(sourceArray);
			}
		}
		return data;
	}
	
	
	//获取类型映射、用于二级分类查找
	//返回值：表名
	public String getKnowMapTable(String mappingKey){
		String result = KeyMapModel.map_comprehensive;
		if(mappingKey!=null&&!mappingKey.equals("")){
			switch(mappingKey){
				case KeyMapModel.key_comprehensive:
					result = KeyMapModel.map_comprehensive;
					break;
				case KeyMapModel.key_natural:
					result = KeyMapModel.map_natural;
					break;
				case KeyMapModel.key_society:
					result = KeyMapModel.map_society;
					break;
				case KeyMapModel.key_history:
					result = KeyMapModel.map_history;
					break;
					default:
						result = KeyMapModel.map_comprehensive;
						break;
			}
		}
		return result;
	}
	
	//二级查找、获取答案
	public void getAnswerFromDatabase(String input_value,String key,String mapping,String tableName){
		
		String sql = "select * from "+tableName+" where input_value like '%"+input_value+"%' and second_classification like '%"+ mapping +"%'";
		System.out.println("getAnswerFromDatabase : "+sql);
		
		if(key!=null&&!key.equals("")){
			switch(key){
				case KeyMapModel.key_comprehensive:
					List<KnowledgeComprehensiveModel> comprehensiveModels = KnowledgeComprehensiveModel.dao.find(sql);
					if(comprehensiveModels!=null&&comprehensiveModels.size()>0){
						KnowledgeComprehensiveModel model = comprehensiveModels.get(0);
						System.out.println(""+model.toString());
						sendMessageToClient(changeMappingToCN(mapping),(String)model.get("answer",MessageModel.answer_default_ask_again),(String)model.get("answer", ""),(String)model.get("answer", ""),"",key,mapping);
					}else{
						//发送默认作答内容
						sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default_comprehensive,"","","","无匹配","无匹配");
					}
					break;
				case KeyMapModel.key_natural:
					List<KnowledgeNaturalModel> naturalModels = KnowledgeNaturalModel.dao.find(sql);
					if(naturalModels!=null&&naturalModels.size()>0){
						KnowledgeNaturalModel model = naturalModels.get(0);
						System.out.println(""+model.toString());
						sendMessageToClient(changeMappingToCN(mapping),(String)model.get("answer",MessageModel.answer_default_ask_again),(String)model.get("answer", ""),(String)model.get("answer", ""),"",key,mapping);
					}else{
						//发送默认作答内容
						sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default_natural,"","","","无匹配","无匹配");
					}
					break;
				case KeyMapModel.key_society:
					List<KnowledgeSocietyModel> societyModels = KnowledgeSocietyModel.dao.find(sql);
					if(societyModels!=null&&societyModels.size()>0){
						KnowledgeSocietyModel model = societyModels.get(0);
						System.out.println(""+model.toString());
						sendMessageToClient(changeMappingToCN(mapping),(String)model.get("answer",MessageModel.answer_default_ask_again),(String)model.get("answer", ""),(String)model.get("answer", ""),"",key,mapping);
					}else{
						//发送默认作答内容
						sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default_society,"","","","无匹配","无匹配");
					}
					break;
				case KeyMapModel.key_history:
					List<KnowledgeHistoryModel> historyModels = KnowledgeHistoryModel.dao.find(sql);
					if(historyModels!=null&&historyModels.size()>0){
						KnowledgeHistoryModel model = historyModels.get(0);
						System.out.println(""+model.toString());
						sendMessageToClient(changeMappingToCN(mapping),(String)model.get("answer",MessageModel.answer_default_ask_again),(String)model.get("answer", ""),(String)model.get("answer", ""),"",key,mapping);
					}else{
						//发送默认作答内容
						sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default_history,"","","","无匹配","无匹配");
					}
					break;
					default:
						List<KnowledgeComprehensiveModel> defaultModels = KnowledgeComprehensiveModel.dao.find(sql);
						if(defaultModels!=null&&defaultModels.size()>0){
							KnowledgeComprehensiveModel model = defaultModels.get(0);
							System.out.println(""+model.toString());
							sendMessageToClient(changeMappingToCN(mapping),(String)model.get("answer",MessageModel.answer_default_ask_again),(String)model.get("answer", ""),(String)model.get("answer", ""),"",key,mapping);
						}else{
							//发送默认作答内容
							sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default,"","","","无匹配","无匹配");
						}
						break;
			}
		}else{
			sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default,"","","","无匹配","无匹配");
		}
	}
	
	//词汇联想作答
	void getProbablyAnswer(){
		List<String> list = getSplitString(question);
		String sql_and = "select * from knowledge_mapping where ";
		String sql_or = "select * from knowledge_mapping where ";
		if(list!=null&&list.size()>0){
			for(int i = 0;i<list.size();i++){
				if(i==list.size()-1){
					sql_and += " input_value like '%"+list.get(i)+"%'";
					sql_or += " input_value like '%"+list.get(i)+"%'";
				}else{
					sql_and += " input_value like '%"+list.get(i)+"%' and ";
					sql_or += " input_value like '%"+list.get(i)+"%' or ";
				}
			}
		}
		log(sql_and);
		List<KnowledgeMappingModel> mappingModelAnd = KnowledgeMappingModel.dao.find(sql_and);
		List<KnowledgeMappingModel> mappingModelOr = KnowledgeMappingModel.dao.find(sql_or);
		if(mappingModelAnd!=null&&mappingModelOr!=null){
			mappingModelAnd.addAll(mappingModelOr);
			log(mappingModelAnd.toString());
			if(mappingModelAnd.size()>=1){
				String messageList = "";
				//当匹配到多个答案时
				for(int i =0;i<mappingModelAnd.size();i++){
					KnowledgeMappingModel model= mappingModelAnd.get(i);
					if(i==mappingModelAnd.size()-1){
						messageList +=model.get("input_value","");
					}else{
						messageList +=model.get("input_value","")+",";
					}
				}
				System.out.println(messageList);
				sendMessageToClient(MessageModel.ServerMsgTextList,"可能感兴趣的话题","","",messageList,"无匹配","无匹配");
			}else{
				//发送默认作答内容
				sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default,"","","","无匹配","无匹配");
			}
		}else{
			//发送默认作答内容
			sendMessageToClient(MessageModel.ServerMsgText,MessageModel.answer_default,"","","","无匹配","无匹配");
		}
	}
	
	//类型转换 例如 文本 ==> MessageModel.ServerMsgText
	public int changeMappingToCN(String mapping){
		int result = MessageModel.ServerMsgText;
		if(mapping!=null){
			switch(mapping){
			case "文本":
				result = MessageModel.ServerMsgText;
				break;
			case "图片":
				result = MessageModel.ServerMsgImage;
				break;
			case "网址":
				result = MessageModel.ServerMsgUrl;
				break;
			case "表情":
				result = MessageModel.ServerMsgEmoji;
				break;
			}
		}
		return result;
	}
	
	//发送答案至客户端
	public void sendMessageToClient(int msgType,String message,String imageUrl,String httpUrl,String messageList,String key_record,String mapping_record){
		JSONObject js = new JSONObject();
		js.put("code", "200");
		js.put("message", message);
		switch(msgType){
			//文本类型
			case MessageModel.ServerMsgText:
				js.put(MessageModel.key_type, msgType);//设置消息类型
				break;
			//http网址类型
			case MessageModel.ServerMsgUrl:
				js.put(MessageModel.key_type, msgType);//设置消息类型
				js.put(MessageModel.key_webUrl,httpUrl);
				break;
			//图片地址类型
			case MessageModel.ServerMsgImage:
				js.put(MessageModel.key_type, msgType);//设置消息类型
				js.put(MessageModel.key_imageUrl,imageUrl);
				break;
			//表情类型
			case MessageModel.ServerMsgEmoji:
				break;
			case MessageModel.ServerMsgTextList:
				js.put(MessageModel.key_type, msgType);
				js.put(MessageModel.key_message_list, messageList);
				break;
			default:
				js.put(MessageModel.key_type, MessageModel.MineMsgText);//设置消息类型
			break;
		}
		renderJson(js.toJSONString());
		
//		new Runable(){
//			void run(){
//				
//			}
//		}.run();
		//保存访问记录
		IntelligentAnswerRecord model = new IntelligentAnswerRecord();
		model.set("input_value",question);
		
		model.set("key_record",key_record);
		model.set("mapping_record", mapping_record);
		
		model.set("answer_record",message);
		model.set("user_id",user_id);
		model.set("evaluate", "");//评价
		model.set("create_time",SystemClock.now()+"");//创建时间
		model.set("remark", "");//备注
		model.set("del", "normal");//备注
		System.out.println(model);
		model.save();
	}
	
	//打印日志
	void log(String log){
		System.out.println("---------[ "+log+" ]"+new Date().toString());
	}

}
