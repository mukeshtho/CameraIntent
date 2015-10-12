package com.mthotengera.cameraintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    public final int REQUEST_CODE =1000;
    private ImageView mPhotoCaptureImageView;
    private String mImageFileLocation = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPhotoCaptureImageView = (ImageView) findViewById(R.id.photoCapture);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mImageFileLocation = savedInstanceState.getString("imageLocation");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("imageLocation",mImageFileLocation);
    }

    public void openCamera(View view){
        Snackbar.make(view, "Clicked on Camera button", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT , Uri.fromFile(photoFile));
        startActivityForResult(callCameraApplicationIntent, REQUEST_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();
            Bitmap photoCaptureBitmap =  BitmapFactory.decodeFile(mImageFileLocation);
            mPhotoCaptureImageView.setImageBitmap(photoCaptureBitmap);
        }
    }

    public void convertPhoto(View view){
        Snackbar.make(view, "Clicked on blur button", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    public void applyCartoonFilter(View view){
        Snackbar.make(view, "Clicked on Cartoon Filter button", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    File createImageFile() throws IOException{
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_"+ timestamp;
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg",storageDirectory);
        mImageFileLocation = image.getAbsolutePath();
        return image;
    }

}
