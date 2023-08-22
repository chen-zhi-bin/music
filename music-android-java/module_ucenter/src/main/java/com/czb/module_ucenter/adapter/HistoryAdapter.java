package com.czb.module_ucenter.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.bean.db.Music;
import com.czb.module_ucenter.R;

public class HistoryAdapter extends BaseQuickAdapter<Music, BaseViewHolder> {



    public HistoryAdapter() {
        super(R.layout.ucenter_adapter_history);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Music music) {
        ImageView musicCoverIv = baseViewHolder.findView(R.id.item_pic_id);
        String songCover = music.getSongCover();
        Glide.with(musicCoverIv.getContext())
                .load(songCover)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .placeholder(com.czb.module_base.R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                .into(musicCoverIv);
        baseViewHolder.setText(R.id.item_name,music.getSongName());
        baseViewHolder.setText(R.id.item_singer_name,music.getArtist());
    }
}
