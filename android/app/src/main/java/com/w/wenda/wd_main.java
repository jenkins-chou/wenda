package com.w.wenda;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.w.wenda.util.MyToastUtil;
import com.w.wenda.util.UserClient;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class wd_main extends MyBaseActivity {
    ListView listView;
    List<his> list;
    EditText msg;
    Button fs;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void initUI() {
        setContentView(R.layout.wd_main);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black),0);

        listView = findViewById(R.id.listView);
        msg = findViewById(R.id.msg);
        fs = findViewById(R.id.fs);
        fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.getApp().getU().getState().equals("正常")) {
                    RequestParams ps = new RequestParams();
                    ps.add("uid", MyApplication.getApp().getU().getId());
                    ps.add("uname", MyApplication.getApp().getU().getName());
                    ps.add("type", getIntent().getStringExtra("type"));
                    ps.add("msg", msg.getText().toString());
                    UserClient.get("his/wd", ps, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            msg.setText("");
                            getlist();
                        }
                    });
                }else{
                    MyToastUtil.ShowToast(wd_main.this,"账户被屏蔽!!");
                }
            }
        });

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

    public void getlist() {
        RequestParams ps = new RequestParams();
        ps.add("uid", MyApplication.getApp().getU().getId());
        ps.add("type", getIntent().getStringExtra("type"));
        UserClient.get("his/getmy", ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                list = JSON.parseArray(content, his.class);
                listView.setAdapter(new CommonAdapter<his>(wd_main.this, list, R.layout.wd_item) {
                    @Override
                    public void convert(ViewHolder helper, his item) {
                        helper.setText(R.id.msg2, item.getMyquestion());
                        helper.setText(R.id.msg1, item.getAnswer());
                    }
                });
                listView.setSelection(list.size()-1);
            }
        });
    }
}
