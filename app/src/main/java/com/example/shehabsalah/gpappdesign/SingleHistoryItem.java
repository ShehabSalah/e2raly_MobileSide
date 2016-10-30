package com.example.shehabsalah.gpappdesign;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleHistoryItem extends Activity {
    Button back;
    ImageView internet;
    ImageView ViewTextImage;
    TextView ViewTextRecognized;
    ImageLoader imageLoader;
    String imgDir = "http://www.platformhouse.com/e2ralyMobApp/images/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_history_item);
        internet = (ImageView)findViewById(R.id.NInternet);
        back = (Button)findViewById(R.id.dummy_button);
        ViewTextImage = (ImageView)findViewById(R.id.ViewTextImage);
        ViewTextRecognized = (TextView)findViewById(R.id.ViewTextRecognized);
        internet.setVisibility(View.INVISIBLE);
        imageLoader = new ImageLoader(getApplicationContext());
        back.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    protected void onResume() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            internet.setVisibility(View.INVISIBLE);
            ViewTextImage.setVisibility(View.VISIBLE);
            ViewTextRecognized.setVisibility(View.VISIBLE);
            try {
                JSONObject jo = User_History.jsonArray.getJSONObject(User_History.deletePosition);
                ViewTextRecognized.setText(jo.getString("_text_recognized"));
                imageLoader.DisplayImage(imgDir + jo.getString("_image_org"), ViewTextImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            internet.setVisibility(View.VISIBLE);
            ViewTextImage.setVisibility(View.INVISIBLE);
            ViewTextRecognized.setVisibility(View.INVISIBLE);
        }
        super.onResume();
    }
}
