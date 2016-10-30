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
public class Register {
    public static boolean success;
    String user_id;
    String Fname;
    String Lname;
    static String container;
    public void checkEmail(final String email, final Context context) {
        success = false;
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://www.platformhouse.com/e2ralyMobApp/register.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("User_Response", response);
                if(response.contains("ok")){
                    success = true;
                    container = "x";
                }else if(response.contains("no")){
                    container = "y";
                    success = false;
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "Network Not Availible!.\n" +
                                "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
                Log.i("idAndFac", "error");
                container = "z";
                success = false;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
        try {synchronized (this) {wait(1000);}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(container == null){
            try {synchronized (this) {wait(2000);}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(container == null){
                try {synchronized (this) {wait(2000);}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(container == null){
                    try {synchronized (this) {wait(2000);}
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(container == null){
                        try {synchronized (this) {wait(2000);}
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        container = "z";
                    }
                }
            }
        }
    }
    public void registerUserInfo(final String email, final String password,final String fname,final String lname, final Context context) {
        success = false;
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://www.platformhouse.com/e2ralyMobApp/register.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                    String[] separated = response.split(" ");
                    if (separated.length > 0) {
                        user_id = separated[0];
                        Fname = separated[1];
                        Lname = separated[2];
                        Log.i("idAndFac", "in1");
                        success = true;
                    }
                    // Inserting row in users table
                    // Launch main activity
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "Network Not Availible!.\n" +
                                "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
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
                params.put("Fname", fname);
                params.put("Lname", lname);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
        try {synchronized (this) {wait(2000);}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Fname == null) {
            try {synchronized (this) {wait(2000);}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Fname == null) {
                try {synchronized (this) {wait(2000);}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Fname == null) {
                    try {synchronized (this) {wait(2000);}
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Fname == null) {
                        try {synchronized (this) {wait(2000);}
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (Fname == null) {
                            Toast.makeText(context,
                                    "Network is very slow!.\n" +
                                            "Please try again", Toast.LENGTH_LONG).show();
                            Log.i("idAndFac", "error");
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
