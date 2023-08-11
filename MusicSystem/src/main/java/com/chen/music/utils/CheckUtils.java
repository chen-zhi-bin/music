package com.chen.music.utils;

public class CheckUtils {
     public static int checkPage(int page){
        if (page < Constants.Page.DEFAULT_PAGE){
            page=Constants.Page.DEFAULT_PAGE;
        }
        return page;
    }

    public static int checkSize(int size){
        if (size< Constants.Page.DEFAULT_SIZE){
            size=Constants.Page.DEFAULT_SIZE;
        }
        return size;
    }
}
