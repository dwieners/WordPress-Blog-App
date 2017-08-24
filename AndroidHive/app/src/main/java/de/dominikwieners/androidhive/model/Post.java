package de.dominikwieners.androidhive.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominikwieners on 13.08.17.
 */

public class Post {


    @SerializedName("id")
    private int id;

    @SerializedName("featured_media")
    private int featured_media;

    @SerializedName("title")
    private JsonObject title;

    @SerializedName("excerpt")
    private JsonObject excerpt;

    @SerializedName("content")
    private JsonObject content;


    //Variable for SQLite
    private int sqLiteId;
    private int wpPostId;
    private String wpTitle;
    private String wpExcerpt;
    private String wpContent;


    private boolean isFavorite;

    public Post(){

    }

    public Post(int sqLiteId, int wpPostId, String wpTitle, String wpExcerpt, int isFavorite) {

        this.sqLiteId = sqLiteId;
        this.wpPostId = wpPostId;
        this.wpTitle = wpTitle;
        this.wpExcerpt = wpExcerpt;
        this.wpContent = wpContent;

        this.isFavorite = false;
        if(isFavorite == 1){
            this.isFavorite = true;
        }
    }


    // Getter
    public int getId() {
        return id;
    }

    public int getFeatured_media() {
        return featured_media;
    }

    public JsonObject getTitle() {
        return title;
    }

    public JsonObject getExcerpt() {
        return excerpt;
    }

    public JsonObject getContent() {
        return content;
    }


    //Gette SQLite

    public int getSqLiteId() {
        return sqLiteId;
    }

    public int getWpPostId() {
        return wpPostId;
    }

    public String getWpTitle() {
        return wpTitle;
    }

    public String getWpExcerpt() {
        return wpExcerpt;
    }

    public String getWpContent() {
        return wpContent;
    }



    public boolean isFavorite() {
        return isFavorite;
    }




    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setFeatured_media(int featured_media) {
        this.featured_media = featured_media;
    }

    public void setTitle(JsonObject title) {
        this.title = title;
    }

    public void setExcerpt(JsonObject excerpt) {
        this.excerpt = excerpt;
    }

    public void setContent(JsonObject content) {
        this.content = content;
    }


    //SetterSQLite
    public void setSqLiteId(int sqLiteId) {
        this.sqLiteId = sqLiteId;
    }

    public void setWpPostId(int wpPostId) {
        this.wpPostId = wpPostId;
    }

    public void setWpTitle(String wpTitle) {
        this.wpTitle = wpTitle;
    }

    public void setWpExcerpt(String wpExcerpt) {
        this.wpExcerpt = wpExcerpt;
    }

    public void setWpContent(String wpContent) {
        this.wpContent = wpContent;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
