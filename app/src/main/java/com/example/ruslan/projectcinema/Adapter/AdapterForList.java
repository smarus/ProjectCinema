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

/**
 * Created by ruslan on 13.01.16.
 */
public class AdapterForList extends BaseAdapter {
    Context context;
    ArrayList<TimetableHelper> list;
    private LayoutInflater layoutInflater;
    public AdapterForList(Context context,ArrayList<TimetableHelper> list)
    {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View view =convertView;
        Holder holder = new Holder();
        if (view==null)
        {
            view = layoutInflater.inflate(R.layout.forbaseadapter,parent,false);
        }
        holder.text_date = (TextView)view.findViewById(R.id.for_date);
        holder.text_price = (TextView)view.findViewById(R.id.for_price);
        holder.text_title =(TextView)view.findViewById(R.id.for_film);
        holder.text_theatre = (TextView)view.findViewById(R.id.for_theatre);
        holder.text_date.setText(list.get(position).getDateTime());
        holder.text_price.setText(list.get(position).getPrice());
        holder.text_title.setText(list.get(position).getMovie());
        holder.text_theatre.setText(list.get(position).getTheatre());
        return view;
    }
    public  class Holder
    {
        TextView text_price,text_title,text_theatre,text_date;
    }
}
