package com.w.wenda.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	
	public static final String getTime(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	public static final String getTimes(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
}
