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
}
