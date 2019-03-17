package com.w.wenda.activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
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
import zuo.biao.library.util.Log;

public class CommunityAnswerAddActivity extends AppCompatActivity {
    private String question_id;
    private Context con;
    @BindView(R.id.answer)
    EditText answer;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.submit)
    void submit(){
        String answer_str = answer.getText().toString();
       if (StringUtil.isNotEmpty(answer_str)&&StringUtil.isNotEmpty(question_id)){
           addAnswer(answer_str);
       }else{
           Toast.makeText(this, "请输入", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_answer_add);

        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        con = this;

//        Log.e("str",getIntent().getExtras().toString());
        if (getIntent()!=null){
            question_id = getIntent().getStringExtra("question_id");
            Log.e("question_id ",getIntent().getStringExtra("question_id")+"");
            Log.e("question_id ",question_id+"");
        }else{
            Toast.makeText(con, "question_id is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void addAnswer(String answer){
        RequestParams ps = new RequestParams();
        ps.add("question_id",question_id);
        ps.add("answer",answer);
        ps.add("creator_id",MyApplication.getU().getId());
        ps.add("creator_name",MyApplication.getU().getName());
        ps.add("useful",0+"");
        ps.add("useless",0+"");
        ps.add("del","normal");

        UserClient.get("community_answer/addAnswer", ps, new AsyncHttpResponseHandler() {


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
