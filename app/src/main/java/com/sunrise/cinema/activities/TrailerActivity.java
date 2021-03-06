package com.sunrise.cinema.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sunrise.cinema.DB.Movie;
import com.sunrise.cinema.R;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class TrailerActivity extends AppCompatActivity {
    TextView description;
    ImageView afisha;
    VideoView video;
    String pathUrl;
    ImageButton button;
    Movie movie;
    int finalHeight, finalWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toolbar.setVisibility(View.GONE);
        }

        ((TextView) findViewById(R.id.toolbarText)).setText(getString(R.string.about));
        movie = (Movie) getIntent().getSerializableExtra("value");
        button = (ImageButton) findViewById(R.id.button);
        afisha = (ImageView) findViewById(R.id.image_for_afisha);
        video = (VideoView) findViewById(R.id.surface_view);
        description = (TextView) findViewById(R.id.text_description);
        pathUrl = movie.getTrailer();
//        Log.e("VITAMIO", pathUrl);
        if (!movie.getImageUrl().isEmpty()) {
            Picasso.with(this).load(movie.getImageUrl()).into(afisha);
        }
        if (!movie.getDescription().isEmpty()) {
            final ViewTreeObserver vto = afisha.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
//                    afisha.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    finalHeight = afisha.getMeasuredHeight();
                    finalWidth = afisha.getMeasuredWidth();
                    makeSpan();
                }
            });

        }
        if (!LibsChecker.checkVitamioLibs(this))
            return;


    }

    public void playVideo(View view) {
        //TODO remove videoview if trailer is not exist

        if (pathUrl == null) {
            Toast.makeText(this, "У этого фильма не трейлера", Toast.LENGTH_LONG).show();

        } else {
            //   connectDb();
            video.setVideoPath(pathUrl);
            video.setMediaController(new MediaController(this));
            video.requestFocus();
            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
            button.setVisibility(View.GONE);

        }
    }

    public void makeSpan() {
        String plainText = movie.getDescription();
        Spanned htmlText = Html.fromHtml(plainText);
        SpannableString mSpannableString = new SpannableString(htmlText);
        int allTextStart = 0;
        int allTextEnd = htmlText.length() - 1;
        int lines;
        Rect bounds = new Rect();
        description.getPaint().getTextBounds(plainText.substring(0, 10), 0, 1, bounds);
        float fontSpacing = description.getPaint().getFontSpacing();
        lines = (int) (finalHeight / (fontSpacing));
        MyLeadingMarginSpan span = new MyLeadingMarginSpan(lines, finalWidth + 10);
        mSpannableString.setSpan(span, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        description.setText(mSpannableString);
    }
}
