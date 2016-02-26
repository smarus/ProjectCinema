package com.example.ruslan.projectcinema.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruslan.projectcinema.R;
import com.example.ruslan.projectcinema.dbt.TimeTable;
import com.example.ruslan.projectcinema.dbt.TimetableHelper;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ruslan on 13.01.16.
 */
public class AdapterForList extends BaseAdapter {
    Context context;
    ArrayList<TimetableHelper> list;
    private LayoutInflater layoutInflater;

    public AdapterForList(Context context, ArrayList<TimetableHelper> list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder = new Holder();
        if (view == null) {
            view = layoutInflater.inflate(R.layout.forbaseadapter, parent, false);
        }
        //holder.text_theatre = (TextView)view.findViewById(R.id.for_theatre);
        holder.text_time = (TextView) view.findViewById(R.id.for_time);
        holder.text_title = (TextView) view.findViewById(R.id.for_hall);
        holder.text_film = (TextView) view.findViewById(R.id.for_name);
        holder.text_price = (TextView) view.findViewById(R.id.for_price);
        //   holder.textView = (ShimmerTextView)view.findViewById(R.id.for_theatre);

        //  holder.text_theatre.setText(list.get(position).getTheatre());
        holder.text_time.setText(list.get(position).getDateTime().split(" ")[1].substring(0, 5));
        holder.text_title.setText(list.get(position).getHall());
        holder.text_film.setText(list.get(position).getMovie());
        holder.text_price.setText(list.get(position).getPrice());
        //      Shimmer shimmer = new Shimmer();
//        shimmer.start(holder.textView);

        return view;
    }

    public class Holder {
        TextView text_price, text_title, text_theatre, text_time, text_film;
    }
}
