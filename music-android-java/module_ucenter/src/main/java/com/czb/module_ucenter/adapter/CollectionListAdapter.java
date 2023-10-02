package com.czb.module_ucenter.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czb.module_base.common.Constants;
import com.czb.module_ucenter.R;
import com.czb.module_ucenter.model.bean.CollectionListBean;

public class CollectionListAdapter extends BaseQuickAdapter<CollectionListBean.DataBean.ListBean, BaseViewHolder> {
    public CollectionListAdapter() {
        super(R.layout.ucenter_adapter_collection_list);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CollectionListBean.DataBean.ListBean music) {
        ImageView musicCoverIv = baseViewHolder.findView(R.id.item_pic_id);
        String songCover = music.getPicId();
        if (songCover != null&&!songCover.equals("null")) {
            Glide.with(musicCoverIv.getContext())
                    .load(Constants.BASE_URL_IMAGE+songCover)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .placeholder(com.czb.module_base.R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                    .into(musicCoverIv);
        }else {
            Glide.with(musicCoverIv.getContext())
                    .load(com.czb.module_base.R.mipmap.iv_default_music)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(musicCoverIv);
        }
        baseViewHolder.setText(R.id.item_name,music.getName());
        baseViewHolder.setText(R.id.item_singer_name,music.getSingerName());
    }
}
