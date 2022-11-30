package com.example.climatechangesanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActinometricActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButtonAnalyze;
    private EditText mFirstDate, mSecondDate;
    private String mSendCityName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteological);

        mListView = findViewById(R.id.listView);
        mButtonAnalyze = findViewById(R.id.buttonAnalyse);

        mFirstDate = findViewById(R.id.editTextFirstYear);
        mSecondDate = findViewById(R.id.editTextSecondYear);

        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Łódź");
        arrayList.add("Łeba");
        arrayList.add("Śnieżka");
        arrayList.add("Bielsko-Biała");
        arrayList.add("Gdynia");
        arrayList.add("Gorzów Wielkopolski");
        //arrayList.add("Grabów");
        //arrayList.add("Jarczew");
        arrayList.add("Jelenia Góra");
        //arrayList.add("Kłodzko");
        arrayList.add("Kasprowy Wierch");
        arrayList.add("Kołobrzeg");
        arrayList.add("Warszawa Bielany");
        //arrayList.add("Legionowo");
        arrayList.add("Legnica");
        //arrayList.add("Lesko");
        //arrayList.add("Mikołajki");
        //arrayList.add("Piła");
        //arrayList.add("Puławy");
        //arrayList.add("Sulejów");
        //arrayList.add("Suwałki");
        arrayList.add("Toruń");
        arrayList.add("Wieluń");
        arrayList.add("Zakopane");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), arrayList.get(position), Toast.LENGTH_LONG).show();
                mButtonAnalyze.setText("Wykonaj analizę dla " + arrayList.get(position));
                mSendCityName = arrayList.get(position);
            }
        });

        mButtonAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateInput()) {
                    startActivity();
                }
            }
        });

    }

    public boolean updateInput() {
        if (Integer.parseInt(mFirstDate.getText().toString()) < 1950 || Integer.parseInt(mFirstDate.getText().toString()) > 2022) {
            Toast.makeText(this, "Zakres lat to 1950 - 2022. Proszę wpisać datę z zakresu", Toast.LENGTH_LONG).show();
            mFirstDate.setText("1950");
            return false;
        }

        if (Integer.parseInt(mSecondDate.getText().toString()) < 1950 || Integer.parseInt(mSecondDate.getText().toString()) > 2022) {
            Toast.makeText(this, "Zakres lat to 1950 - 2022. Proszę wpisać datę z zakresu", Toast.LENGTH_LONG).show();
            mSecondDate.setText("2020");
            return false;
        }

        return true;
    }


    public void startActivity() {
        Intent intent = new Intent(this, AnalyzeActivity.class);
        intent.putExtra("keyCityName", mSendCityName);
        intent.putExtra("keyFirstDate", mFirstDate.getText().toString());
        intent.putExtra("keySecondDate", mSecondDate.getText().toString());
        startActivity(intent);
    }

}