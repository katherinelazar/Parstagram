package me.katherinelazar.parstagram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CameraFragment.Callback {


    private final int CAMERA_REQUEST_CODE = 15;
    private final int CAMERA__ROLL_REQUEST_CODE = 16;
    // The list of fragments used in the view pager. They live in the activity and we pass them down
    //   to the adapter upon creation.
    private final List<Fragment> fragments = new ArrayList<>();

    // reference to view pager
    private ViewPager viewPager;

    private final int REQUEST_CODE = 20;

    //The adapter used to display information for our bottom navigation view.
    private ExampleAdapter adapter;

    // A reference to our bottom navigation view.
    private BottomNavigationView bottomNavigation;

    CameraFragment cameraFragment = new CameraFragment();

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Create the placeholder fragments to be passed to the ViewPager

        fragments.add(new NotYetImplementedFragment());
        fragments.add(new NotYetImplementedFragment());
        fragments.add(cameraFragment);
        fragments.add(new NotYetImplementedFragment());
        fragments.add(new NotYetImplementedFragment());

        // Grab a reference to our view pager.
        viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(0);

        // Instantiate our ExampleAdapter which we will use in our ViewPager
        adapter = new ExampleAdapter(getSupportFragmentManager(), fragments);

        // Attach our adapter to our view pager.
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().show();
                
                switch (position) {
                    case 0:
                        bottomNavigation.setSelectedItemId(R.id.action_home);
                        break;
                    case 1:
                        bottomNavigation.setSelectedItemId(R.id.action_discover);
                        break;
                    case 2:
                        bottomNavigation.setSelectedItemId(R.id.action_camera);
                        getSupportActionBar().hide();
                        break;
                    case 3:
                        bottomNavigation.setSelectedItemId(R.id.action_likes);
                        break;
                    case 4:
                        bottomNavigation.setSelectedItemId(R.id.action_profile);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // Grab a reference to our bottom navigation view
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Handle the click for each item on the bottom navigation view.
        // we then delegate this out to the view pager adapter such that it can switch the
        // page which we are currently displaying
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        // Set the item to the first item in our list.
                        // This is the home placeholder fragment.
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.action_discover:
                        viewPager.setCurrentItem(1);
                        return true;

                    case R.id.action_camera:
                        viewPager.setCurrentItem(2);

                        //callbackitem here


                        // it goes outside of application
                        // then we need to handle the activity result (how do we do this? what method????)
                        //
                        // Once we have the photo... how will the user input the description?
                        // will we create a fragment and show it at this time? will we create a new activity?
                        return true;
                    case R.id.action_likes:
                        viewPager.setCurrentItem(3);
                        return true;
                    case R.id.action_profile:
                        viewPager.setCurrentItem(4);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public void hideActionBar() {
//        getSupportActionBar().hide();
    }

    @Override
    public void showActionBar() {
//        getSupportActionBar().show();
    }

    //    // ActivityOne.java, time to handle the result of the sub-activity
//    @Override
//    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//        // REQUEST_CODE is defined above
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            final Intent intent = new Intent(MainActivity.this, CreateNewPost.class);
//            startActivity(intent);
//        }
//    }


    /**
     * The example view pager which we use in combination with the bottom navigation view to make
     * a smooth horizontal sliding transition.
     */
    static class ExampleAdapter extends FragmentPagerAdapter {

        /**
         * The list of fragments which we are going to be displaying in the view pager.
         */
        private final List<Fragment> fragments;

        public ExampleAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);

            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // convert byte array to Bitmap

            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length);

            cameraFragment.imageView.setImageBitmap(bitmap);
        }

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA__ROLL_REQUEST_CODE) {

        }
    }

}
