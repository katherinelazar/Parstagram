package me.katherinelazar.parstagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.katherinelazar.parstagram.model.ImagePost;

public class TimeLineFragment extends Fragment {

    private MainActivityListener listener;
    AsyncHttpClient client;
    PostAdapter postAdapter;
    ArrayList<ImagePost> posts;
    RecyclerView rvPosts;

    //configure the SwipeRefreshLayout during view initialization in the activity:
    private SwipeRefreshLayout swipeContainer;

     // The onCreateView method is called when Fragment should create its View object hierarchy,
     // either dynamically or via XML layout inflation.
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        final View rootView = inflater.inflate(R.layout.fragment_time_line, parent, false);

         client = new AsyncHttpClient();

         // find the RecyclerView
         rvPosts = (RecyclerView) rootView.findViewById(R.id.rvPosts);

         // init the arrayList
         posts = new ArrayList<>();

         // construct adapter from data source
         postAdapter = new PostAdapter(posts);

         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
         //recyclerView setup
         rvPosts.setLayoutManager(linearLayoutManager);

         // set the adapter
         rvPosts.setAdapter(postAdapter);

         postAdapter.setListener(new PostAdapter.AdapterListener() {
             public void sendPostToDetails(ImagePost post) {
                 listener.sendPostToMainActivity(post);
             }
         });

         loadTopPosts();

         // Lookup the swipe container view
         swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
         // Setup refresh listener which triggers new data loading
         swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 // Your code to refresh the list here.
                 // Make sure you call swipeContainer.setRefreshing(false)
                 // once the network request has completed successfully.
                //  fetchTimelineAsync(0);
                 loadTopPosts();
             }
         });
         // Configure the refreshing colors
         swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                 android.R.color.holo_green_light,
                 android.R.color.holo_orange_light,
                 android.R.color.holo_red_light);

         return rootView;
     }

     // Listen for cue to display details fragment
    public interface MainActivityListener {
        void sendPostToMainActivity(ImagePost post);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivityListener) {
            listener = (MainActivityListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement Camera Fragment");
        }
    }

     // This event is triggered soon after onCreateView().
     // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
     @Override
     public void onViewCreated(View view, Bundle savedInstanceState) {
     // Setup any handles to view objects here
     // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

     }


    private void loadTopPosts() {
        final ImagePost.Query postsQuery = new ImagePost.Query();
        postsQuery.newestFirst()
                .limit20()
                .withUser();

        postsQuery.findInBackground(new FindCallback<ImagePost>() {
            @Override
            public void done(List<ImagePost> objects, ParseException e) {
                if (e == null) {

                    posts.clear();
                    posts.addAll(objects);
                    postAdapter.notifyDataSetChanged();
                    rvPosts.scrollToPosition(0);
                    
                    swipeContainer.setRefreshing(false);

                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("homeactivity", "post[" + i + " ] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername()
                        );;
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}