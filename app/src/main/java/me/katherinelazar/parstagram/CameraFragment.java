package me.katherinelazar.parstagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CameraFragment extends Fragment {

    private Button cameraRoll;
    private Button camera;

    private final int CAMERA_REQUEST_CODE = 15;
    private final int CAMERA_ROLL_REQUEST_CODE = 16;

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
        return inflater.inflate(R.layout.fragment_camera, parent, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        cameraRoll = getView().findViewById(R.id.chooseCameraRoll);
        camera = getView().findViewById(R.id.chooseCamera);

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


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.ACTION_IMAGE_CAPTURE");



                startActivityForResult(intent, CAMERA_REQUEST_CODE);

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
        // REQUEST_CODE is defined above
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // convert byte array to Bitmap

            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length);

            imageView.setImageBitmap(bitmap);
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




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();
    }
}