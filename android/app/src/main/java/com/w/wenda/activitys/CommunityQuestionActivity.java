package com.w.wenda.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.MainActivity;
import com.w.wenda.R;
import com.w.wenda.model.CommunityQuestionModel;
import com.w.wenda.pojo.CommonData;
import com.w.wenda.pojo.his;
import com.w.wenda.util.MyToastUtil;
import com.w.wenda.util.StringUtil;
import com.w.wenda.util.UserClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.util.Log;

public class CommunityQuestionActivity extends AppCompatActivity {
    private Context con;
    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.add_question)
    void add_question(){
        startActivity(new Intent(this,CommunityQuestionAddActivity.class));
    }

    @OnClick(R.id.mine_question)
    void mine_question(){
        startActivity(new Intent(this,CommunityQuestionMineActivity.class));
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<CommunityQuestionModel> datas;
    private BaseQuickAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_question);

        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        con = this;
        datas = new ArrayList<>();
        adapter = new BaseQuickAdapter<CommunityQuestionModel,BaseViewHolder>(R.layout.activity_community_question_item,datas) {
            @Override
            protected void convert(BaseViewHolder helper, final CommunityQuestionModel item) {
                Log.e("CommunityQuestionModel",item.toString());
                helper.setText(R.id.creator_name,item.getCreator_name());
                helper.setText(R.id.title,item.getTitle());
                helper.setText(R.id.detail,item.getDetail());
                helper.setText(R.id.useful,"有用"+item.getUseful());
                helper.setText(R.id.useless,"无用"+item.getUseless());
//                TextView add_answer = helper.getView(R.id.add_answer);
//                add_answer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(CommunityQuestionActivity.this,CommunityAnswerAddActivity.class);
//                        intent.putExtra("question_id",item.getId()+"");
//                        startActivity(intent);
//                    }
//                });
            }
        };

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CommunityQuestionActivity.this,CommunityAnswerListActivity.class);
                intent.putExtra("question_id",datas.get(position).getId()+"");
                intent.putExtra("question_title",datas.get(position).getTitle()+"");
                intent.putExtra("question_detail",datas.get(position).getDetail()+"");
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    void getData(){
        RequestParams ps = new RequestParams();
        UserClient.get("community_question/getAllQuestion", ps, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                Log.e("content",content);
                List<CommunityQuestionModel> data=JSON.parseArray(content,CommunityQuestionModel.class);
//                Log.e("datas",data!=null?data.toString():"");
//                List<CommunityQuestionModel> data = (List<CommunityQuestionModel>)new Gson().fromJson(content, CommunityQuestionModel.class);
                if (data!=null){
                    datas = data;
                    Log.e("datas notifyDataSetChanged",datas.toString());
                    adapter.setNewData(datas);
                }
            }


        });
    }
}
