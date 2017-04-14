package com.example.gil.which;

/**
 * Created by gil on 14/04/17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gil.which.adapters.MySimpleAdapter;
import com.example.gil.which.helpers.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Tab0Home extends Fragment {

    private ListView lv;
    List<Map<String, Object>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab0home, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);

        data = new ArrayList<Map<String, Object>>();
        lv = (ListView) getView().findViewById(R.id.listView1);
        new GetPosts().execute();

    }

    private class GetPosts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://192.168.1.13:8000/lincoln_post.json";
            String jsonStr = sh.makeServiceCall(url);


            Log.d("bla", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Map<String, Object> post = PostJSONtoMap(jsonObj);

                data.add(post);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            MySimpleAdapter adapter = new MySimpleAdapter(getActivity(), data,
                    R.layout.row, new String[] {}, new int[] {});
            lv.setAdapter(adapter);
        }

        protected Map<String, Object> PostJSONtoMap(JSONObject jsonObj){

            Map<String, Object> post = null;
            try {
                // Getting JSON user object
                JSONObject user = jsonObj.getJSONObject("user");
                String userPhoto = user.getString("userphoto");
                String userName = user.getString("username");

                // Getting JSON date
                String date = jsonObj.getString("date");
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
            return post;
        }
    }


}
