package com.example.rectangler;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class GameplayActivity extends AppCompatActivity {

    private String currentLanguageCode, filePath, fileName;
    private GameView gameView;
    private MediaPlayer music;
    private int volume;

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
        filePath = getApplicationContext().getFilesDir().getPath() + "/" + fileName;

        File file = new File(filePath);
        return file.exists();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkIfFileExists("config.txt")){
            if(!readFile()){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        gameView.resume();
        music = MediaPlayer.create(GameplayActivity.this, R.raw.background_song);
        music.setLooping(true);
        music.setVolume(volume / 50.0f, volume / 50.0f);
        music.start();
    }
}