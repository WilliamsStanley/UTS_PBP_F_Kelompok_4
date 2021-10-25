package com.example.utf_pbp_f_4.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.utf_pbp_f_4.dao.BooksDao;
import com.example.utf_pbp_f_4.dao.UserDao;
import com.example.utf_pbp_f_4.model.Books;
import com.example.utf_pbp_f_4.model.User;

@Database(entities = {Books.class, User.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BooksDao todoDao();
    public abstract UserDao userDao();

}
