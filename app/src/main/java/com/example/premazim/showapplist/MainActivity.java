package com.example.premazim.showapplist;

import java.util.*;
import java.io.IOException;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String msg = "Android : ";

    /*ArrayList mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};*/

    ArrayList mobileArray = new ArrayList<String>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(msg, "The onCreate() event");

        final TextView textView = findViewById(R.id.textView);

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://sogetiapi.com/appitecture/getAndroidApps")
                        .build();

                Response response = null;

                try{

                    response = client.newCall(request).execute();

                    String jsonData = response.body().string().toString();
                    Log.d(msg, jsonData);

                    try {
                        JSONObject Jobject = new JSONObject(jsonData);
                        JSONArray Jarray = Jobject.getJSONArray("apps");

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object     = Jarray.getJSONObject(i);

                            String titleTmp = object.getString("title");
                            Log.d(msg, titleTmp);

                            mobileArray.add(titleTmp);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    Log.d("in the APIcall: ", mobileArray.toString());

                    return mobileArray;

                }catch(IOException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                Log.d("in the array: ", mobileArray.toString());

                ArrayAdapter<String> adapter;
                adapter new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);

                ListView listView = (ListView) findViewById(R.id.mobile_list);
                listView.setAdapter(adapter);
            }
        }.execute();
    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
        /*stopService(new Intent(getBaseContext(), APIService.class));*/
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }
}
