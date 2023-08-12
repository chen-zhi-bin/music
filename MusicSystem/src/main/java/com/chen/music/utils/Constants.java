package com.chen.music.utils;

public interface Constants {

    interface MUSICIAN{
        String STATE_DELETE="0";
        String STATE_PUBLISH="1";
        String STATE_TOP="3";
    }

    interface Music{
        //1表示正常，0表示删除，3表示置顶
        String STATE_DELETE="0";
        String STATE_PUBLISH="1";
        String STATE_TOP="3";
        Long PREDICT_SUB_COMMENT_SIZE=3L;

    }

    interface MusicianSex{
        String TYPE_WOMAN="0";
        String TYPE_MAN="1";
        String TYPE_COMBINATION="2";
        String TYPE_NONE="3";
    }

    interface AdminPermission{
        String ROLE_SUPER_ADMIN ="1";
    }

    interface User {
        String DEFAULT_AVATAR = "default_avatar.png";
        String DEFAULT_STATE = "1";
        String COOKIE_TOKEN_KEY = "cookie_token";
        String ROLE_NORMAL = "10";
        //redis的key
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip_";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_TOKEN = "key_token_";
        String ROLE_ADMIN_USER_ID = "2";
        String ROLE_ADMIN_IMAGE_ID = "3";
        String ROLE_ADMIN_MUSIC_ID = "4";
        String ROLE_ADMIN_SUPER_ID = "1";
    }

    interface ImageType{
        String PREFIX="image/";
        String TYPE_JPEG = "jpeg";
        String TYPE_JPG = "jpg";
        String TYPE_PNG = "png";
        String TYPE_GIF = "gif";
        String TYPE_JPEG_WITH_PREFIX =PREFIX+"jpeg";
        String TYPE_JPG_WITH_PREFIX =PREFIX+"jpg";
        String TYPE_PNG_WITH_PREFIX=PREFIX+"png";
        String TYPE_GIF_WITH_PREFIX=PREFIX+"gif";
    }
    interface MusicType {
        String PREFIX_AUDIO="audio/";
        String TYPE_MP3="mp3";
        String TYPE_FLAC="flac";
        String TYPE_MP3_AND_FLAC=TYPE_MP3+"/"+TYPE_FLAC;
        String TYPE_MP3_WITH_PREFIX_AUDIO=PREFIX_AUDIO+TYPE_MP3;
        String TYPE_FLAC_WITH_PREFIX_AUDIO=PREFIX_AUDIO+TYPE_FLAC;

    }

    interface Settings {
        String HAS_MANAGER_ACCOUNT_INT_STATE = "has_manager_account_int_state"; //初始化管理员
        String WEB_SIZE_TITLE="web_size_title";
        String WEB_SIZE_DESCRIPTION="web_size_description";
        String WEB_SIZE_KEYWORDS="web_size_keywords";
        String WEB_SIZE_VIEW_COUNT="web_size_view_count";
        String WEB_SIZE_TODAY_VIEW_COUNT="web_size_today_view_count";
        String WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT="web_size_one_day_before_view_count";
        String WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT="web_size_two_day_before_view_count";
        String WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT="web_size_three_day_before_view_count";
        String WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT="web_size_four_day_before_view_count";
        String WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT="web_size_five_day_before_view_count";
        String WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT="web_size_six_day_before_view_count";
        String WEB_SIZE_SEVEN_DAY_BEFORE_VIEW_COUNT="web_size_seven_day_before_view_count";
    }

    interface Page{
        int DEFAULT_PAGE=1;
        int DEFAULT_SIZE=10;
    }

    interface TimeValueInMillions {
        long MIN = 60*1000;
        long HOUR = 60 * MIN;
        long HOUR_2 = 2 * 60 * MIN;
        long DAY = 24 * HOUR;
        long WEEK = 7 * DAY;
        long MONTH = 30 * DAY;
    }

    /**
     * 单位是秒
     */
    interface TimeValueInSecond {
        int MIN = 60;
        int HOUR = 60 * MIN;
        int HOUR_2 = 2 * 60 * MIN;
        int DAY = 24 * HOUR;
        int WEEK = 7 * DAY;
        int MONTH = 30 * DAY;
    }


}
