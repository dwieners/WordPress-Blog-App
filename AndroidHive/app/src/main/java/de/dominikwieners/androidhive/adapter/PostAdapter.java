package de.dominikwieners.androidhive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import de.dominikwieners.androidhive.model.PostList;

import de.dominikwieners.androidhive.R;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

/**
 * Created by dominikwieners on 13.08.17.
 */

public class PostAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<PostList> posts;

    //Constructor
    public PostAdapter(Context context, List<PostList> posts){
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
        PostList post = posts.get(position);
        PostViewHolder postHolder = (PostViewHolder) holder;

        String title = post.getTitle().get("rendered").toString().replaceAll("\"", "");


        String excerpt = post.getExcerpt().get("rendered").toString().replaceAll("\"", "");

        postHolder.cardPt.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY));
        postHolder.cardEx.setText(Html.fromHtml(excerpt,Html.FROM_HTML_MODE_LEGACY));




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
