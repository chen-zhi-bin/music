package com.czb.module_base;

public class RoutePath {
    public static final String PATH="path";

    public static class Main{
        public static final String PAGE_MAIN="/main/MainActivity";
    }

    public static class Login{
        public static final String PATH_lOGIN="/login/login";
    }

    public static class Search{
        public static final String PAGE_SEARCH = "/search/SearchActivity";
    }

    public static class Home{
        public static final String MUSICIAN="musician";

        public static final String SERVICE_HOME="/home/SERVICE_HOME";
        public static final String DETAIL_MUSICIAN_HOME="/home/musician";
        public static final String DETAIL_MUSIC_MORE="/home/music_more";
        public static final String DETAIL_MUSIC = "/home/music";
    }

    public static class Moyu{
        public static final String MUSIC_ID="music_ID";
        public static final String COMMENT = "comment";
        public static final String SERVICE_MOYU="/moyu/moyu_service";

        public static final String DETAIL_MUSIC_COMMENT = "/moyu/music_comment";
        public static final String DETAIL_MUSIC_SUB_COMMENT = "/moyu/sub_comment";
    }

    public static class Ucenter{
        public static final String PARAMS_USER_ID="userId";
        public static final String SERVICE_UCENTER="/ucenter/SERVICE_UCENTER";
        public static final String PAGE_USER_SET = "/ucenter/setting";
        public static final String PAGE_USER_HISTORY = "/ucenter/history";
        public static final String PAGE_USER_COLLECTION = "/ucenter/collection";
    }
}
