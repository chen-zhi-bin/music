package com.czb.module_home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_home.R;
import com.czb.module_home.model.bean.MusicianMusicBean;
import com.czb.module_home.model.bean.RecommendMusicBean;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MusicianAdapter extends BaseQuickAdapter<MusicianMusicBean.DataBean.ListBean, BaseViewHolder> {

    private Random mRandom = new Random();

    public MusicianAdapter() {
        super(R.layout.home_adapter_musician);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, MusicianMusicBean.DataBean.ListBean data) {
        viewHolder.setText(R.id.home_recommend_name, data.getMusicName());
        String recommendSingerName = data.getSingerName();
        if (recommendSingerName == null || recommendSingerName.equals("")) {
            recommendSingerName = "未知歌手";
        }
        viewHolder.setText(R.id.home_recommend_singer_name, recommendSingerName);
        viewHolder.setText(R.id.home_recommend_count, mRandom.nextInt(50) + "");
        ImageView recommendImage = viewHolder.getView(R.id.home_recommend_pic_id);
        String recommendImageUrl = data.getPicId() + "";
        if (recommendImageUrl == null || recommendImageUrl.equals("") || recommendImageUrl.equals("null")) {
            Glide.with(recommendImage.getContext())
                    .load(R.mipmap.iv_default_music)
                    .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                    .circleCrop()       //圆角
                    .into(recommendImage);
        } else {
            Glide.with(recommendImage.getContext())
                    .load(Constants.BASE_URL_IMAGE + recommendImageUrl)
                    .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                    .circleCrop()       //圆角
                    .into(recommendImage);
        }
    }
}
