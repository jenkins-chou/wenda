package com.w.wenda;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.adapater.CommonAdapter;
import com.w.wenda.adapater.ViewHolder;
import com.w.wenda.base.MyBaseActivity;
import com.w.wenda.pojo.his;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.UserClient;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class his_list extends MyBaseActivity {
    List<his> list;
    ListView listView;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.his_list);

        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black),0);

        listView=findViewById(R.id.listView);
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
        ps.add("uid",MyApplication.getApp().getU().getId());
        UserClient.get("his/getmyhis",ps,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list=JSON.parseArray(content,his.class);
                listView.setAdapter(new CommonAdapter<his>(his_list.this,list,R.layout.his_item) {
                    @Override
                    public void convert(ViewHolder helper, his item) {
                        helper.setText(R.id.title,"提问:"+item.getMyquestion());
                        helper.setText(R.id.answer,"回答:"+item.getAnswer());
                        helper.setText(R.id.time,item.getTime());
                    }
                });
            }
        });
    }
}
