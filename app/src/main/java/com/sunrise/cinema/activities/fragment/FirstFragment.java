package com.sunrise.cinema.activities.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sunrise.cinema.Adapter.AdapterForList;
import com.sunrise.cinema.DB.DbHelper;
import com.sunrise.cinema.DB.Manager;
import com.sunrise.cinema.DB.Movie;
import com.sunrise.cinema.R;
import com.sunrise.cinema.activities.TrailerActivity;
import com.sunrise.cinema.dbt.DbTimeHelper;
import com.sunrise.cinema.dbt.TimeTable;
import com.sunrise.cinema.dbt.TimetableHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by ruslan on 19.01.16.
 */
public class FirstFragment extends Fragment {
    ListView listView;
    Date date;
    long dt=0;
    SimpleDateFormat format;
    DbTimeHelper dbTimeHelper;
    String theatre;
    DbHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        date =new Date();
        dt = date.getTime()+TimeUnit.DAYS.toMillis(1);
         format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dbTimeHelper = new DbTimeHelper(getContext());
        dbHelper = new DbHelper(getContext());

        Random rnd = new Random();
        theatre = getArguments().getString("theatre");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);


        listView = (ListView) view.findViewById(R.id.listview);
        SQLiteDatabase db = dbTimeHelper.getWritableDatabase();
        SQLiteDatabase db2 = dbHelper.getWritableDatabase();
        final ArrayList<Movie> movies = Manager.read(db2);
        ArrayList<TimeTable> list1 = com.sunrise.cinema.dbt.Manager.read(db);
        ArrayList<TimetableHelper> list = new ArrayList<>();
        for (TimeTable timeTable : list1) {
            String[] date1 = timeTable.getDate().split(" ");
             if (date1[0].equals(format.format(dt))&&theatre.equals(timeTable.getTheatre()))
                 list.add(new TimetableHelper(timeTable.getDate(),timeTable.getTitle(),timeTable.getHall(),timeTable.getTheatre(),timeTable.getPrice(),timeTable.getCodeid()));
        }
        final AdapterForList adapterForList = new AdapterForList(view.getContext(), list);
        listView.setAdapter(adapterForList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimetableHelper time = (TimetableHelper) adapterForList.getItem(position);
                for (Movie movie : movies) {
                    if (time.getCoideid().equals(movie.getMovieid())) {
                        Intent intent = new Intent(getContext(), TrailerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("value", movie);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });


        return view;

    }
}
