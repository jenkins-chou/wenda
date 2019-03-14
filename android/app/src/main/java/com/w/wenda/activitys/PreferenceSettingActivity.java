package com.w.wenda.activitys;

import android.content.Intent;
import android.media.Image;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.R;
import com.w.wenda.adapater.CommonAdapter;
import com.w.wenda.adapater.ViewHolder;
import com.w.wenda.his_list;
import com.w.wenda.model.PreferenceModel;
import com.w.wenda.pojo.his;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.UserClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.util.Log;

public class PreferenceSettingActivity extends AppCompatActivity {

    private BaseQuickAdapter baseQuickAdapter;

    private List<PreferenceModel> datas;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_setting);
        ButterKnife.bind(this);
        initData();
    }

    void initData(){
        datas = new ArrayList<>();
        baseQuickAdapter = new BaseQuickAdapter<PreferenceModel,BaseViewHolder>(R.layout.activity_preference_setting_item,datas) {
            @Override
            protected void convert(BaseViewHolder helper, PreferenceModel item) {
                ImageView item_image = helper.getView(R.id.item_icon);
                if (item.getType()!=null){
                    switch (item.getType()){
                        case "人文":
                            Glide.with(PreferenceSettingActivity.this).load(R.mipmap.icon_humanity).into(item_image);
                            break;
                        case "自然":
                            Glide.with(PreferenceSettingActivity.this).load(R.mipmap.icon_natural).into(item_image);
                            break;
                        case "社会":
                            Glide.with(PreferenceSettingActivity.this).load(R.mipmap.icon_society).into(item_image);
                            break;
                        case "历史":
                            Glide.with(PreferenceSettingActivity.this).load(R.mipmap.icon_history).into(item_image);
                            break;
                        case "综合":
                            Glide.with(PreferenceSettingActivity.this).load(R.mipmap.icon_comprehensive).into(item_image);
                            break;
                        default:
                            Glide.with(PreferenceSettingActivity.this).load(R.mipmap.icon_default).into(item_image);
                            break;
                    }
                }

                helper.setText(R.id.item_title,item.getPreference_name());

            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (datas!=null&&datas.get(position)!=null){
                    Intent intent = getIntent();
                    if (intent!=null){
                        intent.putExtra("preference_type",datas.get(position).getType());
                        setResult(1000,intent);
                        finish();
                    }
                }
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseQuickAdapter);
        getData();
    }

    void getData(){
        RequestParams ps=new RequestParams();
//        ps.add("uid", MyApplication.getApp().getU().getId());//设置参数
        UserClient.get("preference/getAllPreferenceToMobile",ps,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                datas.clear();
                datas = JSON.parseArray(content,PreferenceModel.class);
                baseQuickAdapter.addData(datas);
            }
        });
    }
}
