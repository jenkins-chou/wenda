package com.w.wenda.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 正则表达式：验证数字
     */
    public static final String REGEX_NUMBER = "^[0-9]\\d*$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";




    /**
     * 校验是否为数字
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number){
        return number!=null&&!number.equals("")&& Pattern.matches(REGEX_NUMBER,number);
    }
    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 判断字符串是否含有数字
     * @return
     */
    public static boolean hasNumber(String value){
        //【含有数字】true
        String regex = ".*[0-9].*";
        boolean result = value.matches(regex);
        return result;
    }
    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }


    /**
     * 获取手机MAC物理地址
     *
     * @author zhouzhenjian
     */
//    public static String getMacAddress(Context context) {
//
//        //安卓6.0以上
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            try {
//                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
//                for (NetworkInterface nif : all) {
//                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
//                    byte[] macBytes = nif.getHardwareAddress();
//                    if (macBytes == null) {
//                        return "";
//                    }
//
//                    StringBuilder res1 = new StringBuilder();
//                    for (byte b : macBytes) {
//                        res1.append(String.format("%02X:",b));
//                    }
//
//                    if (res1.length() > 0) {
//                        res1.deleteCharAt(res1.length() - 1);
//                    }
//                    return res1.toString();
//                }
//            } catch (Exception ex) {
//            }
//            return "02:00:00:00:00:00";
//        }else{
//            String macString = "";
//            WifiManager wifimsg = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
//            if (wifimsg != null)
//            {
//                if (wifimsg.getConnectionInfo() != null)
//                {
//                    if (wifimsg.getConnectionInfo().getMacAddress() != null)
//                    {
//                        macString = wifimsg.getConnectionInfo().getMacAddress();
//                    }
//                }
//            }
//            return macString;
//        }
//    }

    /**
     * 将图片转换成Base64编码的字符串
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     *base64编码字符集转化成图片文件。
     * @param base64Str
     * @param path 文件存储路径
     * @return 是否成功
     */
    public static boolean base64ToFile(String base64Str, String path){
        byte[] data = Base64.decode(base64Str, Base64.DEFAULT);
        for (int i = 0; i < data.length; i++) {
            if(data[i] < 0){
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(context.getExternalCacheDir().getPath(), "yongbei");
        if (!appDir.exists()) {        appDir.mkdir();    }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appDir+"/"+fileName;
    }

    //获取系统时间戳（精度到秒）
    public static String getTime(){
        long time= System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String str= String.valueOf(time);
        return str;
    }

    //字符串转时间戳（精度到秒）
    public static String getTime(String timeString){
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime()/1000;
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    //字符串转时间戳（精度到秒）
    public static String getTime(String timeString, String pattern){
        String timeStamp = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime()/1000;
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    //时间戳转字符串（精度到秒）
    public static String getStrTime(String timeStamp){
        if (timeStamp==null)return "";
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        long l = Long.valueOf(timeStamp)*1000;
        timeString = sdf.format(new Date(l));
        return timeString;
    }

    //时间戳转字符串（精度到秒）
    public static String getStrTime(String timeStamp, String pattern){
        if (timeStamp==null)return "";
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        long l = Long.valueOf(timeStamp)*1000;
        timeString = sdf.format(new Date(l));
        return timeString;
    }

    /**
     * 判断两个字符串是否相等
     * @param arg1
     * @param arg2
     * @return
     */
    public static boolean isEquals(String arg1, String arg2){
        boolean result = false;
        if (arg1==null&&arg2==null)
            result = true;
        else{
            if (arg1!=null&&arg2!=null){
                if (arg1.equals(arg2))
                    result = true;
            }
        }
        return result;
    }

    /**
     * 判断字符串是否不为空
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value){
        return value!=null&&!value.equals("");
    }



}
