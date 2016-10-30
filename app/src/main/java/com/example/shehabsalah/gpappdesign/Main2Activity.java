package com.example.shehabsalah.gpappdesign;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main2Activity extends Activity {
    ImageView img;
    ImageView history;
    static String timeStamp;
    AlertDialog alertMenuDialog;
    static boolean map = false;
    View cameraV;

    static final int REQUEST_IMAGE_CAPTURE = 1313;
    final int MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE = 1;
    final int MY_PERMISSIONS_REQUEST_CAMERA = 2;
    static {
        if (!OpenCVLoader.initDebug()) {
            Log.i("opencvxy", "opencv initialzation fild");
        } else {
            Log.i("opencvxy", "opencv initialzation success");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        img = (ImageView)findViewById(R.id.imageView3);
        history = (ImageView)findViewById(R.id.imageView4);
        int permissionCheck = ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(Main2Activity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Main2Activity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////
        int permissionCheck2 = ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.CAMERA);
        if (ContextCompat.checkSelfPermission(Main2Activity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,
                    Manifest.permission.CAMERA)) {


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Main2Activity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        history.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(LoginActivity.user_id == null){
                    Intent i = new Intent(Main2Activity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(Main2Activity.this, User_History.class);
                    startActivity(i);
                }
            }
        });
        img.setOnClickListener(
                new ImageView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraV = v;
                        showMenuDialog();


                    }
                }
        );
        if(!hasCamera()) {
            img.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("no", null).show();
    }
    // check if the user has a camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    // launch the Camera
    public void launchCamera(View view){
        // we intent to tack a picture
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // make the qualty of the picture is high
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        File file = getFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        // tack a picture and send it to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {

                Intent i = new Intent(Main2Activity.this, ResultActivity.class);
                startActivity(i);
            }
        }
    }
    private File getFile(){
        File folder = new File("sdcard/e2raly");
        if(!folder.exists()){
            folder.mkdir();
        }
        File image = new File(folder, timeStamp+".jpg");
        return image;
    }

    private void showMenuDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Main2Activity.this);
        alert.setItems(R.array.camera, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                map = which!=0;
                Log.i("text_or_map", map + "");
                launchCamera(cameraV);
            }
        });
        alertMenuDialog = alert.create();
        alertMenuDialog.show();
    }

}
