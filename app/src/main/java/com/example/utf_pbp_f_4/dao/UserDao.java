package com.example.utf_pbp_f_4.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.utf_pbp_f_4.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE username=:username AND password=:password")
    User attemptLogin(String username, String password);

    @Insert
    void registerUser(User user);

}
