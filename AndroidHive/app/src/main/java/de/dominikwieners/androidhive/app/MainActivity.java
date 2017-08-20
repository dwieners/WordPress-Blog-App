package de.dominikwieners.androidhive.app;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.dominikwieners.androidhive.R;
import de.dominikwieners.androidhive.adapter.PostAdapter;
import de.dominikwieners.androidhive.model.Media;
import de.dominikwieners.androidhive.model.Post;
import de.dominikwieners.androidhive.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private RecyclerView postList;
    private View parentView;

    private List<Post> postItemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        postList = (RecyclerView) findViewById(R.id.postRecycler);
        parentView = findViewById(R.id.parentLayout);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        setListContent();




    }






    public void setListContent(){

        if(InternetConnection.checkInternetConnection(getApplicationContext())) {


            ApiService api = WordPressClient.getApiService();

            Call<List<Post>> call = api.getPosts();

            // Set up progress before call
            final ProgressDialog progressDoalog;
            progressDoalog = new ProgressDialog(MainActivity.this);
            progressDoalog.setTitle(getString(R.string.progressdialog_title));
            progressDoalog.setMessage(getString(R.string.progressdialog_message));

            progressDoalog.show();

            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    Log.d("RetrofitResponse", "Status Code " + response.code());
                    postItemList = response.body();
                    postList.setHasFixedSize(true);
                    postList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    postList.setAdapter(new PostAdapter(getApplicationContext(), postItemList));
                    progressDoalog.dismiss();



                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.d("RetrofitResponse", "Error");
                    progressDoalog.dismiss();
                }
            });






        }else{
            Snackbar.make(parentView, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.my_favorites:
                break;
        }

        return true;
    }
}
