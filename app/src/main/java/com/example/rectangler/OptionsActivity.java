package com.example.rectangler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

public class OptionsActivity extends AppCompatActivity {

    private Button btnSave;
    private RadioButton rbSlovak, rbEnglish;
    private RadioGroup rgLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        inicializeViews();

        checkRadioButton();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), getString(R.string.saved_text), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void checkRadioButton(){

        String country = Locale.getDefault().getCountry();
        country = country.toLowerCase();

        if(country.equals("sk")){
            rgLanguage.check(R.id.rbSlovak);
        }
        else {
            rgLanguage.check(R.id.rbEnglish);
        }

    }

    public void inicializeViews(){

        btnSave = (Button) findViewById(R.id.btnSave);
        rgLanguage = (RadioGroup) findViewById(R.id.rgLanguage);
        rbSlovak = (RadioButton) findViewById(R.id.rbSlovak);
        rbEnglish = (RadioButton) findViewById(R.id.rbEnglish);

    }
}