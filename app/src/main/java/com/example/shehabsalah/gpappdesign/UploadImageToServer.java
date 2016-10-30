package com.example.shehabsalah.gpappdesign;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shehab on 1/22/2016.
 */
public class UploadImageToServer {
    JSONArray jsonArray;
    String res = null;
    String ImageData;
    JSONObject jsonObject;
    public JSONArray upload(Bitmap Image,Context context){
        ImageData = encodeTobase64(Image);
        Log.i("recogRunTime","start uploading");
        Log.i("base64: ", ImageData.length()+"");
        jsonObject = null;

        StringRequest request = new StringRequest(Request.Method.POST, "http://e2ralyserverside.mybluemix.net/GetTextOfThisImage",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("Entity Response  : ", response);
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
                parameters.put("binaryImage", ImageData);

                return parameters;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        request.setShouldCache(false);
        requestQueue.add(request);
        /*TO BE CONTINUE*/
        //................................................
        //................................................
        //................................................

        try {
            synchronized (this) {
                wait(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(jsonArray == null) {
            try {
                synchronized (this) {
                    wait(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (jsonArray == null) {
                try {
                    synchronized (this) {
                        wait(10000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(jsonArray == null){
                    try {
                        synchronized (this) {
                            wait(10000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }if(jsonArray == null){
                        try {
                            synchronized (this) {
                                wait(10000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }if(jsonArray == null){
                            try {
                                synchronized (this) {
                                    wait(10000);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }if(jsonArray == null){
                                try {
                                    synchronized (this) {
                                        wait(10000);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }if(jsonArray == null){
                                    try {
                                        synchronized (this) {
                                            wait(10000);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }if(jsonArray == null){
                                        try {
                                            synchronized (this) {
                                                wait(20000);
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }if(jsonArray == null){
                                            try {
                                                synchronized (this) {
                                                    wait(20000);
                                                }
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }if(jsonArray == null){
                                                try {
                                                    synchronized (this) {
                                                        wait(20000);
                                                    }
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }if(jsonArray != null)
                                                    return jsonArray;
                                            }else return jsonArray;
                                        }else return jsonArray;
                                    }else return jsonArray;
                                }else return jsonArray;
                            }else return jsonArray;;
                        }else return jsonArray;
                    }else return jsonArray;
                }else return jsonArray;
            } else return jsonArray;
        }else return jsonArray;
        return null;
    }
    /*public JSONArray getText(final int class_id,Context context){
            jsonObject = null;
            jsonArray = null;
            StringRequest request = new StringRequest(Request.Method.POST, "http://www.platformhouse.com/e2ralyMobApp/get_text.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                jsonObject = new JSONObject(response);
                                jsonArray = jsonObject.getJSONArray("result");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("Entity Response  : ", response);
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
                    parameters.put("class_number", class_id+"");
                    return parameters;
                }
            };
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            request.setShouldCache(false);
            requestQueue.add(request);
            *//*TO BE CONTINUE*//*
            //................................................
            //................................................
            //................................................

            try {
                synchronized (this) {
                    wait(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(jsonArray == null) {
                try {
                    synchronized (this) {
                        wait(10000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (jsonArray == null) {
                    try {
                        synchronized (this) {
                            wait(10000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(jsonArray == null){
                        try {
                            synchronized (this) {
                                wait(10000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }if(jsonArray != null)
                            return jsonArray;
                    }else return jsonArray;
                } else return jsonArray;
            }else return jsonArray;
            return null;
        }*/
    public Boolean startSaving(final String user_id, Bitmap Image,final String txt, Context context){
        ImageData = encodeTobase64(Image);
        Log.i("base64: ", ImageData);
        StringRequest request = new StringRequest(Request.Method.POST, "http://www.platformhouse.com/e2ralyMobApp/save_to_history.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        res = response;
                        Log.i("Entity Response  : ", response);
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
                parameters.put("image", ImageData);
                parameters.put("user_id", user_id);
                parameters.put("txt", txt);
                return parameters;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        request.setShouldCache(false);
        requestQueue.add(request);
        /*TO BE CONTINUE*/
        //................................................
        //................................................
        //................................................

        try {
            synchronized (this) {
                wait(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(res == null) {
            try {
                synchronized (this) {
                    wait(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (res == null) {
                try {
                    synchronized (this) {
                        wait(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(res == null){
                    try {
                        synchronized (this) {
                            wait(5000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }if(res != null)
                        return true;
                }else return true;
            } else return true;
        }else return true;
        return false;
    }
    public static String encodeTobase64(Bitmap image) {
        System.gc();
        if (image == null)return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT); // min minSdkVersion 8;
    }
}
