package com.w.wenda;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.adapater.CommonAdapter;
import com.w.wenda.adapater.ViewHolder;
import com.w.wenda.base.MyBaseActivity;
import com.w.wenda.pojo.fenlei;
import com.w.wenda.util.UserClient;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class type_choose extends MyBaseActivity {

    ListView listView;
    List<fenlei> list;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.type_choose);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black),0);
        listView = findViewById(R.id.listView);
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getlist();
    }

    public void getlist(){
        RequestParams ps=new RequestParams();
        UserClient.get("fenlei/alllist",ps,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list=JSON.parseArray(content,fenlei.class);
                listView.setAdapter(new CommonAdapter<fenlei>(type_choose.this,list,R.layout.type_item) {
                    @Override
                    public void convert(ViewHolder helper, fenlei item) {
                        helper.setText(R.id.name,item.getName());
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(type_choose.this,wd_main.class).putExtra("type",list.get(position).getName()));
                    }
                });
            }
        });
    }
}
