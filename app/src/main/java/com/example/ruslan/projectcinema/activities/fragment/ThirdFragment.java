package com.example.ruslan.projectcinema.activities.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ruslan.projectcinema.Adapter.AdapterForList;
import com.example.ruslan.projectcinema.R;
import com.example.ruslan.projectcinema.dbt.DbTimeHelper;
import com.example.ruslan.projectcinema.dbt.TimeTable;
import com.example.ruslan.projectcinema.dbt.TimetableHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by ruslan on 20.01.16.
 */
public class ThirdFragment extends Fragment { ListView listView;
    Date date;SimpleDateFormat format;
    long dt=0;
    DbTimeHelper dbTimeHelper;
    String theatre;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        date =new Date();
        dt = date.getTime()+TimeUnit.DAYS.toMillis(2);
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dbTimeHelper = new DbTimeHelper(getContext());
        Random rnd = new Random();
        theatre = getArguments().getString("theatre");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);


        listView = (ListView) view.findViewById(R.id.listview);
        SQLiteDatabase db = dbTimeHelper.getWritableDatabase();

        ArrayList<TimeTable> list1 = com.example.ruslan.projectcinema.dbt.Manager.read(db);
        ArrayList<TimetableHelper> list = new ArrayList<>();
        for (TimeTable timeTable : list1) {
            String[] date1 = timeTable.getDate().split(" ");
            if (date1[0].equals(format.format(dt))&&theatre.equals(timeTable.getTheatre()))
                list.add(new TimetableHelper(timeTable.getDate(),timeTable.getTitle(),timeTable.getHall(),timeTable.getTheatre(),timeTable.getPrice()));
        }
        AdapterForList adapterForList = new AdapterForList(view.getContext(), list);
        listView.setAdapter(adapterForList);

        return view;

    }
}


