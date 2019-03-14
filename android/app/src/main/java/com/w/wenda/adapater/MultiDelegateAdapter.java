package com.w.wenda.adapater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.w.wenda.R;

public class MultiDelegateAdapter extends BaseQuickAdapter<MessageModel, BaseViewHolder> {

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
                .registerItemType(MessageModel.MineMsg, R.layout.item_message_mine_layout)
                .registerItemType(MessageModel.ServerMsgText, R.layout.item_message_server_text_layout)
                .registerItemType(MessageModel.ServerMsgUrl, R.layout.item_message_server_url_layout)
                .registerItemType(MessageModel.ServerMsgImage, R.layout.item_message_server_image_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageModel entity) {
        //Step.3
        switch (helper.getItemViewType()) {
            case MessageModel.MineMsg:
                helper.setText(R.id.message,entity.getMessage());
                break;
            case MessageModel.ServerMsgText:
                helper.setText(R.id.message,entity.getMessage());
                break;
            case MessageModel.ServerMsgUrl:
                helper.setText(R.id.message,entity.getMessage());
                break;
            case MessageModel.ServerMsgImage:
                helper.setText(R.id.message,entity.getMessage());
                break;
        }
    }
}
