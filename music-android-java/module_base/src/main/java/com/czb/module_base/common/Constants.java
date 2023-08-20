package com.czb.module_base.common;

public class Constants {
    public static final int RETURN_TO_HMOE = 1;
    public static final int RETURN_TO_USER = 3;
    public static final int NEED_RESULT = 0;

    public static final int NAVIGATION_VIEW_MENU_RECOMMEND_ITEM_ID_INDEX = 0;
    public static final int NAVIGATION_VIEW_MENU_USER_ITEM_ID_INDEX = 2;
    public static final String BASE_URL = "http://10.0.2.2:8090";
    public static final int SUCCESS = 20000;
    public static final String BASE_URL_IMAGE = BASE_URL + "/image/";
    public static final String BASE_URL_MUSIC = BASE_URL + "/music/";

    public class MultiItemType {
        //标题
        public static final int TYPE_TITLE = 1;
        public static final int TYPE_TOP = 2;
        public static final int TYPE_RECOMMEND = 3;
        public static final int TYPE_SINGER = 4;
    }

    public class MusicType {
        public static final String TYPE = "TYPE";
        public static final String TYPE_TOP = "TOP";
        public static final String TYPE_RECOMMEND = "RECOMMEND";
    }

    public class MusicState {
        public static final String PLAY = "PLAY";
        public static final String PAUSE = "PAUSE";
    }

    public class MusicianSex {
        public static final int WOMEN = 0;
        public static final int MAN = 1;
        public static final int COMBINATION = 2;
        public static final int UNKNOWN = 3;
    }

    public class SearchType {
        public static final String SEARCH_TYPE="search_type";
        public static final String TYPE_MUSIC_NAME="music_name";
        public static final String TYPE_MUSIC_LYRICS="music_lyrics";
        public static final int SEARCH_BY_NAME = 1;
        public static final int SEARCH_BY_LYRIC = 2;
    }
}
