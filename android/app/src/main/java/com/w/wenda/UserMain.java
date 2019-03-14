package com.w.wenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;


import com.jaeger.library.StatusBarUtil;
import com.w.wenda.MainActivity;
import com.w.wenda.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseBottomTabActivity;

public class UserMain extends BaseBottomTabActivity {


    private wo shouye;

//    @OnClick(R.id.back)
//    void back(){
//        finish();
//    }

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black),0);

        initView();
        initData();
        initEvent();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {
        super.initView();
        exitAnim = R.anim.bottom_push_out;
        shouye = wo.createInstance();
    }

    @Override
    protected void selectTab(int position) {

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected int[] getTabClickIds() {
        return new int[]{R.id.llBottomTabTab0, R.id.llBottomTabTab1};
    }

    @Override
    protected int[][] getTabSelectIds() {
        return new int[][]{
                new int[]{R.id.ivBottomTabTab0, R.id.ivBottomTabTab1},//顶部图标
                new int[]{R.id.tvBottomTabTab0, R.id.tvBottomTabTab1}//底部文字
        };
    }

    @Override
    public int getFragmentContainerResId() {
        return R.id.flMainTabFragmentContainer;
    }

    @Override
    protected Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return shouye;
            case 1:
                return xg.createInstance();
            default:
                return wo.createInstance();
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }
}
