package de.dominikwieners.androidhive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.dominikwieners.androidhive.R;
import de.dominikwieners.androidhive.model.Post;

/**
 * Created by dominikwieners on 23.08.17.
 */

public class FavoritePostAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<Post> posts;

    public FavoritePostAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_entry, parent, false);
        return new FavoritePostAdapter.PostViewHolder(view);
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

            cardPt.setText(currentPost.getWpTitle());
        }

        @Override
        public void onClick(View v) {

        }


    }
}
