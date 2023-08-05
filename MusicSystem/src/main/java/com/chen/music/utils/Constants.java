package com.chen.music.utils;

public interface Constants {

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
        String ROLE_ADMIN_ID = "2";
        String ROLE_ADMIN_SUPER_ID = "1";
    }

    interface Settings {
        String HAS_MANAGER_ACCOUNT_INT_STATE = "has_manager_account_int_state"; //初始化管理员
        String WEB_SIZE_TITLE="web_size_title";
        String WEB_SIZE_DESCRIPTION="web_size_description";
        String WEB_SIZE_KEYWORDS="web_size_keywords";
        String WEB_SIZE_VIEW_COUNT="web_size_view_count";
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
