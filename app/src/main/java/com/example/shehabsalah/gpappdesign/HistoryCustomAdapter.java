package com.example.shehabsalah.gpappdesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Shehab on 4/1/2016.
 */
public class HistoryCustomAdapter extends BaseAdapter {
    private JSONArray historyArray;
    private Context activity;
    private static LayoutInflater inflater = null;

    public ImageLoader imageLoader;
    private static final String imgDir = "http://www.platformhouse.com/e2ralyMobApp/images/";
    ListCell cell;
    public HistoryCustomAdapter(Context context, JSONArray jsonArray){
        /*super(context, R.layout.post);*/
        this.historyArray = jsonArray;
        this.activity = context;
        imageLoader = new ImageLoader(activity);
    }
    public void setNewHistoryCustomAdapter(Context context, JSONArray jsonArray){
        this.historyArray = jsonArray;
        this.activity = context;
        imageLoader = new ImageLoader(activity);
    }
    @Override
    public int getCount() {
        if(historyArray != null)
            return historyArray.length() > 0?historyArray.length():0;
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject jsonObject = null;
        View row = convertView;

        if (row == null){
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.history_list,parent,false);
            cell = new ListCell();
            cell.text_recognized = (TextView) row.findViewById(R.id.text_recognized);
            cell.his_image = (ImageView) row.findViewById(R.id.his_image);
            row.setTag(cell);
        }else{
            cell = (ListCell) row.getTag();
        }
        try{
            jsonObject = this.historyArray.getJSONObject(position);
            String temp = jsonObject.getString("_text_recognized");
            char[] charTemp;
            if(temp.length() > 24){
                charTemp = temp.toCharArray();
                temp = "";
                for(int i = 0; i < 25; i++){
                    temp+=charTemp[i];
                }
                temp+="...";
            }
                cell.text_recognized.setText(new String(temp.getBytes()));

            ImageView profile = cell.his_image;
            imageLoader.DisplayImage(imgDir + jsonObject.getString("_image_org"),profile);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return row;
    }

    private class ListCell{
        private TextView text_recognized;
        private ImageView his_image;
    }
}