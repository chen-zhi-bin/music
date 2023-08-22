package com.czb.module_base.bean.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.czb.module_base.bean.db.dao.MusicDao;

@Database(entities = {Music.class},version = 1,exportSchema = false)
public abstract class MusicDataBase extends RoomDatabase {
    public abstract MusicDao musicDao();
}
