package de.dominikwieners.androidhive.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominikwieners on 13.08.17.
 */

public class PostList {

    @SerializedName("slug")
    private String slug;

    @SerializedName("title")
    private JsonObject title;

    @SerializedName("excerpt")
    private JsonObject excerpt;



    public String getSlug() {
        return slug;
    }

    public JsonObject getTitle() {
        return title;
    }

    public JsonObject getExcerpt() {
        return excerpt;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTitle(JsonObject title) {
        this.title = title;
    }

    public void setExcerpt(JsonObject excerpt) {
        this.excerpt = excerpt;
    }
}
