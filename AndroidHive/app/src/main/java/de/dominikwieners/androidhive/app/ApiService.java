package de.dominikwieners.androidhive.app;

import java.util.List;

import de.dominikwieners.androidhive.model.Media;
import de.dominikwieners.androidhive.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dominikwieners on 13.08.17.
 */

public interface ApiService {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int postId);

    @GET("media/{featured_media}")
    Call<Media> getPostThumbnail(@Path("featured_media") int media);


}
