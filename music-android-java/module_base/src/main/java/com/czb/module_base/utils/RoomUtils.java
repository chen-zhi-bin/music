package com.czb.module_base.utils;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.room.Room;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.bean.db.MusicDataBase;
import com.czb.module_base.bean.db.dao.MusicDao;

public class RoomUtils {
    private static RoomUtils instance;
    private final MusicDao mMusicDao;

    private RoomUtils(Context context) {
        MusicDataBase db = Room.databaseBuilder(context.getApplicationContext(), MusicDataBase.class, "MUSIC_DATA").build();
        mMusicDao = db.musicDao();
    }

    public MusicDao getMusicDao() {
        return mMusicDao;
    }

    public static RoomUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (instance == null) {
                    instance = new RoomUtils(context);
                }
            }
        }
        return instance;
    }

}
