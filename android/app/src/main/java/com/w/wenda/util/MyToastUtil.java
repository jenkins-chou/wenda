package com.w.wenda.util;


import android.content.Context;
import android.widget.Toast;


public class MyToastUtil {
	
	public static void ShowToast(Context context, String message){
		if(message==null||message.trim().equals(""))
			return ;
		Toast toast= Toast.makeText(context, message, Toast.LENGTH_SHORT);
		/*View view=toast.getView();
		view.setBackgroundResource(R.drawable.toast_frame);
		TextView textView=(TextView)view.findViewById(android.R.id.message);
		textView.setTextColor(context.getResources().getColor(R.color.yellow));*/
		toast.show();
	}
	
}
