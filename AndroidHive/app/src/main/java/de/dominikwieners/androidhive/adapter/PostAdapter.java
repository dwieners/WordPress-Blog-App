package de.dominikwieners.androidhive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;


import java.util.List;

import de.dominikwieners.androidhive.app.ApiService;
import de.dominikwieners.androidhive.app.WordPressClient;
import de.dominikwieners.androidhive.model.Media;
import de.dominikwieners.androidhive.model.Post;

import de.dominikwieners.androidhive.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dominikwieners on 13.08.17.
 */

public class PostAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Post> posts;

    //Constructor
    public PostAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }



    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_entry, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = posts.get(position);
        final PostViewHolder postHolder = (PostViewHolder) holder;

        /**
         * Set title and excerpt
          */
        String title = post.getTitle().get("rendered").toString().replaceAll("\"", "");
        String excerpt = post.getExcerpt().get("rendered").toString().replaceAll("\"", "");



        postHolder.cardPt.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY));
        postHolder.cardEx.setText(Html.fromHtml(excerpt,Html.FROM_HTML_MODE_LEGACY));

        Log.d("RetrofitPostId", ""+ post.getId());

        ApiService api = WordPressClient.getApiService();

        Call<Media> call = api.getPostThumbnail(post.getFeatured_media());
        call.enqueue(new Callback<Media>() {
            @Override
            public void onResponse(Call<Media> call, Response<Media> response) {
                Log.d("RetrofitThumbResp", "Status Code " + response.code());
                Media thumbnailObject = response.body();
                String thumbnailUrl = thumbnailObject.getGuid().get("rendered").toString().replaceAll("\"", "");

                Glide.with(context).load(thumbnailUrl)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(postHolder.cardImage);
            }

            @Override
            public void onFailure(Call<Media> call, Throwable t) {

            }
        });



    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder{

     //   private ImageView cardImage;
        private TextView cardPt;
        private TextView cardEx;
        private ImageView cardImage;
        public PostViewHolder(View itemView) {
            super(itemView);

            cardImage = (ImageView) itemView.findViewById(R.id.cardImage);
            cardPt = (TextView) itemView.findViewById(R.id.cardPt);
            cardEx = (TextView) itemView.findViewById(R.id.cardEx);
        }
    }



}
