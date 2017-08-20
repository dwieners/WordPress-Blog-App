package de.dominikwieners.androidhive.adapter;

import android.content.Context;
import android.content.Intent;
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


import java.util.ArrayList;
import java.util.List;

import de.dominikwieners.androidhive.app.ApiService;
import de.dominikwieners.androidhive.app.PostActivity;
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

public class PostAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<Post> posts;
    private Post currentPost;


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

        postHolder.setCurrentPost(post);


    }




    @Override
    public int getItemCount() {
        return posts.size();
    }






    private static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

     //   private ImageView cardImage;
        private TextView cardPt;
        private TextView cardEx;

        private Post currentPost;

        public PostViewHolder(View itemView) {
            super(itemView);

            cardPt = (TextView) itemView.findViewById(R.id.cardPt);
            cardEx = (TextView) itemView.findViewById(R.id.cardEx);

            itemView.setOnClickListener(this);
        }

        public void setCurrentPost(Post post) {
            currentPost = post;
            /**
             * Set title and excerpt
             */
            String title = post.getTitle().get("rendered").toString().replaceAll("\"", "");
            String excerpt = post.getExcerpt().get("rendered").toString().replaceAll("\"", "");

            cardPt.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY));
            cardEx.setText(Html.fromHtml(excerpt,Html.FROM_HTML_MODE_LEGACY));


        }


        @Override
        public void onClick(View v) {


            String title = currentPost.getTitle().get("rendered").toString().replaceAll("\"", "");
            String content = currentPost.getContent().get("rendered").toString().replaceAll("\"", "");

            content = contentFilter(content, "<ins", "</ins>");

            Intent intent = PostActivity.createIntent(v.getContext(), currentPost.getId(), currentPost.getFeatured_media(), Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString() , content);
            v.getContext().startActivity(intent);
        }


        public String contentFilter(String content, String first, String last){

            String contentOutput;
            String contentResult;


            //set index
            int firstIndex = content.indexOf(first);
            int lastIndex = content.lastIndexOf(last);

            if(firstIndex != -1 || lastIndex != -1) {

                //get substring
                contentOutput = content.substring(firstIndex,lastIndex + last.length());

                //replace
                contentResult = content.replace(contentOutput, "");

            }else{
                contentResult = content;
            }
            return contentResult;
        }
    }



}
