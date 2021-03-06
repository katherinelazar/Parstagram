package me.katherinelazar.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class CreateNewPost extends AppCompatActivity {

    private static final String imagePath = "/storage/emulated/0/Download/thumbs_boxer-dogs-puppies-3.jpg";
    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);

        descriptionInput = findViewById(R.id.description_et);
        createButton = findViewById(R.id.create_btn);
        refreshButton = findViewById(R.id.refresh_btn);

//        createButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String description = descriptionInput.getText().toString();
//                final ParseUser user = ParseUser.getCurrentUser();
//
//                final File file = new File(imagePath);
//                final ParseFile parseFile = new ParseFile(file);
//
//                parseFile.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e == null) {
//                            createPost(description, parseFile, user);
//                        } else {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
//
//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadTopPosts();
//            }
//        });
    }

//    private void createPost(final String description, final ParseFile imageFile, final ParseUser user) {
//        imageFile.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.d("CreateNewPost", "image save success");
//
//                    final Post newPost = new Post();
//                    newPost.setDescription(description);
//                    newPost.setImage(imageFile);
//                    newPost.setUser(user);
//
//                    newPost.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                Log.d("CreateNewPost", "create post success");
//                            } else {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


}
