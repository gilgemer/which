package com.example.gil.which;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.gil.which.adapters.MySimpleAdapter;
import com.example.gil.which.helpers.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<Map<String, Object>>();
        lv = (ListView) findViewById(R.id.listView1);
        new GetPosts().execute();

    }

    private class GetPosts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            String [] JSONUrls = {"http://192.168.1.13:8000/michael_post.json", "http://192.168.1.13:8000/lincoln_post.json"};
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            for (String url : JSONUrls) {
                String jsonStr = sh.makeServiceCall(url);

                Log.d("bla", "Response from url: " + jsonStr);
                if (jsonStr != null) {

                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(jsonStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Map<String, Object> post = PostJSONToMap(jsonObj);

                    data.add(post);
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            MySimpleAdapter adapter = new MySimpleAdapter(MainActivity.this, data,
                    R.layout.row, new String[]{}, new int[]{});
            lv.setAdapter(adapter);
        }


        /**
         * @param jsonObj - jsonObj of a post
         * @return a new Map holding the keys and values of the JSON object (According to
         * the post pattern
         */
        protected Map<String, Object> PostJSONToMap(JSONObject jsonObj) {

            Map<String, Object> post = null;

            try {
                JSONObject user = jsonObj.getJSONObject("user");
                String userPhoto = user.getString("userphoto");
                String userName = user.getString("username");

                // Getting JSON date
                String date = jsonObj.getString("date");
                // In future, when a timestamp format is decided, the timestamp will be
                // parsed here
                String formattedDate = date;
                // Getting the first choice object
                JSONObject firstChoice = jsonObj.getJSONObject("choice1");
                String image1 = firstChoice.getString("image");
                String title1 = firstChoice.getString("title");

                // Getting the second choice object
                JSONObject secondChoice = jsonObj.getJSONObject("choice2");
                String image2 = secondChoice.getString("image");
                String title2 = secondChoice.getString("title");

                post = new HashMap<String, Object>();

                // adding each child node to HashMap key => value
                post.put("userphoto", userPhoto);
                post.put("username", userName);
                post.put("timestamp", formattedDate);
                post.put("choice1", image1);
                post.put("title1", title1);
                post.put("choice2", image2);
                post.put("title2", title2);

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return post;
        }
    }
}
