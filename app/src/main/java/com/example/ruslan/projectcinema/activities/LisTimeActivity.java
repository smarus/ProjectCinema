package com.example.ruslan.projectcinema.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.ruslan.projectcinema.Adapter.AdapterForList;
import com.example.ruslan.projectcinema.R;
import com.example.ruslan.projectcinema.activities.fragment.FirstFragment;
import com.example.ruslan.projectcinema.activities.fragment.PageFragment;
import com.example.ruslan.projectcinema.activities.fragment.ThirdFragment;
import com.example.ruslan.projectcinema.dbt.DbTimeHelper;
import com.example.ruslan.projectcinema.dbt.Manager;
import com.example.ruslan.projectcinema.dbt.TimeTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LisTimeActivity extends AppCompatActivity {
    DbTimeHelper dbTimeHelper;
    ListView listView;
    ViewPager pager;
    Date datefrom = new Date();
    long dateto = datefrom.getTime() + TimeUnit.DAYS.toMillis(3);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    String timetable ="http://cinema.su.kg/api/?token=24dDSFasdoj31adfwedf83213xxsfwed&object=timetable&action=list&date_from=+"+format.format(datefrom)+"%2000:00:00&&date_to="+format.format(dateto)+"%2000:00:00%22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis_time);
        dbTimeHelper = new DbTimeHelper(this);
        //listView = (ListView)findViewById(R.id.listView);
        Onclick();


    }

    public void Onclick() {
        new DownloadPageTask().execute(timetable);

    }

    private class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                String result = downloadOneUrl(urls[0]);
                Log.e("Oшибка", result);
                if (result != null)
                    try {
                        SQLiteDatabase db = dbTimeHelper.getWritableDatabase();
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("theatre_id");
                            String theatre_title = object.getString("theatre_title");
                            String hall_title = object.getString("hall_title");
                            String movie_title = object.getString("movie_title");
                            String price = object.getString("price_older");
                            String date = object.getString("session_time");
                            Manager.save(db, new TimeTable(hall_title, movie_title, theatre_title, date, price));
                        }
                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(),"JsonException",Toast.LENGTH_SHORT).show();

                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "true";
        }

        @Override
        protected void onPostExecute(String result) {
            SQLiteDatabase db = dbTimeHelper.getReadableDatabase();
            if (result.equals("true")) {
                pager = (ViewPager) findViewById(R.id.pager_for_fragment);
                String theatre = getIntent().getStringExtra("title");
                Log.e("tDLKDJGLKSDFJ",theatre);
                MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),theatre);
                pager.setAdapter(pagerAdapter);
//
            }

        }


    }

    private String downloadOneUrl(String myurl) throws IOException {
        InputStream inputstream = null;
        String data = "";
        try {
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                inputstream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                int read = 0;
                while ((read = inputstream.read()) != -1) {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();

                data = new String(result);

            } else {
                data = null;
            }
            connection.disconnect();
            //return data;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputstream != null) {
                inputstream.close();
            }
        }
        return data;
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        String theatre;

        public MyFragmentPagerAdapter(FragmentManager fm,String theatre)
        {
            super(fm);
            this.theatre = theatre;

        }

        @Override
        public Fragment getItem(int position) {
                Fragment fragment = null;
            switch (position)
            {
                case 0:
                {

                    fragment = new PageFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("theatre",theatre);
                    fragment.setArguments(arguments);
                    break;
                }
                case 1:
                {
                    fragment = new FirstFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("theatre",theatre);
                    fragment.setArguments(arguments);
                    break;
                }
                case 2:
                {
                    fragment = new ThirdFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("theatre",theatre);
                    fragment.setArguments(arguments);
                    break;
                }
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int position) {
            long date=0;
            switch (position) {
                case 0:
                    date =datefrom.getTime();
                break;
                case 1:
                     date=datefrom.getTime() + (24 * 60 * 60 * 1000);
                break;
                case 2:
                     date=datefrom.getTime() +( 24 * 60 * 60 * 1000*2);
                break;

            }
            return format.format(date);
        }
    }}





