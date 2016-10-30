package com.example.shehabsalah.gpappdesign;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shehab on 4/1/2016.
 */
public class GetUserHistory {
    public JSONArray getHistoryList(final String user_id, int limit){
        JSONArray history = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://www.platformhouse.com/e2ralyMobApp/get_user_history.php?user_id=" + user_id + "&limit=" + limit);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null)
                buffer.append(line);
            String jsontxt = buffer.toString();
            JSONObject jsonObject = new JSONObject(jsontxt);
            history = jsonObject.getJSONArray("history");
            Log.i("jsonString", jsontxt);
            connection.disconnect();
            return history;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public Boolean deleteItem(final int HistoryItemID, Context context){
        StringRequest request = new StringRequest(Request.Method.POST, "http://www.platformhouse.com/e2ralyMobApp/delete_history_item.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error Response", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("item", HistoryItemID+"");
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
        try {
            synchronized (this) {
                wait(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoginActivity.user_id = null;
        return true;
    }
}