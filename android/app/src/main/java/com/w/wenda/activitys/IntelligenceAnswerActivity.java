package com.w.wenda.activitys;

import android.content.Entity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.w.wenda.R;
import com.w.wenda.adapater.MessageModel;
import com.w.wenda.adapater.MultiDelegateAdapter;
import com.w.wenda.model.BaseKnowModel;
import com.w.wenda.model.ComprehensiveKnowModel;
import com.w.wenda.model.HistoryKnowModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.Log;

public class IntelligenceAnswerActivity extends AppCompatActivity {

    private String preference_type = "";
    private MultiDelegateAdapter multiDelegateAdapter;

    private List<MessageModel> datas;

    @BindView(R.id.preference_type)
    TextView preference_type_txt;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.preference_setting)
    void preference_setting(){
        Intent intent = new Intent(this,PreferenceSettingActivity.class);
        startActivityForResult(intent,1000);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelligence_answer);
        ButterKnife.bind(this);
        initData();

    }

    void initData(){
        datas = new ArrayList<>();
        datas.add(new MessageModel(MessageModel.MineMsg,"hello"));
        datas.add(new MessageModel(MessageModel.ServerMsgText,"Hi"));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        multiDelegateAdapter = new MultiDelegateAdapter();
        recyclerView.setAdapter(multiDelegateAdapter);

        multiDelegateAdapter.setNewData(datas);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1000:
                if (data!=null){
                    preference_type = data.getStringExtra("preference_type");
                    preference_type_txt.setText(preference_type);
                }
                break;
        }
    }
}
