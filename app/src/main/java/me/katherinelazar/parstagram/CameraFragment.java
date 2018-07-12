package me.katherinelazar.parstagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;

import me.katherinelazar.parstagram.model.Post;

public class CameraFragment extends Fragment {

    private Button cameraRoll;
    private Button camera;

    private Button post;
    private EditText description;

    private final int CAMERA_REQUEST_CODE = 15;
    private final int CAMERA_ROLL_REQUEST_CODE = 16;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;


    File file;
    public String photoFileName = "photo.jpg";

    ImageView imageView;
    Activity activity;

    interface Callback {
        public void hideActionBar();
        public void showActionBar();
    }

    Callback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Callback) {
            callback = (Callback) context;
        }
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        final View rootview = inflater.inflate(R.layout.fragment_camera, parent, false);

        return rootview;
    }


    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        description = getView().findViewById(R.id.editText);
        post = getView().findViewById(R.id.submit_post);


        cameraRoll = getView().findViewById(R.id.chooseCameraRoll);
        camera = getView().findViewById(R.id.chooseCamera);
        imageView = getView().findViewById(R.id.imageView);

        // have view as one of your parameters
        cameraRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent for picking a photo from the gallery
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent, CAMERA_ROLL_REQUEST_CODE);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(activity, "clicked post", Toast.LENGTH_LONG).show();

                final String desc = description.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                file = getPhotoFileUri(photoFileName);
                final ParseFile parseFile = new ParseFile(file);

                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            createPost(desc, parseFile, user);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void createPost(final String description, final ParseFile imageFile, final ParseUser user) {
        Toast.makeText(activity, "Entered createpost", Toast.LENGTH_LONG).show();
        imageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
            if (e == null) {
                Log.d("CreateNewPost", "image save success");

                final Post newPost = new Post();
                newPost.setDescription(description);
                newPost.setImage(imageFile);
                newPost.setUser(user);

                newPost.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                    if (e == null) {
                        Log.d("CreateNewPost", "create post success");
                        Toast.makeText(activity, "Successfully post", Toast.LENGTH_LONG).show();
                    } else {
                        e.printStackTrace();
                    }
                    }
                });
            } else {
                e.printStackTrace();
            }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isVisible()) {
            callback.hideActionBar();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!isVisible()) {
            callback.showActionBar();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Toast.makeText(activity, "Entered onActivityResult", Toast.LENGTH_LONG).show();
        // REQUEST_CODE is defined above
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {

//            Bitmap bmp = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//            // convert byte array to Bitmap
//            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
////
//            imageView.setImageBitmap(bitmap);

            Bitmap takenImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            // RESIZE BITMAP, see section below
            // Load the taken image into a preview
            imageView.setImageBitmap(takenImage);

            } else { // Result was a failure
                 Toast.makeText(activity, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_ROLL_REQUEST_CODE) {

            if (data != null) {
                Uri photoUri = data.getData();
                // Do something with the photo based on Uri
                Bitmap selectedImage = null;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview


                imageView.setImageBitmap(selectedImage);
//                ImageView ivPreview = (ImageView) Activity.findViewById(R.id.ivPreview);
//                ivPreview.setImageBitmap(selectedImage);
            }

        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

                Toast.makeText(activity, "Entered camera", Toast.LENGTH_LONG).show();

                // Create a File reference to access to future access
                file = getPhotoFileUri(photoFileName);

                Uri fileProvider = FileProvider.getUriForFile(activity, "com.codepath.fileprovider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
    }
}

//android.media.action.ACTION_IMAGE_CAPTURE
//MediaStore.ACTION_IMAGE_CAPTURE