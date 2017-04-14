package com.example.gil.which;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
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
=======
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final int[] ICONS = new int[]{
                R.drawable.home,
                R.drawable.clock,
                R.drawable.notif,
                R.drawable.add,
        };
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return new Tab0Home();
                case 1:
                    return new Tab1Timeline();
                case 2:
                    return new Tab2Notifications();
                case 3:
                    return new Tab3Add();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

>>>>>>> gil
    }
}
