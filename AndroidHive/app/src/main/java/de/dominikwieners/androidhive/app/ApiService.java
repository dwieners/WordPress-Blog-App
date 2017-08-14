package de.dominikwieners.androidhive.app;

import de.dominikwieners.androidhive.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dominikwieners on 13.08.17.
 */

public interface PostsInterface {

    @GET("posts")
    Call<PostList> getPosts();


}
