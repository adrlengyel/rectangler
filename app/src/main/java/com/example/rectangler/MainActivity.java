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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay, btnOptions, btnQuit, btnCredits;
    private TextView tvTitle;
    private String currentLanguageCode, filePath, fileName;
    private Context context;
    private Resources resources;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicialize();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.stop();
                Intent intent = new Intent(MainActivity.this, GameplayActivity.class);
                startActivity(intent);
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        btnCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });

        music = MediaPlayer.create(MainActivity.this, R.raw.mainmenu_song);
        music.setVolume(100, 100);
        music.setLooping(true);
        music.start();
    }

    private void inicialize(){

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnOptions = (Button) findViewById(R.id.btnOptions);
        btnCredits = (Button) findViewById(R.id.btnCredits);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

    }

    private boolean readFile(){

        try{
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String fileText = bufferedReader.readLine();

            currentLanguageCode = fileText;
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

        context = LocaleHelper.setLocale(MainActivity.this, currentLanguageCode);
        resources = context.getResources();
        changeLanguage();

    }

    private void changeLanguage(){

        tvTitle.setText(resources.getString(R.string.app_name));
        btnPlay.setText(resources.getString(R.string.play_button));
        btnCredits.setText(resources.getString(R.string.credits_button));
        btnOptions.setText(resources.getString(R.string.options_button));
        btnQuit.setText(resources.getString(R.string.quit_button));

    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
        if(checkIfFileExists("config.txt")){
            if(readFile()){
                changeLocale();
            }
        }
    }

}