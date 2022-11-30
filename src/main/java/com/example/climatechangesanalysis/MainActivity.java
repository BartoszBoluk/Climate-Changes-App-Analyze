package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButtonAbout, mButtonMeteo, mButtonAcro, mButtonHydro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonAbout = findViewById(R.id.buttonAbout);
        mButtonMeteo = findViewById(R.id.buttonMeteo);
        mButtonAcro = findViewById(R.id.buttonAcro);
        mButtonHydro = findViewById(R.id.buttonHydro);

        mButtonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(view, ActivityAboutProgram.class);
            }
        });

        mButtonMeteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(view, MeteologicalActivity.class);
            }
        });

        mButtonAcro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(view, AcroActivity.class);
            }
        });

        mButtonHydro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(view, HydroActivity.class);
            }
        });
    }

    public void startActivity(View view, Class directed_class) {
        Intent intent = new Intent(this, directed_class);
        startActivity(intent);
    }
}