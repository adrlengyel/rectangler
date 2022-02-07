package com.example.rectangler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity{

    private TextView tvLoadingTitle, tvUnderLogo;
    private ImageView imgLoadingLogo;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        inicialize();

        music = MediaPlayer.create(LoadingActivity.this, R.raw.loading_song);
        music.setVolume(50, 50);

        Animation animation = AnimationUtils.loadAnimation(LoadingActivity.this, R.anim.fade_in);
        tvLoadingTitle.setAnimation(animation);
        imgLoadingLogo.setAnimation(animation);
        tvUnderLogo.setAnimation(animation);

        Handler animHandler = new Handler();
        animHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(LoadingActivity.this, R.anim.fade_out);
                tvLoadingTitle.setAnimation(animation);
                imgLoadingLogo.setAnimation(animation);
                tvUnderLogo.setAnimation(animation);
            }

        },3500);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }, music.getDuration() + 1000);
    }

    private void inicialize(){

        tvLoadingTitle = (TextView) findViewById(R.id.tvLoadingTitle);
        imgLoadingLogo = (ImageView) findViewById(R.id.imgLoadingLogo);
        tvUnderLogo = (TextView) findViewById(R.id.tvUnderLogo);

    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }
}