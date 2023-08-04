package com.chan.music.utils;

public class CheckUtils {
    int checkPage(int page){
        if (page < Constants.Page.DEFAULT_PAGE){
            page=Constants.Page.DEFAULT_PAGE;
        }
        return page;
    }

    int checkSize(int size){
        if (size< Constants.Page.DEFAULT_SIZE){
            size=Constants.Page.DEFAULT_SIZE;
        }
        return size;
    }
}
