package me.katherinelazar.parstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import me.katherinelazar.parstagram.model.ImagePost;
import me.katherinelazar.parstagram.model.UserWrapper;

public class ShowDetailsFragment extends Fragment {

    public ImagePost post;
    ImageView image;
    TextView username;
    ImageView avatar;
    TextView likeCount;
    TextView description;
    TextView timeSince;
    ImageView likeButton;
    ImageView commentButton;
    ImageView messageButton;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation. 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_show_details, parent, false);
    }



    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View itemView, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);


        image = (ImageView) itemView.findViewById(R.id.post_image_iv);
        username = (TextView) itemView.findViewById(R.id.username_tv);
        description = (TextView) itemView.findViewById(R.id.description_tv);
        avatar = (ImageView) itemView.findViewById(R.id.user_avatar_iv);
        likeCount = (TextView) itemView.findViewById(R.id.likes_count_tv);
        timeSince = (TextView) itemView.findViewById(R.id.time_since_tv);
        likeButton = (ImageView) itemView.findViewById(R.id.like_iv);
        commentButton = (ImageView) itemView.findViewById(R.id.comment_iv);
        messageButton = (ImageView) itemView.findViewById(R.id.message_iv);

        UserWrapper user = post.getUser();

        username.setText(user.getUsername());
        description.setText(post.getDescription());

        ParseFile userProfile = user.getAvatar();

        ParseFile postImage = post.getImage();


        Glide.with(getContext()).load(postImage.getUrl()).into(avatar);

//        Glide.with(getContext()).load(userProfile.getUrl()).into(avatar);
        Glide.with(getContext()).load(postImage.getUrl()).into(image);


    }
}


