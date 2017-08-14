package de.dominikwieners.androidhive.app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dominikwieners on 13.08.17.
 */

public class WordPressClient {

    /*********
     * URLs
     */
    private static final String BASE_URL = "https://www.androidhive.info/wp-json/wp/v2/";


    /**
     * Retrofit Instance
     */
    public static Retrofit getRetroInstance(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiService getApiService() {
        return getRetroInstance().create(ApiService.class);
    }
}
