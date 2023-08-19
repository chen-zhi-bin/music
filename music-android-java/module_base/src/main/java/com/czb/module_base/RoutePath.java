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
    }

    public static class Moyu{
        public static final String SERVICE_MOYU="/moyu/moyu_service";

    }

    public static class Ucenter{
        public static final String PARAMS_USER_ID="userId";
        public static final String SERVICE_UCENTER="/ucenter/SERVICE_UCENTER";
    }
}
