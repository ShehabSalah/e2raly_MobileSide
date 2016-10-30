package com.example.shehabsalah.gpappdesign;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shehab on 3/8/2016.
 */
public class LoginConnector {
    public static boolean success;
    String user_id;
    String Fname;
    String Lname;
    public void checkLogin(final String email, final String password, final Context context) {
        success = false;
        user_id = "0";
        Fname = "z";
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://www.platformhouse.com/e2ralyMobApp/login.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(!response.contains("The Email or Password you've") && !response.equals("You are missing Email or Password") && !response.contains("null")){
                    String[] separated = response.split(" ");
                    if(separated.length > 0){
                        user_id = separated[0];
                        Log.i("balabala", response);
                        // Inserting row in users table
                        // Launch main activity
                        success = true;
                    }
                }else if(response.contains("You are missing Email or Password")){
                    Log.i("balabala", "You are missing Email or Password!");
                    user_id = "-1";
                    Fname = "s";
                    success = false;
                }else if(response.contains("null")){
                    Log.i("balabala", "This Email is not exist!");
                    Fname = "s";
                    LoginActivity.user_Fname = "s";
                    LoginActivity.user_id = "0";
                    user_id = "0";
                    success = false;
                }else{
                    Log.i("balabala", response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("idAndFac", "error");
                success = false;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
        try {synchronized (this) {wait(2000);}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Fname == null){
            try {synchronized (this) {wait(2000);}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Fname == null){
                try {synchronized (this) {wait(2000);}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(Fname == null){
                    try {synchronized (this) {wait(2000);}
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(Fname == null){
                        try {synchronized (this) {wait(2000);}
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        LoginActivity.user_id = user_id;
        LoginActivity.user_Fname = Fname;
        LoginActivity.user_Lname = Lname;
    }
}
