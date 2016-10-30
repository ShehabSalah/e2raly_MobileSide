package com.example.shehabsalah.gpappdesign;
import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
/*import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;*/

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    ImageView login_btn;
    EditText inputEmail;
    EditText inputPass;
    Button registerNow;
    String email;
    String password;
    ProgressBar pb;
    /******************************************/
    // User Saved info
    static String user_id = null;
    static String user_Fname;
    static String user_Lname;
    /******************************************/
    final int MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn = (ImageView)findViewById(R.id.email_sign_in_button);
        inputEmail = (EditText)findViewById(R.id.email);
        inputPass = (EditText)findViewById(R.id.password);
        registerNow = (Button)findViewById(R.id.registerNow);
        pb = (ProgressBar)findViewById(R.id.loginprogressBar2);
        /*******************************************************/
        //************** check if user exists *****************//
        new CheckIfUserExists().execute(1);
        //************** check if user exists *****************//
        /*******************************************************/
        login_btn.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                login_btn.setImageResource(R.mipmap.loginbtn_onclick);
                int Delay = 200;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login_btn.setImageResource(R.mipmap.loginbtn);
                    }
                }, Delay);
                email = inputEmail.getText().toString().trim();
                password = inputPass.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    ConnectivityManager connMgr = (ConnectivityManager)
                            LoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        /*********************************************/
                        //************** user Login *****************//
                        int permissionCheck = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {


                            } else {

                                // No explanation needed, we can request the permission.

                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        }
                        new Login().execute(new LoginConnector());
                        //************** user Login *****************//
                        /*********************************************/
                    } else {
                        Toast.makeText(getApplicationContext(), "Network Not Availible!.\nPlease Connect to Internet and try again", Toast.LENGTH_LONG).show();
                    }

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }

            }
        });
        registerNow.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new Login().execute(new LoginConnector());

                } else {

                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void writeToFile1(String user_id) {
        try
        {
            Log.i("dir","1");
            File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "e2raly");
            if(!folder.exists()){
                Log.i("dir","2");
                folder.mkdir();
            }
            Log.i("dir","id:" + user_id);

            File gpxfile = new File(folder, "Config.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(user_id);
            writer.flush();
            writer.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private Boolean readFromFile() {
        Boolean access = false;
        boolean cont = true;
        File folder = new File("sdcard/e2raly");
        if(!folder.exists()){
            folder.mkdir();
            cont = false;
        }
        if(cont){
            File file = new File(folder,"Config.txt");
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                }
                br.close();
                if(text.length() > 0) {
                    if(text.toString().equals("null"))
                        Log.i("user_id: ","null");
                    LoginActivity.user_id = text.toString();

                    access = true;
                }
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
            }
        }
        return access;
    }
    private class Login extends AsyncTask<LoginConnector,Integer,Boolean>{
        @Override
        protected void onPreExecute() {
            inputEmail.setVisibility(View.INVISIBLE);
            inputPass.setVisibility(View.INVISIBLE);
            login_btn.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
            user_id = null;
            user_Fname = null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(pb != null)
                pb.setProgress(values[0]);
        }

        @Override
        protected Boolean doInBackground(LoginConnector... params) {
            params[0].checkLogin(email, password,getApplicationContext());
            return LoginConnector.success;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            inputEmail.setVisibility(View.VISIBLE);
            inputPass.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            if((!LoginActivity.user_id.equals("0") && !LoginActivity.user_id.equals("-1")) && LoginConnector.success){
                Log.i("idAndFac", LoginActivity.user_id);
                writeToFile1(LoginActivity.user_id);
                Intent i = new Intent(LoginActivity.this,User_History.class);
                startActivity(i);
                finish();
            }else if(user_id.equals("-1") && user_Fname.equals("s")){
                Toast.makeText(getApplicationContext(),"ERROR: You are missing Email or Password!" , Toast.LENGTH_LONG).show();
            }else if(user_id.equals("0") && user_Fname.equals("s")){
                Toast.makeText(getApplicationContext(),"ERROR: The Email or Password you've entered is incorrect!" , Toast.LENGTH_LONG).show();
            }else{
                Log.i("user_id: ",user_id);
                Log.i("user_idF: ",user_Fname);
                Toast.makeText(getApplicationContext(),"ERROR: Network Error!" , Toast.LENGTH_LONG).show();
            }
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
      /*  AppEventsLogger.activateApp(this);*/
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        /*AppEventsLogger.deactivateApp(this);*/
    }
    private class CheckIfUserExists extends AsyncTask<Integer,Long,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Intent i = new Intent(LoginActivity.this,User_History.class);
                startActivity(i);
                finish();
            }
        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            return readFromFile();
        }
    }
}