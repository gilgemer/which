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

//        itemList = new ArrayList<>();
//        lv = (ListView) findViewById(R.id.list);
//
        data = new ArrayList<Map<String, Object>>();
        lv = (ListView) findViewById(R.id.listView1);
        new GetPosts().execute();

//        Map<String, Object> map = new HashMap<String, Object>();
//
//        map.put("userphoto", "http://images2.fanpop.com/images/photos/3100000/mike-michael-scofield-3137164-346-500.jpg");
//        map.put("username", "Michael Scofield");
//        map.put("choice1", "https://s-media-cache-ak0.pinimg.com/736x/34/9b/27/349b27628868842c346cd385de28b0f2.jpg");
//        map.put("choice2", "http://s-media-cache-ak0.pinimg.com/564x/1d/9e/e6/1d9ee66aebc07c455a341a5a1252d74d.jpg");
//        map.put("title1", "Winter");
//        map.put("title2", "Summer");
//        map.put("timestamp", "About an hour ago");
//
//        data.add(map);
//        MySimpleAdapter adapter = new MySimpleAdapter(MainActivity.this, data,
//                R.layout.row, new String[] {}, new int[] {});
//        lv.setAdapter(adapter);
//    }
    }
    private class GetPosts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            //HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "http://0.0.0.0:8000/post.json";
            //String jsonStr = sh.makeServiceCall(url);


            String jsonStr = "{  \"date\": \"About an hour ago\",  \"user\": {    \"userphoto\": \"http://www.konbini.com/wp-content/blogs.dir/3/files/2015/08/ok-810x492.jpg\",    \"username\": \"Michael Scofield\"  },  \"choice1\": {    \"image\": \"https://s-media-cache-ak0.pinimg.com/736x/34/9b/27/349b27628868842c346cd385de28b0f2.jpg\",    \"title\": \"winter\"  },  \"choice2\": {    \"image\": \"http://s-media-cache-ak0.pinimg.com/564x/1d/9e/e6/1d9ee66aebc07c455a341a5a1252d74d.jpg\",    \"title\": \"summer\"  }}";

            Log.d("bla", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON user object
                    JSONObject user = jsonObj.getJSONObject("user");
                    String userPhoto = user.getString("userphoto");
                    String userName = user.getString("username");

                    // Getting JSON date
                    String date = jsonObj.getString("date");
                    // Parsing the date
                    // MM/dd/yy     HH:mm:ss
                    // Just a test for a future possibility of using a more complex date format
//                    SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy     HH:mm:ss");
//                    try {
//                        Date _date = parser.parse(date);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                    String formattedDate = formatter.format(date);
                    String formattedDate = date;
                    // Getting the first choice object
                    JSONObject firstChoice = jsonObj.getJSONObject("choice1");
                    String image1 = firstChoice.getString("image");
                    String title1 = firstChoice.getString("title");

                    // Getting the second choice object
                    JSONObject secondChoice = jsonObj.getJSONObject("choice2");
                    String image2 = secondChoice.getString("image");
                    String title2 = secondChoice.getString("title");


                    Map<String, Object> post = new HashMap<String, Object>();

                    // adding each child node to HashMap key => value
                    post.put("userphoto", userPhoto);
                    post.put("username", userName);
                    post.put("timestamp", formattedDate);
                    post.put("choice1", image1);
                    post.put("title1", title1);
                    post.put("choice2", image2);
                    post.put("title2", title2);


                    data.add(post);


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

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            MySimpleAdapter adapter = new MySimpleAdapter(MainActivity.this, data,
                    R.layout.row, new String[] {}, new int[] {});
            lv.setAdapter(adapter);
        }
    }
}