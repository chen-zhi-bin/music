package com.czb.module_home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_home.R;
import com.czb.module_home.model.bean.MusicianBean;

import org.jetbrains.annotations.NotNull;

public class SingerAdapter extends BaseQuickAdapter<MusicianBean.DataBean.ListBean, BaseViewHolder> {
    public SingerAdapter() {
        super(R.layout.home_adapter_sing_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, MusicianBean.DataBean.ListBean data) {
        String name = data.getName();
        if (name == null||name.equals("")||name.equals("null")) {
            name="未知歌手";
        }
        viewHolder.setText(R.id.item_singer_name,name.toString().trim());
        ImageView iv = viewHolder.getView(R.id.home_singer_iv);
        Glide.with(iv.getContext())
                .load(Constants.BASE_URL_IMAGE+data.getPicId())
                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                .circleCrop()       //圆角
                .into(iv);
    }
}
