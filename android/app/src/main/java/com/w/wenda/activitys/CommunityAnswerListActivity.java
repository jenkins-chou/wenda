package com.w.wenda.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.R;
import com.w.wenda.model.CommunityAnswerModel;
import com.w.wenda.model.CommunityQuestionModel;
import com.w.wenda.util.StringUtil;
import com.w.wenda.util.UserClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.util.Log;

public class CommunityAnswerListActivity extends AppCompatActivity {

    private String question_id;
    private String question_title;
    private String question_detail;
    private Context con;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.detail)
    TextView detail;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.add_answer)
    void add_answer(){
        if (StringUtil.isNotEmpty(question_id)){
            Intent intent = new Intent(this,CommunityAnswerAddActivity.class);
            intent.putExtra("question_id",question_id+"");
            startActivity(intent);
        }else{
            Toast.makeText(con, "页面初始化失败，请重试", Toast.LENGTH_SHORT).show();
        }
         
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<CommunityAnswerModel> datas;
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_answer_list);

        ButterKnife.bind(this);
        initData();

    }

    private void initData(){
        if (getIntent()!=null){
            question_id = getIntent().getStringExtra("question_id");
            title.setText(getIntent().getStringExtra("question_title"));
            detail.setText(getIntent().getStringExtra("question_detail"));


        }
        datas = new ArrayList<>();
        adapter = new BaseQuickAdapter<CommunityAnswerModel,BaseViewHolder>(R.layout.activity_community_answer_item,datas) {
            @Override
            protected void convert(BaseViewHolder helper, CommunityAnswerModel item) {
                helper.setText(R.id.creator_name,item.getCreator_name());
                helper.setText(R.id.answer,item.getAnswer());
            }
        };

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StringUtil.isNotEmpty(question_id)){
            getData();
        }
    }

    void getData(){
        RequestParams ps = new RequestParams();
        ps.add("question_id",question_id);
        UserClient.get("community_answer/getAnswerByQuestion", ps, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                Log.e("content",content);
                List<CommunityAnswerModel> data=JSON.parseArray(content,CommunityAnswerModel.class);
                if (data!=null){
                    datas = data;
                    Log.e("datas notifyDataSetChanged",datas.toString());
                    adapter.setNewData(datas);
                }
            }


        });
    }

}
