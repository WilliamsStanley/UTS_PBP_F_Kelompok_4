package com.example.utf_pbp_f_4.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.utf_pbp_f_4.model.Books;

import java.util.List;

@Dao
public interface BooksDao {
    @Query("SELECT * FROM Books")
    List<Books> getAll();

    @Insert
    void insertBooks(Books books);

    @Delete
    void deleteBooks(Books books);

    @Query("SELECT * FROM Books where user_id = :user_id")
    List<Books> getBooksByUserId(int user_id);
}
