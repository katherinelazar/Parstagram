package me.katherinelazar.parstagram;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.katherinelazar.parstagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private List<Post> mPosts;
    Context context;
    //pass in tweets array
    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    // for each row inflate layout and pass into ViewHolder class

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);

        return viewHolder;
    }

    // bind values based on position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data based on position
        Post post = mPosts.get(position);

        // populate views
        holder.username.setText(post.getUser().getUsername());
        holder.description.setText(post.getDescription());

//        ParseRelativeData parseRelativeData = new ParseRelativeData();

//        holder.tvTime.setText(parseRelativeData.getRelativeTimeAgo(post.createdAt));

        Glide.with(context).load(post.getImage().getUrl())
            .into(holder.ivProfileImage);
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    // create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfileImage;
        public TextView username;
        public TextView description;

//        public TextView tvTime;


        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.post_image);
            username = (TextView) itemView.findViewById(R.id.username);
            description = (TextView) itemView.findViewById(R.id.description);
//            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
