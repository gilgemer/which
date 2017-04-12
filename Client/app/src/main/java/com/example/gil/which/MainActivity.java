package com.example.gil.which;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetPosts().execute();
    }

    private class GetPosts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            // String url = "http://api.androidhive.info/contacts/";
            // String jsonStr = sh.makeServiceCall(url);

            String jsonStr = "{  \"date\": \"4/12/17     16:49:17\",  \"user\": {    \"userphoto\": \"url\",    \"username\": \"Michael Scofield\"  },  \"choice1\": {    \"image\": \"url\",    \"title\": \"winter\"  },  \"choice2\": {    \"image\": \"url\",    \"title\": \"summer\"  }}";
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

                    // tmp hash map for single contact
                    HashMap<String, String> post = new HashMap<>();

                    // adding each child node to HashMap key => value
                    post.put("userPhoto", userPhoto);
                    post.put("userName", userName);
                    post.put("formattedDate", formattedDate);
                    post.put("image1", image1);
                    post.put("title1", title1);
                    post.put("image2", image2);
                    post.put("title2", title2);

                    // adding contact to contact list
                    itemList.add(post);

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
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, itemList,
                    R.layout.list_item, new String[]{ "userPhoto","userName", "formattedDate", "image1", "title1", "image2", "title2"},
                    new int[]{R.id.userPhoto, R.id.userName, R.id.formattedDate, R.id.image1, R.id.title1, R.id.image2, R.id.title2});
            lv.setAdapter(adapter);
        }
    }
}