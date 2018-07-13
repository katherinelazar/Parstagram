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
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseFile;

import java.util.List;

import me.katherinelazar.parstagram.model.ImagePost;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private AdapterListener adapterListener;
    private List<ImagePost> mPosts;
    Context context;
    //pass in tweets array
    public PostAdapter(List<ImagePost> posts) {
        mPosts = posts;
    }

    // for each row inflate layout and pass into ViewHolder class
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);

        return viewHolder;
    }

    // bind values based on position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // get data based on position
        ImagePost post = mPosts.get(position);

        loadPostImage(post.getImage(), holder.image);
        loadAvatarImage(post.getAvatar(), holder.avatar);

        // populate views
        holder.username.setText(post.getUser().getUsername());
        holder.description.setText(post.getDescription());

//        ParseRelativeData parseRelativeData = new ParseRelativeData();
//        holder.tvTime.setText(parseRelativeData.getRelativeTimeAgo(post.createdAt));


        Glide.with(context).load(post.getImage().getUrl())
            .into(holder.image);
    }

    private void loadPostImage(final ParseFile imageFile, final ImageView imageView) {
        if (imageFile == null) {
            imageView.setImageResource(R.color.grey_5);
        } else {
            Glide.with(imageView)
                    .asBitmap()
                    .load(imageFile.getUrl())
                    .apply(
                            RequestOptions.centerCropTransform()
                                    .placeholder(R.color.grey_5)
                                    .error(R.color.grey_5)
                    )
                    .transition(
                            BitmapTransitionOptions.withCrossFade()
                    )
                    .into(imageView);
        }
    }

    private void loadAvatarImage(final ParseFile avatarFile, final ImageView avatarView) {
        if (avatarFile == null) {
            avatarView.setImageResource(R.drawable.ic_placeholder_circle);
        } else {
            Glide.with(avatarView)
                    .asBitmap()
                    .load(avatarFile.getUrl())
                    .apply(
                            RequestOptions.circleCropTransform()
                                    .placeholder(R.drawable.ic_placeholder_circle)
                                    .error(R.drawable.ic_placeholder_circle)
                    )
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(avatarView);

        }
    }

    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView username;
        ImageView avatar;
        TextView likeCount;
        TextView description;
        TextView timeSince;
        ImageView likeButton;
        ImageView commentButton;
        ImageView messageButton;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.post_image_iv);
            username = (TextView) itemView.findViewById(R.id.username_tv);
            description = (TextView) itemView.findViewById(R.id.description_tv);
            avatar = (ImageView) itemView.findViewById(R.id.user_avatar_iv);
            likeCount = (TextView) itemView.findViewById(R.id.likes_count_tv);
            timeSince = (TextView) itemView.findViewById(R.id.time_since_tv);
            likeButton = (ImageView) itemView.findViewById(R.id.like_iv);
            commentButton = (ImageView) itemView.findViewById(R.id.comment_iv);
            messageButton = (ImageView) itemView.findViewById(R.id.message_iv);
//            tvTime = (TextView) itemView.findViewById(R.id.tvTime);

            // itemview's onclicklistener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {

                ImagePost post = mPosts.get(position);

                adapterListener.sendPostToDetails(post);

            }
        }
    }

    // set teh listener, called from fragment
    public void setListener(AdapterListener listener) {
        adapterListener = listener;
    }
    //listener to viewholer. intent to activity (wrapped post)

    public interface AdapterListener {
        void sendPostToDetails(ImagePost post);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
