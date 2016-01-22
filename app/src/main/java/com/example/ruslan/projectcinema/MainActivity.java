package com.example.ruslan.projectcinema;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ruslan.projectcinema.Adapter.PageTransformer;
import com.example.ruslan.projectcinema.Adapter.PagerViewAdapter;
import com.example.ruslan.projectcinema.DB.DbHelper;
import com.example.ruslan.projectcinema.DB.Manager;
import com.example.ruslan.projectcinema.DB.Movie;
import com.example.ruslan.projectcinema.activities.CinemaActivity;

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

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    LinearLayout up_line,down_line,progress_line;
    Button button;
    ViewPager viewPager;
    Date datefrom = new Date();
    long dateto = datefrom.getTime()+ TimeUnit.DAYS.toMillis(3);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    String movie="http://cinema.su.kg/api/?token=24dDSFasdoj31adfwedf83213xxsfwed&object=movie&action=list&date_from=2016-01-01%2000:00:00&&date_to=2016-01-21%2000:00:00%22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("DATE", format.format(datefrom));
        dbHelper = new DbHelper(this);
        long time = System.currentTimeMillis();
        long msDay = 24 * 60 * 60 * 1000;
        int days = (int) (time / msDay);
        button =(Button)findViewById(R.id.btn_cinema);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CinemaActivity.class);
                startActivity(intent);
            }
        });
        setDisplay();

        if (ConnectInet()) {
            update();
        } else {
            showWithoutInternet();
        }

    }

    public boolean ConnectInet() {
        ConnectivityManager myConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = myConnMgr.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnected();
    }
    public void setDisplay()
    {
        up_line = (LinearLayout)findViewById(R.id.up_line);
        progress_line = (LinearLayout)findViewById(R.id.liner_for_progress);
        down_line = (LinearLayout)findViewById(R.id.down_line);
        button = (Button)findViewById(R.id.btn_cinema);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int weight = displayMetrics.widthPixels;
        up_line.setLayoutParams(new LinearLayout.LayoutParams(weight, (int) (height * 0.4)));
    }
    public void update() {
        setDisplay();
        new DownloadUrl().execute(movie);
    }

    public void showWithoutInternet() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Movie> movies = Manager.read(db);


        PagerViewAdapter pagerViewAdapter = new PagerViewAdapter(getApplicationContext(),movies);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setPageTransformer(true, new PageTransformer());
        viewPager.setAdapter(pagerViewAdapter);
        up_line.setVisibility(View.VISIBLE);
        down_line.setVisibility(View.VISIBLE);
        progress_line.setVisibility(View.INVISIBLE);
    }

    public  class DownloadUrl extends AsyncTask<String, Void, String> {
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
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String title = object.getString("title");
                            String description = object.getString("description");
                            String image_url = object.getString("image");
                            String trailer = object.getString("trailer");
                            Manager.save(db, new Movie(title, description, image_url, trailer));
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
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            ArrayList<Movie> movies = Manager.read(db);


            PagerViewAdapter pagerViewAdapter = new PagerViewAdapter(getApplicationContext(),movies);
            viewPager = (ViewPager) findViewById(R.id.pager);
            viewPager.setPageTransformer(true, new PageTransformer());
            viewPager.setAdapter(pagerViewAdapter);
            up_line.setVisibility(View.VISIBLE);
            down_line.setVisibility(View.VISIBLE);
            progress_line.setVisibility(View.INVISIBLE);



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
    }

}