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
import android.widget.Toast;

import java.util.List;

import de.dominikwieners.androidhive.R;
import de.dominikwieners.androidhive.adapter.PostAdapter;
import de.dominikwieners.androidhive.model.PostList;
import de.dominikwieners.androidhive.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private RecyclerView postList;
    private View parentView;
    private List<PostList> postItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        postList = (RecyclerView) findViewById(R.id.postRecycler);
        parentView = findViewById(R.id.parentLayout);



        if(InternetConnection.checkInternetConnection(getApplicationContext())) {


            ApiService api = WordPressClient.getApiService();

            Call<List<PostList>> call = api.getPosts();
            call.enqueue(new Callback<List<PostList>>() {
                @Override
                public void onResponse(Call<List<PostList>> call, Response<List<PostList>> response) {
                    Log.d("RetrofitResponse", "Status Code " + response.code());
                    postItemList = response.body();
                    postList.setHasFixedSize(true);
                    postList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    postList.setAdapter(new PostAdapter(getApplicationContext(), postItemList));


                }

                @Override
                public void onFailure(Call<List<PostList>> call, Throwable t) {
                    Log.d("RetrofitResponse", "Error");
                }
            });
        }else{
            Snackbar.make(parentView, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE);
        }



    }
}
