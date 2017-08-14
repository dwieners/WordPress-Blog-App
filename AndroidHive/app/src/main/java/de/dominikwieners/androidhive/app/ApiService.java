package de.dominikwieners.androidhive.app;

import java.util.List;

import de.dominikwieners.androidhive.model.PostList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dominikwieners on 13.08.17.
 */

public interface ApiService {

    @GET("posts")
    Call<List<PostList>> getPosts();


}
