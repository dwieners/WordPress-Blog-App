package de.dominikwieners.androidhive.app;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.dominikwieners.androidhive.R;
import de.dominikwieners.androidhive.adapter.PostAdapter;
import de.dominikwieners.androidhive.model.Post;
import de.dominikwieners.androidhive.sqlite.PostDB;
import de.dominikwieners.androidhive.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritePostsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View favPost;
    private RecyclerView favList;
    private List<Post> sqLitePostList;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_posts);

        toolbar = (Toolbar) findViewById(R.id.toolbar_fav);
        toolbar.setTitle("My favorite posts");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favPost = findViewById(R.id.favPosts);

        sqLitePostList = PostDB.getInstance(getApplicationContext()).getAllDbPosts();


        Log.d("FavoritePosts", ""+sqLitePostList.size());

        setFavListContent(true, sqLitePostList);



    }


    public void setFavListContent(final boolean withProgress, final List<Post> favPostList) {


        if (InternetConnection.checkInternetConnection(getApplicationContext())) {


            ApiService api = WordPressClient.getApiService();

            Call<List<Post>> call = api.getPosts();


            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(FavoritePostsActivity.this);
            progressDialog.setTitle(getString(R.string.progressdialog_title));
            progressDialog.setMessage(getString(R.string.progressdialog_message));

            if (withProgress) {
                progressDialog.show();
            }


            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    Log.d("RetrofitResponseFL", "Status Code " + response.code());

                    ArrayList<Post> myList = new ArrayList<Post>();

                    postList = response.body();

                    for (Post post : postList) {

                        for (Post dbPost : favPostList) {
                            if (post.getId() == dbPost.getWpPostId()) {
                                myList.add(post);
                            }
                        }
                    }


                    Log.d("FavoritePostsRespone", "" + sqLitePostList.size());

                    favList = (RecyclerView) findViewById(R.id.postRecycler);
                    favList.setHasFixedSize(true);
                    favList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    favList.setAdapter(new PostAdapter(getApplicationContext(), myList));

                    if(withProgress) {
                        progressDialog.dismiss();
                    }



                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.d("RetrofitResponseFL", "Error");
                    if(withProgress) {
                        progressDialog.dismiss();
                    }
                }
            });


        }else {
            Snackbar.make(favPost, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        sqLitePostList = PostDB.getInstance(getApplicationContext()).getAllDbPosts();
        setFavListContent(true, sqLitePostList);
    }
}
