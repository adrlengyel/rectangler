package com.example.rectangler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class OptionsActivity extends AppCompatActivity {

    private TextView tvOptions, tvVolume, tvLanguage;
    private Button btnSave;
    private RadioButton rbSlovak, rbEnglish;
    private RadioGroup rgLanguage;
    private String currentLanguageCode;
    private String fileName;
    private String filePath;
    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        inicializeViews();
        checkSave();

        if(rgLanguage.getCheckedRadioButtonId() == R.id.rbSlovak)
            context = LocaleHelper.setLocale(OptionsActivity.this, "sk");
        else
            context = LocaleHelper.setLocale(OptionsActivity.this, "en");

        resources = context.getResources();
        changeLanguage();

        rbSlovak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLanguageCode = "sk";
                context = LocaleHelper.setLocale(OptionsActivity.this, "sk");
                resources = context.getResources();
                changeLanguage();
            }
        });

        rbEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLanguageCode = "en";
                context = LocaleHelper.setLocale(OptionsActivity.this, "en");
                resources = context.getResources();
                changeLanguage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void checkSave(){

        if(checkIfFileExists("config.txt")){
            if(readFile())
                loadSave();
        }
        else{
            checkRadioButton();
        }

    }

    private void loadSave(){

        if(currentLanguageCode.equals("sk")){
            rgLanguage.check(R.id.rbSlovak);
        }
        else {
            rgLanguage.check(R.id.rbEnglish);
        }

    }

    private void saveSettings(){

        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(getApplicationContext().openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(currentLanguageCode);
            outputStreamWriter.close();

        }
        catch (IOException e) {
            Toast.makeText(OptionsActivity.this, "", Toast.LENGTH_SHORT).show();
        }
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

    private void checkRadioButton() {

        String country = Locale.getDefault().getCountry();
        country = country.toLowerCase();

        if(country.equals("sk")){
            rgLanguage.check(R.id.rbSlovak);
            currentLanguageCode = "sk";
        }
        else {
            rgLanguage.check(R.id.rbEnglish);
            currentLanguageCode = "en";
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

    private void inicializeViews(){

        btnSave = (Button) findViewById(R.id.btnSave);
        rgLanguage = (RadioGroup) findViewById(R.id.rgLanguage);
        rbSlovak = (RadioButton) findViewById(R.id.rbSlovak);
        rbEnglish = (RadioButton) findViewById(R.id.rbEnglish);
        tvOptions = (TextView) findViewById(R.id.tvOptions);
        tvVolume = (TextView) findViewById(R.id.tvVolume);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);

    }

    private void changeLanguage(){

        tvOptions.setText(resources.getString(R.string.options_button));
        tvVolume.setText(resources.getString(R.string.volume));
        tvLanguage.setText(resources.getString(R.string.language));
        rbEnglish.setText(resources.getString(R.string.english));
        rbSlovak.setText(resources.getString(R.string.slovak));
        btnSave.setText(resources.getString(R.string.save));

    }
}