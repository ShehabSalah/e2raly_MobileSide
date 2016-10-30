package com.example.shehabsalah.gpappdesign;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.shehabsalah.gpappdesign.util.SystemUiHider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ResultActivity extends Activity {
    //XML Elements Variables
    TextView txt;
    EditText result;
    Button save;
    static int x= 0;
    Bitmap rotatedBitmap;   // bitmap image after rotation
    int ClassId;    // Class number of Recoginized text
    int num;        // helper number to
    public final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    static String recognizedText;
    static Boolean success;
    private Activity activity;
    private Context context;
    View view;
    ImageProcess process;
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
        setContentView(R.layout.activity_result);
        result = (EditText)findViewById(R.id.result);
        result.setVisibility(View.INVISIBLE);
        save = (Button)findViewById(R.id.Save_to_History);
        save.setVisibility(View.INVISIBLE);
        txt = (TextView)findViewById(R.id.message);
        success = false;
        num = 1;
        Button translateBtn = (Button)findViewById(R.id.translateButton);
        ImageView header = (ImageView)findViewById(R.id.imageView5);
        header.setScaleType(ImageView.ScaleType.FIT_XY);
        /******************Delete********************************/
        /*******************************************************/
        //************** check if user exists *****************//
        new CheckIfUserExists().execute(1);
        //************** check if user exists *****************//
        /*******************************************************/


        /*******************************************************/
        //**************** Start Processing *******************//
        process = new ImageProcess();
        //process.execute("sdcard/e2raly/"+Main2Activity.timeStamp+".bmp");
        process.execute();
        //**************** Start Processing *******************//
        /*******************************************************/

        translateBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, TranslateActivity.class);
                startActivity(i);
            }
        });
        save.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(LoginActivity.user_id == null) {
                    Intent i = new Intent(ResultActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else
                    new SaveToHistory().execute();

            }
        });
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
    public class SaveToHistory extends  AsyncTask<String,Integer,Boolean>{
        @Override
        protected void onPreExecute() {
            txt.setVisibility(View.VISIBLE);
            result.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            txt.setText("Please wait...");
            recognizedText = result.getText().toString();
        }

        @Override
        protected void onPostExecute(Boolean b) {
            txt.setVisibility(View.INVISIBLE);
            result.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            if(b){
                Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "ERROR: Network Error!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //downsize the image to 480 x 680
            rotatedBitmap = Bitmap.createScaledBitmap(rotatedBitmap,480, 680,true);
            return new UploadImageToServer().startSaving(LoginActivity.user_id,rotatedBitmap,recognizedText,getApplicationContext());
        }
    }
    public class ImageProcess extends AsyncTask<String,String,Bitmap> {
        Matrix matrix;
        JSONArray ImageData;
        Bitmap blackBitImage;
        @Override
        protected void onPreExecute() {
            txt.setText(R.string.upload);
        }
        private String shehabSalah(){
            return null;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try{
                Log.i("recogRunTime","do in  background");
                String path = "sdcard/e2raly/"+Main2Activity.timeStamp+".jpg";
                matrix = new Matrix();
                matrix.postRotate(0);
                Bitmap bitmap = ((BitmapDrawable)Drawable.createFromPath(path)).getBitmap();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                //downsize the image to 680 x 880
                rotatedBitmap = Bitmap.createScaledBitmap(rotatedBitmap,680, 880,true);
                //rotatedBitmap = imageCrop(rotatedBitmap);
                Mat tmp = new Mat (rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), CvType.CV_8U);
                Utils.bitmapToMat(rotatedBitmap, tmp);

                Imgproc.cvtColor(tmp, tmp, Imgproc.COLOR_RGB2GRAY);
                Imgproc.GaussianBlur(tmp, tmp, new Size(3, 3), 0);
                Imgproc.adaptiveThreshold(tmp, tmp, 255.0, Imgproc.ADAPTIVE_THRESH_MEAN_C,
                        Imgproc.THRESH_BINARY, 15, 11);
                blackBitImage = Bitmap.createBitmap(tmp.cols(), tmp.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(tmp, blackBitImage);
                tmp.release();
                bitmap.recycle();scaledBitmap.recycle();
                bitmap = null;scaledBitmap = null;tmp = null;
                System.gc();
                matrix.postRotate(90);
                blackBitImage = Bitmap.createBitmap(blackBitImage, 0, 0, blackBitImage.getWidth(), blackBitImage.getHeight(), matrix, true);
               ImageData = new UploadImageToServer().upload(blackBitImage, getApplicationContext());
            }catch (Exception e){}
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
           if(ImageData != null){
                try {
                    Log.i("recogRunTime","on post ex");
                    JSONObject jsonObject = ImageData.getJSONObject(0);
                    String message = jsonObject.getString("result_classes");
                    String[] words = message.split(" ");
                    Log.i("recogRunTime",message);
                    //new Get_text_of_class().execute();
                    recognizedText = "";
                    TextRecognized text = new TextRecognized();
                    for(int i = 0; i < words.length; i++){
                        double x = !words[i].equals("")&&!words[i].equals(" ")?Double.parseDouble(words[i]):29.0;
                        Log.i("recogRunTime",words[i]);
                        ClassId = (int)x;
                        recognizedText += text.fetchText(ClassId);
                    }
                    //recognizedText = new TextRecognized().fetchText(ClassId);
                    /*switch(x){
                        case 0:{recognizedText = "مدينة نصر";x++;break;}
                        case 1:{recognizedText = "مصر الجديدة";x++;break;}
                        case 2:{recognizedText = "شارع البحر الأسكندرية";x++;break;}
                        case 3:{recognizedText = "أسد";x++;break;}
                        case 4:{recognizedText = "عيد سعيد";x++;break;}
                    }*/
                    txt.setVisibility(View.INVISIBLE);
                    result.setVisibility(View.VISIBLE);
                    result.setText(recognizedText);
                    recognizedText = result.getText().toString();
                    save.setVisibility(View.VISIBLE);
                    if(Main2Activity.map){
                        Intent i = new Intent(ResultActivity.this, MapsActivity.class);
                        startActivity(i);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }else{
                txt.setText("Processes takes more than the required time.\nPlease check the internet speed and try again.");
            }
            saveImageToExternalStorage(blackBitImage);
            /*txt.append("\nimage number " + num +" Done!");
           if(num < 15){
                num++;
                new ImageProcess().execute("sdcard/e2raly/"+num+".jpg");
            }*/
        }
    }
    public void saveImageToExternalStorage(Bitmap image) {
        String fullPath = "sdcard/e2raly/";
        try
        {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            OutputStream fOut = null;
            //File file = new File(fullPath, Main2Activity.timeStamp+"_2.bmp");
            File file = new File(fullPath, num+"_bw.jpg");
            if(file.exists())
                file.delete();
            file.createNewFile();
            fOut = new FileOutputStream(file);
            // 100 means no compression, the lower you go, the stronger the compression
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        }
        catch (Exception e)
        {
            Log.e("saveToExternalStorage()", e.getMessage());
        }
    }
    private class CheckIfUserExists extends AsyncTask<Integer,Long,Boolean>{
        @Override
        protected Boolean doInBackground(Integer... params) {
            return readFromFile();
        }
    }

    public  Bitmap imageCrop(Bitmap input) {
        int height = input.getHeight();
        int width = input.getWidth();
        int hend = -1, hstart = Integer.MAX_VALUE, wend = -1, wstart = Integer.MAX_VALUE;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (input.getPixel(j, i) == Color.BLACK) {
                    if (hstart > i) {
                        hstart = i;
                    }
                    if (wstart > j) {
                        wstart = j;
                    }
                    if (hend < i) {
                        hend = i;
                    }
                    if (wend < j) {
                        wend = j;
                    }
                }
            }
        }
        return input;
    }
    /*private class Get_text_of_class extends AsyncTask<String,Long,JSONArray>{
        @Override
        protected void onPreExecute() {
            txt.setText(R.string.recognize);
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            return new UploadImageToServer().getText(ClassId, getApplicationContext());
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(jsonArray!=null){
                JSONObject jsonObject = null;
                String message = "";
                try {
                    jsonObject = jsonArray.getJSONObject(0);
                    message = jsonObject.getString("Word");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                success = true;
                if(Main2Activity.map){
                    Intent i = new Intent(ResultActivity.this, MapsActivity.class);
                    startActivity(i);
                }else{
                    txt.setVisibility(View.INVISIBLE);
                    result.setVisibility(View.VISIBLE);
                    result.setText(message);
                    recognizedText = result.getText().toString();
                    save.setVisibility(View.VISIBLE);
                }
            }
        }
    }*/
}

/*blackBitImage = Bitmap.createBitmap(tmp.cols(), tmp.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(tmp, blackBitImage);
                    int[][] binaryImage = new int[blackBitImage.getWidth()][blackBitImage.getHeight()];
                    for (int x = 0; x < binaryImage.length; x++) {
                        for (int y = 0; y < binaryImage[0].length; y++) {
                            if (blackBitImage.getPixel(x, y) == Color.BLACK)
                                binaryImage[x][y] = 1;
                            else
                                binaryImage[x][y] = 0;
                        }
                    }

                ThinningService thinningService = new ThinningService();
                thinningService.doZhangSuenThinning(binaryImage);
                for (int x = 0; x < binaryImage.length; x++) {
                    for (int y = 0; y < binaryImage[0].length; y++) {
                        if (binaryImage[x][y] == 0)
                            blackBitImage.setPixel(x,y,Color.WHITE);
                        else
                            blackBitImage.setPixel(x,y,Color.BLACK);
                    }
                }
                tmp = new Mat (blackBitImage.getWidth(), blackBitImage.getHeight(), CvType.CV_8U);
                Utils.bitmapToMat(blackBitImage, tmp);
                Mat hierarchy = new Mat();
                List<MatOfPoint> contours = new ArrayList<>();
                List<MatOfPoint> contours2 = new ArrayList<>();
                Imgproc.findContours(tmp, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
                for ( int contourIdx=0; contourIdx < contours.size(); contourIdx++ )
                {
                    if(Imgproc.contourArea(contours.get(contourIdx))< 400 && Imgproc.contourArea(contours.get(contourIdx)) > 50){
                        contours2.add(contours.get(contourIdx));
                        num++;
                    }
                }
                Imgproc.drawContours(tmp, contours2, -1, new Scalar(255, 255, 0));*/