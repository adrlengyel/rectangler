package com.example.rectangler;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class GameplayActivity extends AppCompatActivity {

    private GameView gameView;
    private MediaPlayer music;
    private int volume = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        gameView = new GameView(this, displayMetrics.widthPixels, displayMetrics.heightPixels);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        music.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        music = MediaPlayer.create(GameplayActivity.this, R.raw.background_song);
        music.setLooping(true);
        music.setVolume(volume / 50.0f, volume / 50.0f);
        music.start();
    }
}