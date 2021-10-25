package com.example.utf_pbp_f_4.model;

import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;

@Entity(tableName = "books")
public class Books {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "ISBN")
    private String ISBN;

    @ColumnInfo(name = "nama_buku")
    private String nama_buku;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "imgURL")
    private String imgURL;

    @ColumnInfo(name = "user_id")
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getNama_buku() {
        return nama_buku;
    }

    public void setNama_buku(String nama_buku) {
        this.nama_buku = nama_buku;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImgURL() { return imgURL; }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public static void loadImage(ImageView imageView, String imgURL) {
        Glide.with(imageView)
                .load(imgURL)
                .into(imageView);
    }
}
