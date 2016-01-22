package com.example.ruslan.projectcinema.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruslan.projectcinema.DB.Movie;
import com.example.ruslan.projectcinema.R;
import com.squareup.picasso.Picasso;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Movie movie = (Movie) bundle.getSerializable("value");
        button = (ImageButton)findViewById(R.id.button);
        description=(TextView)findViewById(R.id.text_description);
        afisha = (ImageView)findViewById(R.id.image_for_afisha);
        video = (VideoView)findViewById(R.id.surface_view);
        pathUrl = movie.getTrailer();
        Log.e("VITAMIO",pathUrl);
        if (!movie.getImageUrl().isEmpty())
        Picasso.with(this).load(movie.getImageUrl()).into(afisha);
        if (!movie.getDescription().isEmpty())
        description.setText(movie.getDescription());
        if (!LibsChecker.checkVitamioLibs(this))
            return;




    }
    public void playVideo(View view)
    {

        if (pathUrl==null) {
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

}
