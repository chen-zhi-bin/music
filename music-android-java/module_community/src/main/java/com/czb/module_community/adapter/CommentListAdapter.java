package com.czb.module_community.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_community.R;
import com.czb.module_community.model.bean.CommentAndMusicBean;
import com.makeramen.roundedimageview.RoundedImageView;

public class CommentListAdapter extends BaseQuickAdapter<CommentAndMusicBean.DataBean.ListBean, BaseViewHolder> {

    {
        addChildClickViewIds(R.id.music_info);
    }

    public CommentListAdapter() {
        super(R.layout.community_adapter_comment_list);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CommentAndMusicBean.DataBean.ListBean listBean) {
        RoundedImageView userAvatar = baseViewHolder.findView(R.id.iv_fish_pond_avatar);
        Glide.with(userAvatar.getContext())
                .load(Constants.BASE_URL_IMAGE+listBean.getAvatar())
                .placeholder(R.mipmap.ic_default_avatar)
                .circleCrop()       //圆角
                .into(userAvatar);
        String userName = listBean.getUserName();
        baseViewHolder.setText(R.id.cb_fish_pond_nick_name,userName);
        baseViewHolder.setText(R.id.tv_fish_pond_desc,listBean.getCommentTime());
        baseViewHolder.setText(R.id.tv_reply_msg,listBean.getComment());
        ImageView coverIv = baseViewHolder.findView(R.id.cover_iv);
        Glide.with(coverIv.getContext())
                .load(Constants.BASE_URL_IMAGE+listBean.getPicId())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .placeholder(R.mipmap.iv_default_music)
                .into(coverIv);
        baseViewHolder.setText(R.id.music_name,listBean.getMusicName());
        baseViewHolder.setText(R.id.musician_name,listBean.getSingerName());
    }
}
