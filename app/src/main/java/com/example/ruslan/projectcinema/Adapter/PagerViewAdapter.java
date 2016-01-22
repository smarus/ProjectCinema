package com.example.ruslan.projectcinema.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruslan.projectcinema.DB.Movie;
import com.example.ruslan.projectcinema.R;
import com.example.ruslan.projectcinema.activities.TrailerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ruslan on 13.01.16.
 */
public class PagerViewAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Movie> list;

    public PagerViewAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }


    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }


    public Object instantiateItem(ViewGroup container, final int position) {
        Log.e("POSITION",position+"");
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pager, container, false);
        Holder holder = new Holder();
        holder.text_name = (TextView) view.findViewById(R.id.name_film);
        holder.avatat_film = (ImageView) view.findViewById(R.id.image_view);
        holder.text_name.setText(list.get(position).getTitle());
        if (!list.get(position).getImageUrl().isEmpty()) {
            Picasso.with(context).load(list.get(position).getImageUrl()).into(holder.avatat_film);
            Toast.makeText(context,list.get(position).getTitle()+"",Toast.LENGTH_SHORT);
        }
        else {

        }
        holder.avatat_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", list.get(position));
                Intent intent = new Intent(context, TrailerActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
        ((ViewPager) container).addView(view);
        return view;
    }

    public class Holder {
        TextView text_name;
        ImageView avatat_film;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
