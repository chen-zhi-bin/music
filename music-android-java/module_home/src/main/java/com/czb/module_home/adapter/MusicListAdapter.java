package com.czb.module_home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_home.R;
import com.czb.module_home.model.bean.TopMusicBean;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MusicListAdapter extends BaseQuickAdapter<TopMusicBean.DataBean.ListBean, BaseViewHolder> {

    private Random mRandom = new Random();

    public MusicListAdapter() {
        super(R.layout.home_music_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, TopMusicBean.DataBean.ListBean data) {
        viewHolder.setText(R.id.home_top_name, data.getMusicName());
        String singerName = data.getSingerName();
        if (singerName == null || singerName.equals("")) {
            singerName = "未知歌手";
        }
        viewHolder.setText(R.id.home_top_singer_name, singerName);
        viewHolder.setText(R.id.home_top_count, mRandom.nextInt(50) + "");
        ImageView topImage = viewHolder.getView(R.id.home_top_pic_id);
        String picId = data.getPicId();
        if (picId == null || picId.equals("")) {
            Glide.with(topImage.getContext())
                    .load(R.mipmap.iv_default_music)
                    .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                    .circleCrop()       //圆角
                    .into(topImage);
        } else {
            Glide.with(topImage.getContext())
                    .load(Constants.BASE_URL_IMAGE + picId)
                    .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                    .circleCrop()       //圆角
                    .into(topImage);
        }
        ImageView topIV = viewHolder.getView(R.id.home_top);
        Glide.with(topIV.getContext())
                .load(R.mipmap.iv_top_hot)
                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                .circleCrop()       //圆角
                .into(topIV);
    }
}
