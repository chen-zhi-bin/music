package com.czb.module_community.adapter;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_community.R;
import com.czb.module_community.model.bean.CommentBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<CommentBean.DataBean.ListBean, BaseViewHolder> {

    {
        addChildClickViewIds(R.id.iv_fish_pond_comment,R.id.tv_child_reply_msg_all);
    }

    public CommentAdapter() {
        super(R.layout.community_adapter_comment);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CommentBean.DataBean.ListBean comment) {
        RoundedImageView ivAvatar = viewHolder.getView(R.id.iv_fish_pond_avatar);
        Glide.with(ivAvatar.getContext())
                .load(Constants.BASE_URL_IMAGE+comment.getAvatar())
                .placeholder(R.mipmap.ic_default_avatar)
                .circleCrop()       //圆角
                .into(ivAvatar);
        viewHolder.setText(R.id.tv_fish_pond_desc,comment.getCreateTime());
        viewHolder.setText(R.id.cb_fish_pond_nick_name,comment.getUserName());
        LinearLayout tvBuildReplyMsgContainer = viewHolder.getView(R.id.tv_build_reply_msg_container);
        List<CommentBean.DataBean.ListBean.SubCommentsBean.SubCommentBean> subComments = comment.getSubComments().getSubComment();
        int buildHeight = comment.getSubComments().getTotalCount();
        if (subComments.isEmpty()){
            tvBuildReplyMsgContainer.setVisibility(View.GONE);
        }
        viewHolder.setText(R.id.tv_reply_msg,comment.getComment());
        TextView tvChildReplyMsg = viewHolder.getView(R.id.tv_child_reply_msg);
        TextView tvChildReplyMsg1 = viewHolder.getView(R.id.tv_child_reply_msg1);
        tvChildReplyMsg.setVisibility(View.GONE);
        if (subComments.size()>=1&&subComments.get(0) != null) {
            CommentBean.DataBean.ListBean.SubCommentsBean.SubCommentBean subCommentBean = subComments.get(0);
            String userName = subCommentBean.getUserName();
            String parentUserName = subCommentBean.getParentUserName();
            userName = "<span style=\"color:#0071E0;\">"+userName+"</span>";
            if (parentUserName!=null&&!parentUserName.equals("")&&!parentUserName.equals("null")){
                userName = userName + " 回复" +parentUserName;
            }
            String comment1 = subCommentBean.getComment();
            comment1 = userName+":"+comment1;
            tvChildReplyMsg.setText(Html.fromHtml(comment1));
            tvChildReplyMsg.setVisibility(View.VISIBLE);
//            tvChildReplyMsg.setMovementMethod(LinkMovementMethod.getInstance());//使点击事件起作用
        }
        tvChildReplyMsg1.setVisibility(View.GONE);
        if (subComments.size()>=2&&subComments.get(1) != null) {
            CommentBean.DataBean.ListBean.SubCommentsBean.SubCommentBean subCommentBean = subComments.get(1);
            String userName = subCommentBean.getUserName();
            userName = "<span style=\"color:#0071E0;\">"+userName+"</span>";
            String comment1 = subCommentBean.getComment();
            comment1 = userName+":"+comment1;
            tvChildReplyMsg1.setText(Html.fromHtml(comment1));
            tvChildReplyMsg1.setVisibility(View.VISIBLE);
        }
        TextView tvChildReplyMsgAll = viewHolder.getView(R.id.tv_child_reply_msg_all);
        tvChildReplyMsgAll.setText("查看全部"+buildHeight+"条回复");
        tvChildReplyMsgAll.setVisibility(buildHeight>2?View.VISIBLE:View.GONE);
    }
}
