package com.example.videosharing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private  static  int CAMERA_PERMISSION_CODE=100;
    private  static  int VIDEO_RECORD_CODE=101;

    private Uri VideoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isCameraPresentInPhone()){
            Log.i("VIDEO_RECORD_TAG","Camera is Detected");
            getCameraPermission();
        }
        else{
            Log.i("VIDEO_RECORD_TAG","No camera");
        }


    }

    public void recordVideoButtonPressed(View view){
        recordVideo();
    }

    private boolean isCameraPresentInPhone(){
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        }
        else{
            return false;
        }
    }

    private void getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
        }
    }

    private void recordVideo(){
        Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==VIDEO_RECORD_CODE) {
            if (resultCode == RESULT_OK){

                VideoPath=data.getData();
                Log.i("VIDEO_RECORD_TAG", "Video is recorded and available at path "+ VideoPath);
            }
        }
        else if(resultCode== RESULT_CANCELED){
            Log.i("VIDEO_RECORD_TAG", "Recording video is cancelled");
        }
        else{
            Log.i("VIDEO_RECORD_TAG", "Recording video has error");
        }
    }
}