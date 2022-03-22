package com.example.rectangler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class GameOverActivity extends AppCompatActivity {

    private Button btnQuitGame, btnMainMenu, btnReplay;
    private TextView tvGameOver, tvScore;
    private Context context;
    private Resources resources;
    private String currentLanguageCode, filePath, fileName;
    private MediaPlayer music;
    private int volume, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        inicialize();

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(GameOverActivity.this, GameplayActivity.class);
                startActivity(intent);
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnQuitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(checkIfFileExists("config.txt")){
            if(readFile()){
                changeLocale();
            }
        }

        if(getIntent().getExtras() != null){

            score = getIntent().getIntExtra("score", 0);
            tvScore.append(Integer.toString(score));

        }

        music = MediaPlayer.create(GameOverActivity.this, R.raw.game_over);
        music.setLooping(false);
        music.setVolume(volume / 50.0f, volume / 50.0f);
        music.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        music.stop();
    }

    private void inicialize(){

        tvScore = (TextView)findViewById(R.id.tvScore);
        btnQuitGame = (Button) findViewById(R.id.btnQuitGame);
        btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
        tvGameOver = (TextView) findViewById(R.id.tvGameOver);
        btnReplay = (Button) findViewById(R.id.btnReplay);

    }

    private boolean readFile(){

        try{
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String fileText = bufferedReader.readLine();

            String[] settings = fileText.split(";");

            for(int i = 0; i < settings.length; i++){

                switch (i){
                    case 0:
                        currentLanguageCode = settings[i];
                        break;
                    case 1:
                        volume = Integer.parseInt(settings[i]);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }

            return true;
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkIfFileExists(String fileName){

        this.fileName = fileName;
        filePath = getApplicationContext().getFilesDir().getPath().toString() + "/" + fileName;

        File file = new File(filePath);
        if(file.exists())
            return true;

        return false;
    }

    private void changeLocale(){

        context = LocaleHelper.setLocale(GameOverActivity.this, currentLanguageCode);
        resources = context.getResources();
        changeLanguage();

    }

    private void changeLanguage(){

        tvGameOver.setText(resources.getString(R.string.game_over));
        btnQuitGame.setText(resources.getString(R.string.quit_button));
        btnMainMenu.setText(resources.getString(R.string.main_menu));
        btnReplay.setText(resources.getString(R.string.play_button));
        tvScore.setText(resources.getString(R.string.score));

    }
}