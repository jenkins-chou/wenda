package com.w.wenda.activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.w.wenda.R;
import com.w.wenda.util.StringUtil;
import com.w.wenda.util.Url;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    private Context con;

    @BindView(R.id.title)
    TextView title;
    
    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.submit)
    void submit(){
        String url_str = title.getText().toString();
        if (StringUtil.isNotEmpty(url_str)){
            Url.setUrl(url_str);
            Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }

    void initData(){
        title.setText(Url.url());
    }
}
