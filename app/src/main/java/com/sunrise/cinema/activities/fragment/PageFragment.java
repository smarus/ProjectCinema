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

/**
 * Created by ruslan on 16.01.16.
 */
public class PageFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    Date date;
    SimpleDateFormat format;
    int backColor;
    ListView listView;
    DbTimeHelper dbTimeHelper;
    DbHelper dbHelper;
    String theatre;

    public static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = new Date();
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dbTimeHelper = new DbTimeHelper(getContext());
        dbHelper = new DbHelper(getContext());
        theatre = getArguments().getString("theatre");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);


        listView = (ListView) view.findViewById(R.id.listview);
        SQLiteDatabase db = dbTimeHelper.getWritableDatabase();
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        final ArrayList<Movie> movies = Manager.read(db1);
        final ArrayList<TimeTable> list1 = com.sunrise.cinema.dbt.Manager.read(db);
        ArrayList<TimetableHelper> list = new ArrayList<>();
        for (TimeTable timeTable : list1) {
            String[] date1 = timeTable.getDate().split(" ");
            if (date1[0].equals(format.format(date).toString()) && theatre.equals(timeTable.getTheatre())) {
                list.add(new TimetableHelper(timeTable.getDate(), timeTable.getTitle(), timeTable.getHall(), timeTable.getTheatre(), timeTable.getPrice(), timeTable.getCodeid()));
            }
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

