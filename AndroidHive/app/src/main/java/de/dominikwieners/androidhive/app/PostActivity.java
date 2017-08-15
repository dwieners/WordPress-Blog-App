package de.dominikwieners.androidhive.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.List;

import de.dominikwieners.androidhive.R;
import de.dominikwieners.androidhive.model.Media;
import de.dominikwieners.androidhive.util.InternetConnection;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    Toolbar postToolbar;
    TextView postTitle;
    //TextView postContent;
    ImageView postBackdrop;

    View parentView;

    public static Intent createIntent(Context context, int id, int featuredMedia, String title, String content){
        Intent intent = new Intent(context, PostActivity.class);
        //Setzen des wertes aus dem Intent
        intent.putExtra("de.dominikwieners.androidhive.postId", id);
        intent.putExtra("de.dominikwieners.androidhive.featuredMedia",featuredMedia);
        intent.putExtra("de.dominikwieners.androidhive.postTitle", title);
        intent.putExtra("de.dominikwieners.androidhive.postContent",content);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Get Intent
        int id = (int) getIntent().getSerializableExtra("de.dominikwieners.androidhive.postId");
        int featuredMedia = (int) getIntent().getSerializableExtra("de.dominikwieners.androidhive.featuredMedia");
        String title =  getIntent().getSerializableExtra("de.dominikwieners.androidhive.postTitle").toString();
        String content = getIntent().getSerializableExtra("de.dominikwieners.androidhive.postContent").toString();


        //Set Toolbar
        postToolbar = (Toolbar) findViewById(R.id.postToolbar);
        setSupportActionBar(postToolbar);
        initCollapsingToolbar(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set TitleText and ContentText
        postTitle = (TextView) findViewById(R.id.post_title);
        postTitle.setText(title);

        /*
        postContent = (TextView) findViewById(R.id.post_content);
        postContent.setText(content);
        */



        postBackdrop = (ImageView) findViewById(R.id.post_backdrop) ;

       //Call Media
        if(InternetConnection.checkInternetConnection(getApplicationContext())) {
            ApiService api = WordPressClient.getApiService();

            Call<Media> call = api.getPostThumbnail(featuredMedia);


            call.enqueue(new Callback<Media>() {
                @Override
                public void onResponse(Call<Media> call, Response<Media> response) {

                    Log.d("ResponseMediaCode", "Status = " + response.code());
                    Media media = response.body();
                    String mediaUrl = media.getGuid().get("rendered").toString().replaceAll("\"", "");

                    Glide.with(getApplicationContext()).load(mediaUrl)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(postBackdrop);
                }

                @Override
                public void onFailure(Call<Media> call, Throwable t) {

                }
            });
        }else{
            Snackbar.make(parentView, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE);
        }



    }

    private void initCollapsingToolbar(final String title){
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.post_collapsing_toolbarLayout);
        collapsingToolbar.setTitle(" ");

    }

}
