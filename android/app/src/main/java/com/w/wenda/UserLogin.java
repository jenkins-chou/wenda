package com.w.wenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.activitys.SettingActivity;
import com.w.wenda.pojo.User;
import com.w.wenda.ui.CommonLoading;
import com.w.wenda.util.AccountUtil;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.MyToastUtil;
import com.w.wenda.util.StringUtil;
import com.w.wenda.util.UserClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.base.BaseActivity;

public class UserLogin extends BaseActivity implements View.OnClickListener{
    Context context = UserLogin.this;

    @BindView(R.id.loading)
    CommonLoading loading;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.setting)
    void setting(){
        startActivity(new Intent(this,SettingActivity.class));
    }

    @OnClick(R.id.login)
    void setLogin(){
        RequestParams ps = new RequestParams();
        ps.add("username", username.getText().toString());
        ps.add("password", password.getText().toString());
        if (StringUtil.isNotEmpty(username.getText().toString())
                &&StringUtil.isNotEmpty(password.getText().toString())){
            setLoadingEnable(true);
            UserClient.post("user/login",ps,new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);
                    if(content.contains("500")){
                        MyToastUtil.ShowToast(UserLogin.this,"登录失败");
                    }else {
                        User u = JSON.parseObject(content, User.class);
                        if (u!=null){
                            MyApplication.getApp().setU(u);
                            AccountUtil.saveUser(context,new Gson().toJson(u));
                            startActivity(new Intent(UserLogin.this, MainActivity.class));
                            finish();
                        }
                    }
                    setLoadingEnable(false);
                }
            });
        }else{
            Toast.makeText(context, "请输入完整信息", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.register)
    void register(){
        startActivity(new Intent(UserLogin.this,UserRegist.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black),0);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }



    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
    }

    void setLoadingEnable(boolean enable){
        if (loading==null)return;
        if (enable){
            loading.setVisibility(View.VISIBLE);
        }else{
            loading.setVisibility(View.GONE);
        }
    }
}
