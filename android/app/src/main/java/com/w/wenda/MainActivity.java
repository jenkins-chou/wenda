package com.w.wenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaeger.library.StatusBarUtil;
import com.w.wenda.activitys.IntelligenceAnswerActivity;
import com.w.wenda.base.MyBaseActivity;

import org.raphets.roundimageview.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {

    @BindView(R.id.frag_attention_g1)
    RoundImageView frag_attention_g1;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottom_navigation_bar;

    @OnClick(R.id.intel_answer_helper)
    void intel_answer_helper(){
        startActivity(new Intent(MainActivity.this,IntelligenceAnswerActivity.class));
    }
    @OnClick(R.id.qa_quick)
    void qa_quick(){
        startActivity(new Intent(MainActivity.this,wd_main.class).putExtra("type","全部"));
    }

    @OnClick(R.id.qa_classification)
    void qa_classification(){
        startActivity(new Intent(MainActivity.this,type_choose.class).putExtra("type","全部"));
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black),0);

        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552456252475&di=f952aba264e27b091c67fe8cc9fae6dd&imgtype=0&src=http%3A%2F%2Fphoto.16pic.com%2F00%2F51%2F93%2F16pic_5193989_b.jpg")
                .into(frag_attention_g1);
    }

    @Override
    protected void initData() {
        bottom_navigation_bar
                .setActiveColor(R.color.colorAccent)
                .setInActiveColor(R.color.base_normal_navcolor)
                .setMode(BottomNavigationBar.MODE_DEFAULT)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.mipmap.main_nav_mine, "个人信息").setActiveColorResource(R.color.base_normal_navcolor))
                .addItem(new BottomNavigationItem(R.mipmap.his, "历史记录").setActiveColorResource(R.color.base_normal_navcolor))
                .setFirstSelectedPosition(0)
                .initialise();
        bottom_navigation_bar.setAutoHideEnabled(true);
        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.e("position",""+position);
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,UserMain.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,his_list.class));
                        break;
                    default:break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,UserMain.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,his_list.class));
                        break;
                    default:break;
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }
}
