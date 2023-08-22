package com.czb.module_base.bean.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.czb.module_base.bean.db.Music;

import java.util.List;

@Dao
public interface MusicDao {

    @Query("select * from music where id = :id")
    Music getMusicById(String id);

    @Insert
    void insertMusic(Music music);

    @Insert
    void insertUsers(List<Music> musics);

    //更新数据
    @Update
    void updateUser(Music music);
    //删除数据
    @Delete
    void deleteUser(Music music);

    @Query("SELECT * FROM music limit :off,:size")
    List<Music> getMusicAll(int off,int size);



}
