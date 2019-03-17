package com.w.wenda.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.w.wenda.R;
import com.w.wenda.greendao.DaoSession;
import com.w.wenda.greendao.MessageModelDao;
import com.w.wenda.model.EmojiModel;
import com.w.wenda.model.MessageModel;
import com.w.wenda.adapater.MultiDelegateAdapter;
import com.w.wenda.util.MyApplication;
import com.w.wenda.util.UserClient;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import zuo.biao.library.util.Log;

public class IntelligenceAnswerActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private String preference_type = "";
    private MultiDelegateAdapter multiDelegateAdapter;
    private BaseQuickAdapter baseQuickAdapter;

    private List<MessageModel> datas;
    private List<EmojiModel.EmojiItemModel> emojiItemModels;
    private boolean isEmojiShow = false;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.input_message)
    EditText input_message;//输入框
    @BindView(R.id.send_message)
    TextView send_message;//发送按钮
    @BindView(R.id.emoji_icon)
    ImageView emoji_icon;//表情icon
    @BindView(R.id.emoji_bar)
    RecyclerView emoji_bar;//表情列表

    @BindView(R.id.preference_type)
    TextView preference_type_txt;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //返回函数
    @OnClick(R.id.back)
    void back(){
        finish();
    }

    //右上角设置按钮
    @OnClick(R.id.preference_setting)
    void preference_setting(View view){
        showPopupWindow(view);
    }

    //显示 or 隐藏表情面板
    @OnClick(R.id.emoji_icon)
    void showEmojiBar(){
        hideSoftKeyboard(this);
        if (isEmojiShow){
            isEmojiShow = false;
            emoji_bar.setVisibility(View.GONE);
            Glide.with(this).load(R.mipmap.emoji).into(emoji_icon);
            smoothRecyclerViewToBottom();
        }else{
            isEmojiShow = true;
            emoji_bar.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.mipmap.emoji_select).into(emoji_icon);
            smoothRecyclerViewToBottom();
        }
    }

    //点击发送按钮
    @OnClick(R.id.send_message)
    void send_message(){
        String msg = input_message.getText().toString();
        if (msg == null || msg.equals("")){
            Toast.makeText(this, "不能发送空信息", Toast.LENGTH_SHORT).show();
        }else{
            if (datas!=null){
                input_message.setText("");
                title.setText("小助手正在思考..");
                MessageModel msgModel = new MessageModel(MessageModel.MineMsgText,msg);
                sendMessage(MessageModel.MineMsgText,msgModel);
            }
        }
    }

    //输入框文本输入监听器
    @OnTextChanged(value = R.id.input_message, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("TAG", "onTextChanged , s = " + s);
        String msg = input_message.getText().toString();
        if (msg == null || msg.equals("")){
            send_message.setEnabled(false);
        }else{
            send_message.setEnabled(true);
        }
    }

    //页面创建回调函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelligence_answer);
        ButterKnife.bind(this);
        initData();
        initInputMessage();//监听文本框焦点变化情况
        initEmoji();//初始化表情列表
    }

    //初始化数据
    void initData(){
        daoSession = MyApplication.getDaoSession();
        datas = queryMessageRecordFromSqlite();
        emojiItemModels = new ArrayList<>();
//        datas.add(new MessageModel(MessageModel.MineMsg,"hello"));
//        datas.add(new MessageModel(MessageModel.ServerMsgText,"Hi"));
//        datas.add(new MessageModel(MessageModel.ServerMsgImage,"Hi","","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552565391653&di=e72d77c8ec206bcae819bd39f44213ef&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F2e2eb9389b504fc27c224b2debdde71190ef6d9d.jpg"));
//        datas.add(new MessageModel(MessageModel.MineMsg,"这不是我需要的"));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        multiDelegateAdapter = new MultiDelegateAdapter();
        recyclerView.setAdapter(multiDelegateAdapter);

        multiDelegateAdapter.setNewData(datas);
        recyclerView.smoothScrollToPosition(datas.size());
    }

    //初始化输入框
    void initInputMessage(){
        if (input_message!=null){
            input_message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    smoothRecyclerViewToBottom();
                }
            });
        }
    }

    //滚动recyclerView到底部
    void smoothRecyclerViewToBottom(){
        if (recyclerView!=null&&datas!=null){
            recyclerView.smoothScrollToPosition(datas.size());
        }
    }

    //页面重现回调函数
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

    //发送信息
    void sendMessage(int type,MessageModel messageModel){
        if (messageModel==null)return;
        //本地缓存
        addMessageToSqlite(messageModel);
        multiDelegateAdapter.addData(messageModel);
        smoothRecyclerViewToBottom();
        RequestParams ps=new RequestParams();
        switch (type){
            case MessageModel.MineMsgText:
                ps.add("user_id",MyApplication.getU().getId());
                ps.add("question_type",MessageModel.MineMsgText+"");
                ps.add("question",messageModel.getMessage());
                UserClient.get("answer/getAnswer",ps,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        Log.e("content",content);
                        MessageModel messageModel = new Gson().fromJson(content,MessageModel.class);
                        //本地缓存服务器的回答
                        addMessageToSqlite(messageModel);
                        multiDelegateAdapter.addData(messageModel);
                        smoothRecyclerViewToBottom();
                        title.setText("智能小助手");
                    }
                });
                break;
            case MessageModel.MineMsgEmoji:
                MessageModel serverMessageModel = new MessageModel(MessageModel.ServerMsgEmoji,128513);
                addMessageToSqlite(serverMessageModel);
                multiDelegateAdapter.addData(serverMessageModel);
                smoothRecyclerViewToBottom();
                break;
        }
    }

    void getMessageFromServer(MessageModel messageModel){

    }

    //添加一项本地聊天记录
    void addMessageToSqlite(MessageModel messageModel){
        if (messageModel!=null&&daoSession!=null){
            messageModel.setUser_id(MyApplication.getU().getId());
            try{
                Long result = daoSession.getMessageModelDao().insert(messageModel);
                Log.e("addMessageToSqlite",""+result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //获取本地聊天记录
    List<MessageModel> queryMessageRecordFromSqlite(){
        List<MessageModel> results = new ArrayList<>();
        if (daoSession!=null){
            if (daoSession.getMessageModelDao().queryBuilder().list()!=null){
//                results = daoSession.getMessageModelDao().loadAll();
                results = daoSession.queryBuilder(MessageModel.class).where(MessageModelDao.Properties.User_id.eq(MyApplication.getU().getId())).list();
                Log.e("results",results.size()+" "+results.toString());
            }else{
                Log.e("results","daoSession.getMessageModelDao().queryBuilder().list() is null"+daoSession.getMessageModelDao().queryBuilder().list());
            }
        }else{
            Log.e("results","daoSession is null");
        }
        return results;
    }

    //删除本地聊天记录
    void deleteLocalMessageRecord(){
        if (daoSession!=null){
            daoSession.getMessageModelDao().deleteAll();
            //刷新数据
            datas.clear();
            multiDelegateAdapter.notifyDataSetChanged();
        }
    }

    //隐藏软键盘(可用于Activity，Fragment)
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //显示悬浮按钮
    private void showPopupWindow(View view) {


        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 设置按钮的点击事件
        Button delete_record = (Button) contentView.findViewById(R.id.delete_record);
        delete_record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteLocalMessageRecord();
                popupWindow.dismiss();
            }
        });

        Button preference_setting_btn = (Button) contentView.findViewById(R.id.preference_setting_btn);
        preference_setting_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntelligenceAnswerActivity.this,PreferenceSettingActivity.class);
                startActivityForResult(intent,1000);
                popupWindow.dismiss();
            }
        });



        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.pop_window_bg));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

    //初始化emoji表情
    private void initEmoji(){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("emojiList.json") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            EmojiModel emojiModels = (EmojiModel)new Gson().fromJson(Result,EmojiModel.class);
            if (emojiModels.getEmoji_list()!=null){
                emojiItemModels = emojiModels.getEmoji_list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        emoji_bar.setLayoutManager(new StaggeredGridLayoutManager(7,1));
        baseQuickAdapter = new BaseQuickAdapter<EmojiModel.EmojiItemModel,BaseViewHolder>(R.layout.emoji_item_layout,emojiItemModels) {
            @Override
            protected void convert(BaseViewHolder helper, EmojiModel.EmojiItemModel item) {
                helper.setText(R.id.item_emoji,new String(Character.toChars(item.getUnicode())));
            }
        };

        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageModel messageModel = new MessageModel(MessageModel.MineMsgEmoji,emojiItemModels.get(position).getUnicode());
                sendMessage(MessageModel.MineMsgEmoji,messageModel);
            }
        });

        emoji_bar.setAdapter(baseQuickAdapter);
    }

}
