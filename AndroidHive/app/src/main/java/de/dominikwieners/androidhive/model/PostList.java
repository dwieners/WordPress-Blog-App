package de.dominikwieners.androidhive.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dominikwieners on 13.08.17.
 */

public class Post {

    @SerializedName("slug")
    private String slug;



    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
