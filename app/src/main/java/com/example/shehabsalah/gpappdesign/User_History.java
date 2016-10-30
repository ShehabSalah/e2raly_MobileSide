package com.example.shehabsalah.gpappdesign;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class User_History extends Activity {
    HistoryCustomAdapter adapter;
    static JSONArray jsonArray;
    ImageView internet;
    ListView historyList;
    Button back;
    Button Log_out;
    int old_limit;
    public static int limit;
    static int deletePosition;
    int item_id;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__history);
        internet = (ImageView)findViewById(R.id.NInternet);
        internet.setImageResource(R.mipmap.no_conection);
        internet.setVisibility(View.INVISIBLE);
        Log_out = (Button)findViewById(R.id.Log_out);
        historyList = (ListView)findViewById(R.id.historyList);
        limit = 20;
        old_limit = 0;
        back = (Button)findViewById(R.id.dummy_button);
        if(LoginActivity.user_id == null){
            Intent i = new Intent(User_History.this,LoginActivity.class);
            startActivity(i);
            if(LoginActivity.user_id == null)
                finish();
        }
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        historyList.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (old_limit != scrollState) {
                    if (scrollState >= limit - 1) {
                        old_limit = scrollState;
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            internet.setVisibility(View.INVISIBLE);
                            limit += 20;
                            Log.i("ApiConnector is Called", "" + scrollState);
                            new GetHistory().execute(new GetUserHistory());
                        } else {
                            internet.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

        historyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deletePosition = position;
                showDialog();
                return true;
            }
        });
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deletePosition = position;
                Intent i = new Intent(User_History.this, SingleHistoryItem.class);
                startActivity(i);
            }
        });
        Log_out.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }
    private void logOut(){
        File root = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "e2raly");
        if (!root.exists()) {
            root.mkdirs();
        }
        File gpxfile = new File(root, "Config.txt");
        gpxfile.delete();
        LoginActivity.user_id = null;
        finish();
    }
    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(User_History.this);
        alert.setTitle("Delete History Item");
        alert.setMessage("Do you want to delete this history item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //go and delete or report this comment
                Toast.makeText(User_History.this, "This history item has been deleted.", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject customerClicked = jsonArray.getJSONObject(deletePosition);
                    item_id = customerClicked.getInt("_history_id");
                    new DeleteHisoryItem().execute(new GetUserHistory());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        alertDialog = alert.create();
        alertDialog.show();
    }
    protected void onResume() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        limit = 20;
        old_limit = 0;
        if (networkInfo != null && networkInfo.isConnected()) {
            internet.setVisibility(View.INVISIBLE);
            new GetHistory().execute(new GetUserHistory());
        }else{
            internet.setVisibility(View.VISIBLE);
        }
        super.onResume();

    }
    public void setListAdapter(JSONArray jsonArray) throws JSONException {
        this.jsonArray = jsonArray;
        if(old_limit == 0)
        {
            adapter = new HistoryCustomAdapter(this,jsonArray);
            this.historyList.setAdapter(adapter);
        }
        else{
            adapter.setNewHistoryCustomAdapter(this, jsonArray);
            this.historyList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            this.historyList.setSelection(limit - 21);
        }
    }
    private class GetHistory extends AsyncTask<GetUserHistory,Long,JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(User_History.this);
            pDialog.setMessage("Loading Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            if(old_limit == 0)
                pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(GetUserHistory... params) {
            return params[0].getHistoryList(LoginActivity.user_id, limit);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            try {
                if(pDialog != null)
                    pDialog.dismiss();
                setListAdapter(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class DeleteHisoryItem extends AsyncTask<GetUserHistory,Long,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                new GetHistory().execute(new GetUserHistory());
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected Boolean doInBackground(GetUserHistory... params) {
            return params[0].deleteItem(item_id, getApplicationContext());
        }
    }
}