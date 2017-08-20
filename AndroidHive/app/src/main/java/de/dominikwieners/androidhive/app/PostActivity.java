package de.dominikwieners.androidhive.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import de.dominikwieners.androidhive.R;
import de.dominikwieners.androidhive.model.Media;
import de.dominikwieners.androidhive.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    Toolbar postToolbar;
    TextView postTitle;
    WebView postContent;
    ImageView postBackdrop;

    View parentView;

    boolean isItemSelected = false;

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
        String content = getIntent().getSerializableExtra("de.dominikwieners.androidhive.postContent").toString().replaceAll("\\\\n", "").replaceAll("\\\\r", "").replaceAll("\\\\", "");;

        initToolbar(title);

        initPost(title, content);

        initWebView(content);



        //Call Media
        if(InternetConnection.checkInternetConnection(getApplicationContext())) {
            ApiService api = WordPressClient.getApiService();

            Call<Media> call = api.getPostThumbnail(featuredMedia);


            call.enqueue(new Callback<Media>() {
                @Override
                public void onResponse(Call<Media> call, Response<Media> response) {

                    Log.d("ResponseMediaCode", "Status = " + response.code());

                    if (response.code() != 404) {
                        Media media = response.body();
                        String mediaUrl = media.getGuid().get("rendered").toString().replaceAll("\"", "");

                        Glide.with(getApplicationContext()).load(mediaUrl)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(postBackdrop);
                    }else{

                    }

                }

                @Override
                public void onFailure(Call<Media> call, Throwable t) {

                }
            });

        }else{
            Snackbar.make(parentView, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE);
        }

    }

    //Init Toolbar but do not set media
    private void initPost(String title, String content ){




        postBackdrop = (ImageView) findViewById(R.id.post_backdrop);

        postTitle = (TextView) findViewById(R.id.post_title);
        postTitle.setText(title);

        postContent = (WebView) findViewById(R.id.webview);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Toggle Navigation icon

        if(!isItemSelected){
            item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_24dp,getTheme()));
            isItemSelected = true;
        }else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp,getTheme()));
            isItemSelected = false;
        }

        return super.onOptionsItemSelected(item);

    }



    private class MyWebView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }


    //Init Toolbar
    private void initToolbar(String title){

        //Set StatusBarColor transparent
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //Set Toolbar
        postToolbar = (Toolbar) findViewById(R.id.postToolbar);
        setSupportActionBar(postToolbar);
        initCollapsingToolbar(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Init CollapsingToolbarLayout
    private void initCollapsingToolbar(final String title){
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.post_collapsing_toolbarLayout);
        collapsingToolbar.setTitle(" ");

    }




    private void initWebView(String content){

        //Init ProgressDialog
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(PostActivity.this);
        progressDialog.setTitle(getString(R.string.progressdialog_title));
        progressDialog.setMessage(getString(R.string.progressdialog_message));

        //Set Html content
        content = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" +
                "<script src=\"prism.js\"></script>" +
                "<div class=\"content\">" + content+ "</div>";


        Log.d("WebViewContent", content);

        postContent.getSettings().setLoadsImagesAutomatically(true);
        postContent.getSettings().setJavaScriptEnabled(true);
        postContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();

            }
        });

        postContent.loadDataWithBaseURL("file:///android_asset/*",content, "text/html; charset=utf-8", "UTF-8", null);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_favorite_menu, menu);
        return true;
    }






}
