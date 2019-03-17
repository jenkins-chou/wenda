package com.w.wenda.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.R;
import com.w.wenda.model.CommunityQuestionModel;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.UserClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.util.Log;

public class CommunityQuestionMineActivity extends AppCompatActivity {
    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.add_question)
    void add_question(){

    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<CommunityQuestionModel> datas;
    private BaseQuickAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_question_mine);

        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        datas = new ArrayList<>();
        adapter = new BaseQuickAdapter<CommunityQuestionModel,BaseViewHolder>(R.layout.activity_community_question_item,datas) {
            @Override
            protected void convert(BaseViewHolder helper, final CommunityQuestionModel item) {
                helper.setText(R.id.creator_name,item.getCreator_name());
                helper.setText(R.id.title,item.getTitle());
                helper.setText(R.id.detail,item.getDetail());
                helper.setText(R.id.useful,"有用 "+item.getUseful());
                helper.setText(R.id.useless,"无用 "+item.getUseless());
//                TextView add_answer = helper.getView(R.id.add_answer);
//                add_answer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(CommunityQuestionMineActivity.this,CommunityAnswerAddActivity.class);
//                        intent.putExtra("question_id",item.getId()+"");
//                        startActivity(intent);
//                    }
//                });
            }
        };

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CommunityQuestionMineActivity.this,CommunityAnswerListActivity.class);
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
        ps.add("creator_id",MyApplication.getU().getId());
        UserClient.get("community_question/getMyQuestion", ps, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                Log.e("content",content);
                List<CommunityQuestionModel> data=JSON.parseArray(content,CommunityQuestionModel.class);
                if (data!=null){
                    datas = data;
                    Log.e("datas notifyDataSetChanged",datas.toString());
                    adapter.setNewData(datas);
                }
            }


        });
    }

}
