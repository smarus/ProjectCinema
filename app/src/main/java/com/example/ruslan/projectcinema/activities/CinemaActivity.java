package com.example.ruslan.projectcinema.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ruslan.projectcinema.R;

public class CinemaActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alatoo: {
                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "алатоо");
                startActivity(intent);
                break;
            }
            case R.id.btn_manas: {
                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "манас");
                startActivity(intent);
                break;
            }
            case R.id.btn_cosmo: {
                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "космопарк");
                startActivity(intent);
                break;
            }
            case R.id.btn_bishpark: {
                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "бишкекпарк");
                startActivity(intent);
                break;
            }
            case R.id.btn_okt: {

                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "октябрь");
                startActivity(intent);
                break;
            }
            case R.id.btn_vefa: {
                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "вефа");
                startActivity(intent);
                break;
            }
            case R.id.btn_russia: {
                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "россия");
                startActivity(intent);
                break;
            }
            case R.id.btn_kyrgyz: {
                intent = new Intent(getApplicationContext(), LisTimeActivity.class);
                intent.putExtra("title", "кыргыз киносу");
                startActivity(intent);
                break;
            }


        }
    }

}
