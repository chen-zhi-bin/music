package com.czb.module_home.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.bean.TitleMultiBean;
import com.czb.module_base.common.Constants;
import com.czb.module_home.R;
import com.czb.module_home.model.bean.RecommendMusicBean;
import com.czb.module_home.model.bean.TopMusicBean;

import java.util.Random;

public class HomeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    {
        addItemType(Constants.MultiItemType.TYPE_TITLE, R.layout.modulebase_common_adapter_title);
        addItemType(Constants.MultiItemType.TYPE_TOP, R.layout.home_top_music);
        addItemType(Constants.MultiItemType.TYPE_RECOMMEND, R.layout.home_recommend_music);

        addChildClickViewIds(R.id.tv_title_right,R.id.iv_more);
        addChildClickViewIds(R.id.top_layout_item);
        addChildClickViewIds(R.id.recommend_layout_item);
    }


    private Random mRandom = new Random();

    @Override
    protected void convert(BaseViewHolder viewHolder, MultiItemEntity multiItemEntity) {
        switch (multiItemEntity.getItemType()) {
            case Constants.MultiItemType.TYPE_TITLE:
                TitleMultiBean title = (TitleMultiBean) multiItemEntity;
                viewHolder.setText(R.id.tv_title, title.getTitle());
                if (title.getType().equals(Constants.MusicType.TYPE_RECOMMEND) ||
                        title.getType().equals(Constants.MusicType.TYPE_TOP)) {
                    viewHolder.setText(R.id.tv_title_right, "播放全部");
                }else {
                    viewHolder.setText(R.id.tv_title_right, "");
                    viewHolder.getView(R.id.tv_iv).setVisibility(View.GONE);
                }
                if (title.getType().equals(Constants.MusicType.TYPE_RECOMMEND)){
                    viewHolder.getView(R.id.iv_more).setVisibility(View.GONE);
                }
                break;
            case Constants.MultiItemType.TYPE_TOP:
                TopMusicBean.DataBean.ListBean topMusic = (TopMusicBean.DataBean.ListBean) multiItemEntity;
                viewHolder.setText(R.id.home_top_name, topMusic.getMusicName());
                String singerName = topMusic.getSingerName();
                if (singerName == null || singerName.equals("")) {
                    singerName = "未知歌手";
                }
                viewHolder.setText(R.id.home_top_singer_name, singerName);
                viewHolder.setText(R.id.home_top_count, (mRandom.nextInt(50)+50) + "");
                ImageView topImage = viewHolder.getView(R.id.home_top_pic_id);
                String picId = topMusic.getPicId();
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
                break;
            case Constants.MultiItemType.TYPE_RECOMMEND:
                RecommendMusicBean.DataBean.ListBean recommend = (RecommendMusicBean.DataBean.ListBean) multiItemEntity;
                viewHolder.setText(R.id.home_recommend_name, recommend.getMusicName());
                String recommendSingerName = recommend.getSingerName();
                if (recommendSingerName == null || recommendSingerName.equals("")) {
                    recommendSingerName = "未知歌手";
                }
                viewHolder.setText(R.id.home_recommend_singer_name, recommendSingerName);
                viewHolder.setText(R.id.home_recommend_count, mRandom.nextInt(50) + "");
                ImageView recommendImage = viewHolder.getView(R.id.home_recommend_pic_id);
                String recommendImageUrl = recommend.getPicId() + "";
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
                break;
        }
    }
}
