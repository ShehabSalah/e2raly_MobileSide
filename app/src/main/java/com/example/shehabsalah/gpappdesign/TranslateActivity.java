package com.example.shehabsalah.gpappdesign;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import android.widget.TextView;
public class TranslateActivity extends Activity {
    TextView translatableText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        translatableText = (TextView) findViewById(R.id.translatableText);
        translated();
        /*if (ResultActivity.success) {
            translated();
        } else {
            translatableText.setText(R.string.translation_error);
        }*/
    }

    public void translated() {
        String translatedText = ResultActivity.recognizedText;
        //String translatedText = "مصر";
        new Start_Translation().execute(translatedText);
    }
    public String getText(String translatedText){
        return new GoogleTranslateActivity().translate(translatedText, "ar", "en", "AIzaSyClL6buFzkxVbW9lyzd9WrKqYZh1er6z4U");
    }
    private class Start_Translation extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            return getText(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            translatableText.setText(s);
        }
    }
}