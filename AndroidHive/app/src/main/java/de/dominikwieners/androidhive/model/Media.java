package de.dominikwieners.androidhive.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dominikwieners on 14.08.17.
 */

public class Media {

    @SerializedName("guid")
    private JsonObject guid;

    public JsonObject getGuid() {
        return guid;
    }

    public void setGuid(JsonObject guid) {
        this.guid = guid;
    }
}
