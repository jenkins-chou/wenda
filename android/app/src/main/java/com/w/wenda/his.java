package com.w.wenda;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.w.wenda.adapater.CommonAdapter;
import com.w.wenda.adapater.ViewHolder;
import com.w.wenda.pojo.CommonData;
import com.w.wenda.pojo.User;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.MyToastUtil;
import com.w.wenda.util.Url;
import com.w.wenda.util.UserClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zuo.biao.library.base.BaseFragment;

public class his extends BaseFragment {

    List<com.w.wenda.pojo.his> list;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.his_list);

        //类相关初始化，必须使用>>>>>>>>>>>>>>>>
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static his createInstance() {
        his fragment = new his();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        listView=findViewById(R.id.listView);
        getlist();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    public void getlist(){
        RequestParams ps=new RequestParams();
        ps.add("uid",MyApplication.getApp().getU().getId());
        UserClient.get("his/getmyhis",ps,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list=JSON.parseArray(content, com.w.wenda.pojo.his.class);
                listView.setAdapter(new CommonAdapter<com.w.wenda.pojo.his>(getActivity(),list,R.layout.his_item) {
                    @Override
                    public void convert(ViewHolder helper, com.w.wenda.pojo.his item) {
                        helper.setText(R.id.title,"提问:"+item.getMyquestion());
                        helper.setText(R.id.answer,"回答:"+item.getAnswer());
                        helper.setText(R.id.time,item.getTime());
                    }
                });
            }
        });
    }

}
