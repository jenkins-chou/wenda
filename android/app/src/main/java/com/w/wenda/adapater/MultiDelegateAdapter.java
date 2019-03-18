package com.w.wenda.adapater;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.w.wenda.R;
import com.w.wenda.activitys.WebActivity;
import com.w.wenda.model.MessageModel;
import com.w.wenda.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import zuo.biao.library.util.Log;

public class MultiDelegateAdapter extends BaseQuickAdapter<MessageModel, BaseViewHolder> {

    private OnCallBack onCallBack;
    public MultiDelegateAdapter() {
        super(null);
        //Step.1
        setMultiTypeDelegate(new MultiTypeDelegate<MessageModel>() {
            @Override
            protected int getItemType(MessageModel entity) {
                //根据你的实体类来判断布局类型
                return entity.type;
            }
        });
        //Step.2
        getMultiTypeDelegate()
                .registerItemType(MessageModel.MineMsgText, R.layout.item_message_mine_text_layout)
                .registerItemType(MessageModel.MineMsgEmoji, R.layout.item_message_mine_emoji_layout)
                .registerItemType(MessageModel.ServerMsgText, R.layout.item_message_server_text_layout)
                .registerItemType(MessageModel.ServerMsgUrl, R.layout.item_message_server_url_layout)
                .registerItemType(MessageModel.ServerMsgImage, R.layout.item_message_server_image_layout)
                .registerItemType(MessageModel.ServerMsgEmoji, R.layout.item_message_server_emoji_layout)
                .registerItemType(MessageModel.ServerMsgTextList, R.layout.item_message_server_text_list_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MessageModel entity) {
        //Step.3
        switch (helper.getItemViewType()) {
            case MessageModel.MineMsgText:
                helper.setText(R.id.message,entity.getMessage());
                break;
            case MessageModel.MineMsgEmoji:
                helper.setText(R.id.emoji,new String(Character.toChars(entity.getEmojiUnicode())));
                break;
            case MessageModel.ServerMsgText:
                helper.setText(R.id.message,entity.getMessage());
                break;
            case MessageModel.ServerMsgUrl:
                TextView message = helper.getView(R.id.message);
                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext, WebActivity.class).putExtra("url",entity.getMessage()));
                    }
                });
                helper.setText(R.id.message,entity.getMessage());
                break;
            case MessageModel.ServerMsgImage:
                helper.setText(R.id.message,entity.getMessage());
                ImageView imageView = helper.getView(R.id.image);
                if (imageView!=null&&entity.getImageUrl()!=null){
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.mipmap.icon_default);
                    requestOptions.error(R.mipmap.icon_default);
                    Glide.with(mContext).load(entity.getImageUrl()).apply(requestOptions).into(imageView);
                }
                break;
            case MessageModel.ServerMsgEmoji:
                helper.setText(R.id.emoji,new String(Character.toChars(entity.getEmojiUnicode())));
                break;
            case MessageModel.ServerMsgTextList:
                String messageList = entity.getMessageList();
                if (StringUtil.isNotEmpty(messageList)){
                    String[] arr = messageList.split(",");
                    final List<String> list = Arrays.asList(arr);
                    if (list!=null){
                        RecyclerView item_recyclerView = helper.getView(R.id.item_recyclerView);
                        item_recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));

                        BaseQuickAdapter adapterItem = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_message_server_text_list_item,list) {
                            @Override
                            protected void convert(BaseViewHolder helper, String item) {
                                helper.setText(R.id.item_text,item);
                            }
                        };
                        adapterItem.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                if (onCallBack!=null){
                                    onCallBack.callBack(list.get(position));
                                }
                            }
                        });

                        item_recyclerView.setAdapter(adapterItem);
                    }
                }
                break;
        }
    }

    public void setOnCallBack(OnCallBack onCallBack){
        this.onCallBack = onCallBack;
    }

    public interface OnCallBack{
        void callBack(String value);
    }
}
