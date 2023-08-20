package com.czb.module_search.adapter;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_search.R;
import com.czb.module_search.model.bean.MusicInfoByLyricsBean;
import com.czb.module_search.model.bean.MusicInfoByNameBean;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {



    {
        addItemType(Constants.SearchType.SEARCH_BY_NAME, R.layout.search_music_by_name);
        addItemType(Constants.SearchType.SEARCH_BY_LYRIC, R.layout.search_music_by_lyric);
        addChildClickViewIds(R.id.search_layout_item);
        addChildClickViewIds(R.id.search_lyric_layout_item);
    }

    private Random mRandom = new Random();
    private String regEx="\\[.*\\]";
    @Override
    protected void convert(BaseViewHolder viewHolder, MultiItemEntity multiItemEntity) {
        switch (multiItemEntity.getItemType()) {
            case Constants.SearchType.SEARCH_BY_NAME:
                MusicInfoByNameBean.DataBean.ListBean searchByName = (MusicInfoByNameBean.DataBean.ListBean) multiItemEntity;
                viewHolder.setText(R.id.search_name, Html.fromHtml(searchByName.getName()));
                String singerName = searchByName.getSingerName();
                if (singerName == null || singerName.equals("")) {
                    singerName = "未知歌手";
                }
                viewHolder.setText(R.id.search_singer_name, singerName);
                viewHolder.setText(R.id.search_count, mRandom.nextInt(50) + "");
                ImageView recommendImage = viewHolder.getView(R.id.search_pic_id);
                String recommendImageUrl = searchByName.getPicId() + "";
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
            case Constants.SearchType.SEARCH_BY_LYRIC:
                MusicInfoByLyricsBean.DataBean.ListBean searchByLyric = (MusicInfoByLyricsBean.DataBean.ListBean) multiItemEntity;
                String imageUrl = searchByLyric.getPicId() + "";
                ImageView picIv = viewHolder.getView(R.id.item_pic_id);
                if (imageUrl == null || imageUrl.equals("") || imageUrl.equals("null")) {
                    Glide.with(picIv.getContext())
                            .load(R.mipmap.iv_default_music)
                            .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                            .circleCrop()       //圆角
                            .into(picIv);
                } else {
                    Glide.with(picIv.getContext())
                            .load(Constants.BASE_URL_IMAGE + imageUrl)
                            .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                            .circleCrop()       //圆角
                            .into(picIv);
                }
                viewHolder.setText(R.id.item_name,searchByLyric.getName());
                String musicianName = searchByLyric.getSingerName();
                if (musicianName == null || musicianName.equals("")) {
                    musicianName = "未知歌手";
                }
                viewHolder.setText(R.id.item_singer_name,musicianName);
                TextView lyricTv = viewHolder.getView(R.id.item_lyric);
                String lyric = searchByLyric.getLyric();

                Pattern compile = Pattern.compile(regEx);
                Matcher matcher = compile.matcher(lyric);
                lyric = matcher.replaceAll("");
                String[] split = lyric.split("\\n");
                lyric = "";
                boolean isStart = false;
                for (String s : split) {
                    if (s.indexOf("<span")!=-1) {
                        isStart = true;
                    }
                    if (isStart) {
                        lyric=lyric + s + " ";
                    }
                }
//                int index = lyric.indexOf("<span");
//                lyric = lyric.substring(index,lyric.length());
                lyricTv.setText(Html.fromHtml(lyric));
                break;
        }
    }
}
