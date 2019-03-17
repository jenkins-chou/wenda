package com.w.wenda.activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.R;
import com.w.wenda.pojo.CommonData;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.MyToastUtil;
import com.w.wenda.util.StringUtil;
import com.w.wenda.util.UserClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityQuestionAddActivity extends AppCompatActivity {

    private Context con;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.detail)
    TextView detail;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.submit)
    void submit(){
        String title_str = title.getText().toString();
        String detail_str = detail.getText().toString();
        if (StringUtil.isNotEmpty(title_str)&&StringUtil.isNotEmpty(detail_str)){
            addQuestion(title_str,detail_str);
        }else{
            Toast.makeText(this, "请输入完整描述", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_question_add);

        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        con = this;
    }

    void addQuestion(String title,String detail){
        RequestParams ps = new RequestParams();
        ps.add("title",title);
        ps.add("detail",detail);
        ps.add("creator_id",MyApplication.getU().getId());
        ps.add("creator_name",MyApplication.getU().getName());
        ps.add("useful",0+"");
        ps.add("useless",0+"");
        ps.add("del","normal");

        UserClient.get("community_question/addQuestion", ps, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                CommonData data = JSON.parseObject(content, CommonData.class);
                if (data.getCode().equals("200")) {
                    MyToastUtil.ShowToast(con, "添加成功");
                    finish();
                } else {
                    MyToastUtil.ShowToast(con, data.getMsg());
                }
            }


        });
    }
}
