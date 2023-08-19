package com.czb.module_base.bean;

import androidx.annotation.Nullable;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.czb.module_base.common.Constants;

import java.util.Objects;

public class TitleMultiBean implements MultiItemEntity {

    private String title;

    private String type;

    public TitleMultiBean(String s,String type) {
        this.title = s;
        this.type = type;
    }

    @Override
    public int getItemType() {
        return Constants.MultiItemType.TYPE_TITLE;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
//        if (obj instanceof TitleMultiBean) {
//            String data = ((TitleMultiBean) obj).getTitle();
//            return title.equals(data);
//        } else {
//            return
//        }

        return title.equals(((TitleMultiBean)obj).getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
