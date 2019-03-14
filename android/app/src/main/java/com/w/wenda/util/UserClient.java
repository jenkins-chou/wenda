package com.w.wenda.util;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserClient {
	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		//设置网络超时时间
		client.setTimeout(10000);
	}
	public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
//		if(params!=null)
//		System.out.println(getAbsoluteUrl(url)+params.toString());
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
		if(params!=null)
			System.out.println(getAbsoluteUrl(url)+params.toString());
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	public static void getAbsolute(String url, RequestParams params,
                                   AsyncHttpResponseHandler responseHandler) {
		client.get(url, params, responseHandler);
	}
	public static void getss(String url, RequestParams params,
                             AsyncHttpResponseHandler responseHandler) {
		System.out.println(getAbsoluteUrl(url)+params.toString());
		client.get(url, params, responseHandler);
	}
	private static String getAbsoluteUrl(String relativeUrl) {
		return Url.url() + relativeUrl;
	}
}
