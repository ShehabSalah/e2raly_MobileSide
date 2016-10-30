package com.example.shehabsalah.gpappdesign;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterActivity extends Activity {
    EditText inputEmail;
    EditText Fname;
    EditText Lname;
    String FirstName;
    String LastName;
    String email;
    EditText pass;
    EditText pass2;
    String password;
    String password2;
    ImageView nextbtn;
    ProgressBar pb;
    final int MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        inputEmail = (EditText)findViewById(R.id.RegisterEmail);
        Fname = (EditText)findViewById(R.id.FName);
        Lname = (EditText)findViewById(R.id.LName);
        nextbtn = (ImageView)findViewById(R.id.email_sign_in_button);
        pass = (EditText)findViewById(R.id.pass);
        pass2 = (EditText)findViewById(R.id.pass2);
        pb = (ProgressBar)findViewById(R.id.loginprogressBar2);
        nextbtn.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                nextbtn.setImageResource(R.mipmap.finish_register_onclick);
                int Delay = 200;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextbtn.setImageResource(R.mipmap.finish_register);
                    }
                }, Delay);
                email = inputEmail.getText().toString().trim();
                // Check for empty data in the form
                if (!email.isEmpty() && email.contains("@") && email.contains(".com")) {
                    // login user
                   ConnectivityManager connMgr = (ConnectivityManager)
                            RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        /*************************************************************************/
                        FirstName = Fname.getText().toString().trim();
                        LastName = Lname.getText().toString().trim();
                        if( ( !FirstName.isEmpty() && !LastName.isEmpty() )){
                            if(FirstName.length() > 2 && LastName.length() > 2){
                                //check the password
                                password = pass.getText().toString().trim();
                                password2 = pass2.getText().toString().trim();
                                if(!password.isEmpty() && !password2.isEmpty()){
                                    if(password.equals(password2)){
                                        if(password.length() > 7){
                                            //Fire Register
                                            int permissionCheck = ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                                            if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                                                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED) {

                                                // Should we show an explanation?
                                                if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                                                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {


                                                } else {

                                                    // No explanation needed, we can request the permission.

                                                    ActivityCompat.requestPermissions(RegisterActivity.this,
                                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                                            MY_PERMISSIONS_REQUEST_READ_INTERNAL_STORAGE);

                                                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                                    // app-defined int constant. The callback method gets the
                                                    // result of the request.
                                                }
                                            }
                                            new RegisterEmail().execute(new Register());
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Password must be more than 8 characters!", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Password Doesn’t match!", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "The First Name and Last Name Minimum number of characters is 3!", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Please enter the First Name and Last Name!", Toast.LENGTH_LONG).show();
                        }
                        /*************************************************************************/
                    }else {
                        Toast.makeText(getApplicationContext(), "Network Not Availible!.\nPlease Connect to Internet and try again", Toast.LENGTH_LONG).show();
                    }

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
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

                    new RegisterEmail().execute(new Register());

                } else {

                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private class RegisterEmail extends AsyncTask<Register,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            pass.setVisibility(View.INVISIBLE);
            pass2.setVisibility(View.INVISIBLE);
            Fname.setVisibility(View.INVISIBLE);
            Lname.setVisibility(View.INVISIBLE);
            inputEmail.setVisibility(View.INVISIBLE);
            nextbtn.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Checking The Email!", Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(pb != null)
                pb.setProgress(values[0]);
        }
        @Override
        protected Boolean doInBackground(Register... params) {
            params[0].checkEmail(email,getApplicationContext());
            return Register.success;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            inputEmail.setVisibility(View.VISIBLE);
            nextbtn.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            if(Register.success && Register.container.contains("x")){
                //finish();
                //check the email
                new FullRegister().execute(new Register());

            }else{
                if(Register.container.equals("z"))
                    Toast.makeText(getApplicationContext(),
                            "Network Not Availible!.\n" +
                                    "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
                else if(Register.container.contains("y"))
                    Toast.makeText(getApplicationContext(),"ERROR: This Email is exists!" , Toast.LENGTH_LONG).show();
            }
            pass.setVisibility(View.VISIBLE);
            pass2.setVisibility(View.VISIBLE);
            Fname.setVisibility(View.VISIBLE);
            Lname.setVisibility(View.VISIBLE);
            inputEmail.setVisibility(View.VISIBLE);
            nextbtn.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
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
    private class FullRegister extends AsyncTask<Register,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Sign Up succeeded!", Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(pb != null)
                pb.setProgress(values[0]);
        }
        @Override
        protected Boolean doInBackground(Register... params) {
            params[0].registerUserInfo(email, password, FirstName,LastName, getApplicationContext());
            return Register.success;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            pass.setVisibility(View.VISIBLE);
            pass2.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Sign Up succeeded!", Toast.LENGTH_LONG).show();
            if((!LoginActivity.user_id.equals("0") && !LoginActivity.user_id.equals("-1") ) && Register.success){
                Log.i("idAndFac", "in2");
                writeToFile1(String.valueOf(LoginActivity.user_id));
                Intent i = new Intent(RegisterActivity.this,Main2Activity.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"ERROR: Register Not Succeeded!" , Toast.LENGTH_LONG).show();
            }
        }

    }
}