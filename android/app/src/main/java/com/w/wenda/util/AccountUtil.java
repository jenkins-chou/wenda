package com.w.wenda.util;

import android.content.Context;

import com.google.gson.Gson;
import com.w.wenda.pojo.User;

public class AccountUtil {
    public static void saveUser(Context context,String userJson){
        if (context!=null&&StringUtil.isNotEmpty(userJson)){
            SPUtils.put(context,SPUtils.FILE_USER,SPUtils.user_object,userJson);
        }
    }

    public static void clearUser(Context context){
        if (context!=null){
            SPUtils.remove(context,SPUtils.FILE_USER,SPUtils.user_object);
        }
    }

    public static String getUser(Context context){
        String result = "";
        if (context!=null){
            result = (String) SPUtils.get(context,SPUtils.FILE_USER,SPUtils.user_object,"");
        }
        return result;
    }

    public static boolean isLogin(Context context){
        boolean result = false;
        if (context!=null){
            String userJson = (String) SPUtils.get(context,SPUtils.FILE_USER,SPUtils.user_object,"");

            if (StringUtil.isNotEmpty(userJson)){
                User user = new Gson().fromJson(userJson,User.class);
                if (user!=null){
                    result = true;
                }
            }
        }
        return result;
    }
}
