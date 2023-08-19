package com.czb.module_base.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.R;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.view.MusicBottomSheetDialog;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;

public class SongInfoAdapter extends BaseQuickAdapter<SongInfo, BaseViewHolder> {
    public SongInfoAdapter() {
        super(R.layout.base_song_list_adapter);
    }

    {
        addChildClickViewIds(R.id.dialog_layout_item);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder viewHolder, SongInfo songInfo) {
        SongInfo nowPlayingSongInfo = StarrySky.with().getNowPlayingSongInfo();
        ConstraintLayout constraintLayout = viewHolder.getView(R.id.dialog_layout_item);
        if (nowPlayingSongInfo.equals(songInfo)){
            constraintLayout.setBackground(
                    ContextCompat.getDrawable(BaseApplication.getAppContext(),R.drawable.shape_linearlayout_blue)
            );
        }else {
            constraintLayout.setBackground(
                    ContextCompat.getDrawable(BaseApplication.getAppContext(),R.drawable.shape_linearlayout_white)
            );
        }
        ImageView itemPic = viewHolder.getView(R.id.item_pic_id);
        String songCover = songInfo.getSongCover();
        if (songCover==null||songCover.equals("")||songCover.equals("null")) {
            Glide.with(itemPic.getContext())
                    .load(R.mipmap.iv_default_music)
                    .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                    .circleCrop()       //圆角
                    .into(itemPic);
        }else {
            Glide.with(itemPic.getContext())
                    .load(songCover)
                    .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                    .circleCrop()       //圆角
                    .into(itemPic);
        }
        viewHolder.setText(R.id.item_name, songInfo.getSongName());
        String artist = songInfo.getArtist();
        if (artist.equals("")||artist.equals("null")) {
            artist = "未知歌手";
        }
        viewHolder.setText(R.id.item_singer_name, artist);
    }
}
