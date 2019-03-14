package com.w.wenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.Url;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.base.BaseFragment;

public class wo extends BaseFragment {

    @OnClick(R.id.back)
    void back(){
        getActivity().finish();
    }

    TextView username,nickname,tel;
    ImageView head;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.wo);

        //类相关初始化，必须使用>>>>>>>>>>>>>>>>
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        ButterKnife.bind(this,view);
        return view;
    }

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static wo createInstance() {
        wo fragment = new wo();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {

     username=findView(R.id.username);
     nickname=findView(R.id.nickname);
     tel=findView(R.id.tel);
     head=findView(R.id.head);
     username.setText(MyApplication.getApp().getU().getUsername());
     nickname.setText(MyApplication.getApp().getU().getName());
     tel.setText(MyApplication.getApp().getU().getSjh());
        ImageLoader.getInstance().displayImage(Url.url()+"upload/"+MyApplication.getApp().getU().getHead(),head);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }



}
