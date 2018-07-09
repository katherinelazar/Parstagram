package me.katherinelazar.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import me.katherinelazar.parstagram.model.Post;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        try {
                            Log.d("homeactivity", "post[" + i + " ] = "
                                    + objects.get(i).getDescription()
                                    + "\nusername = " + objects.get(i).getUser().fetchIfNeeded().getUsername()
                            );
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    }

                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
