package com.sunrise.cinema.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.sunrise.cinema.R;
import com.sunrise.cinema.activities.fragment.FirstFragment;
import com.sunrise.cinema.activities.fragment.PageFragment;
import com.sunrise.cinema.activities.fragment.ThirdFragment;
import com.sunrise.cinema.dbt.DbTimeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LisTimeActivity extends AppCompatActivity {
//    DbTimeHelper dbTimeHelper;
    ViewPager pager;
    Date datefrom = new Date();
    long dateto = datefrom.getTime() + TimeUnit.DAYS.toMillis(3);
    SimpleDateFormat format = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
    String timetable = "http://cinema.su.kg/api/?token=24dDSFasdoj31adfwedf83213xxsfwed&object=timetable&action=list&date_from=+" + format.format(datefrom) + "%2000:00:00&&date_to=" + format.format(dateto) + "%2000:00:00%22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis_time);
        ((TextView) findViewById(R.id.toolbarText)).setText(getIntent().getStringExtra("title").toUpperCase());
//        dbTimeHelper = new DbTimeHelper(this);
        //listView = (ListView)findViewById(R.id.listView);
        Onclick();

    }
    public void Onclick() {
        new DownloadPageTask().execute(timetable);
    }

    private class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(String... urls) {
            return "true";
        }
        @Override
        protected void onPostExecute(String result) {
//            SQLiteDatabase db = dbTimeHelper.getReadableDatabase();
            if (result.equals("true")) {
                pager = (ViewPager) findViewById(R.id.pager_for_fragment);
                String theatre = getIntent().getStringExtra("title");
//                Log.e("tDLKDJGLKSDFJ", theatre);
                MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), theatre);
                pager.setAdapter(pagerAdapter);
            }
        }
    }
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        String theatre;

        public MyFragmentPagerAdapter(FragmentManager fm, String theatre) {
            super(fm);
            this.theatre = theatre;
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: {
                    fragment = new PageFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("theatre", theatre);
                    fragment.setArguments(arguments);
                    break;
                }
                case 1: {
                    fragment = new FirstFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("theatre", theatre);
                    fragment.setArguments(arguments);
                    break;
                }
                case 2: {
                    fragment = new ThirdFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("theatre", theatre);
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
            long date = 0;
            switch (position) {
                case 0:
                    date = datefrom.getTime();
                    break;
                case 1:
                    date = datefrom.getTime() + (24 * 60 * 60 * 1000);
                    break;
                case 2:
                    date = datefrom.getTime() + (24 * 60 * 60 * 1000 * 2);
                    break;
            }
            return format.format(date);
        }
    }
}





