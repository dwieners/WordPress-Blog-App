package de.dominikwieners.androidhive.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.dominikwieners.androidhive.R;
import de.dominikwieners.androidhive.adapter.FavoritePostAdapter;
import de.dominikwieners.androidhive.model.Post;
import de.dominikwieners.androidhive.sqlite.PostDB;

public class FavoritePostsActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView favList;

    private List<Post> favPostList;

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

        favPostList = PostDB.getInstance(getApplicationContext()).getAllDbPosts();

        Log.d("FavoritePosts", ""+favPostList.size());

        favList = (RecyclerView) findViewById(R.id.postRecycler_fav);
        favList.setHasFixedSize(true);
        favList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        favList.setAdapter(new FavoritePostAdapter(getApplicationContext(), favPostList));



    }
}
