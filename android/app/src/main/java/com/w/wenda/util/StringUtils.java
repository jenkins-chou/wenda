package com.w.wenda.util;



public class StringUtils {
	public static final String SN="com.tongling.gzbclient";

	/**
	 * @return String
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if ("".equals(str) || "null".equalsIgnoreCase(str)) {
			return true;
		}
		return false;
	}
	/**
	 * @return String
	 */
	public static boolean isPhone(String str) {

		if (!str.startsWith("1") || str.length() != 11) {
			return false;
		} else {
			return true;
		}

	}
	/**
	 * @return String
	 */
	public static String strAddSpace(String tempStr, int splitWidth) {
		String returnStr = "";
		if (isEmpty(tempStr))
			return returnStr;
		if (tempStr.length()<=splitWidth)
			return tempStr;
		
		if(tempStr.length()%splitWidth>0){
			for(int i=0;i<tempStr.length()%splitWidth;i++){
				tempStr+=" ";
			}
		}
		int pos = 0;
		while (pos < tempStr.length()) {
			returnStr += tempStr.substring(pos, pos + splitWidth) + "  ";
			pos += splitWidth;
		}
		while(returnStr.endsWith(" ")){
			returnStr=returnStr.substring(0, returnStr.length()-1);
		}
		return returnStr;
	}
	
	public static int replaceStrToInt(String tempStr) {
		int temp=0;
		if(isEmpty(tempStr))
			temp=0;
		else {
			try {
				temp= Integer.parseInt(tempStr);
			} catch (Exception e) {
				temp=0;
			}
		}
				
		return temp;
	}
	
  
    
}