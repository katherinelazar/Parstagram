package me.katherinelazar.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.katherinelazar.parstagram.model.Post;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("klaz-insta")
                .clientKey("instagram")
                .server("http://katherinelazar-parstagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
