package edu.wd.knowledge.controller;

import com.jfinal.core.Controller;

public class AnswerStatisticsController  extends Controller{
	public void toHtml(){
		render("/know_statistics/statistics.html");
	}
}
