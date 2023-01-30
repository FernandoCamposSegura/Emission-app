package com.svalero.emission.db;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.emission.domain.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE username = :username")
    User getByUserName(String username);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Query("UPDATE user SET pathImg = :pathImg WHERE username = :username")
    void updateImgUser(String username, String pathImg);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}
