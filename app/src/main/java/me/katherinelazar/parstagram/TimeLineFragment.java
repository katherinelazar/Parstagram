package me.katherinelazar.parstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    AsyncHttpClient client;
    PostAdapter postAdapter;
    ArrayList<ImagePost> posts;
    RecyclerView rvPosts;

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
         linearLayoutManager.setReverseLayout(true);
         linearLayoutManager.setStackFromEnd(true);

         // set the adapter
         rvPosts.setAdapter(postAdapter);

         loadTopPosts();


         return rootView;
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
        postsQuery.limit20().withUser();

        postsQuery.findInBackground(new FindCallback<ImagePost>() {
            @Override
            public void done(List<ImagePost> objects, ParseException e) {
                if (e == null) {

                    posts.addAll(objects);
                    postAdapter.notifyDataSetChanged();

                    for (int i = 0; i < objects.size(); i++) {


//                        Log.d("homeactivity", "post[" + i + " ] = "
//                                + objects.get(i).getDescription()
//                                + "\nusername = " + objects.get(i).getUser().getUsername()
//                        );;
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
 }