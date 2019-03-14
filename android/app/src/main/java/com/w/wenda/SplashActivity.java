package com.w.wenda;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.w.wenda.pojo.User;
import com.w.wenda.util.AccountUtil;
import com.w.wenda.util.MyApplication;

import butterknife.ButterKnife;
import zuo.biao.library.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        if (AccountUtil.isLogin(this)){
            startActivity(new Intent(this,MainActivity.class));
            User user = new Gson().fromJson(AccountUtil.getUser(this),User.class);
            MyApplication.getApp().setU(user);
            finish();
        }else{
            startActivity(new Intent(this,UserLogin.class));
            finish();
        }
    }

    @Override
    public Activity getActivity() {
        return null;
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
}
