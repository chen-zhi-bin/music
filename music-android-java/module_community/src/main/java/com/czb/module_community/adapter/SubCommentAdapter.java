package com.czb.module_community.adapter;

import android.text.Html;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_community.R;
import com.czb.module_community.model.bean.SubCommentBean;
import com.makeramen.roundedimageview.RoundedImageView;

public class SubCommentAdapter extends BaseQuickAdapter<SubCommentBean.DataBean.ListBean, BaseViewHolder> {

    {
        addChildClickViewIds(R.id.iv_fish_pond_comment);
    }

    public SubCommentAdapter() {
        super(R.layout.community_adapter_sub_comment_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SubCommentBean.DataBean.ListBean listBean) {
        RoundedImageView userAvatar = baseViewHolder.findView(R.id.iv_fish_pond_avatar);
        Glide.with(userAvatar.getContext())
                .load(Constants.BASE_URL_IMAGE+listBean.getAvatar())
                .placeholder(R.mipmap.ic_default_avatar)
                .circleCrop()       //圆角
                .into(userAvatar);
        String userName = listBean.getUserName();
        String parentUserName = listBean.getParentUserName();
        String comment = listBean.getComment();
        if (parentUserName!=null&&!parentUserName.equals("")&&!parentUserName.equals("null")){
            comment = "回复@" +"<span style=\"color:#0071E0;\">"+parentUserName+"</span>"+": "+comment;
        }
        baseViewHolder.setText(R.id.cb_fish_pond_nick_name, userName);
        baseViewHolder.setText(R.id.tv_fish_pond_desc,listBean.getCreateTime());
        baseViewHolder.setText(R.id.tv_reply_msg, Html.fromHtml(comment));
    }
}
