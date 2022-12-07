package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HydroActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButtonAnalyze;
    private EditText mFirstDate, mSecondDate;
    private String mSendCityName = null;
    private boolean mPickedCity = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydro);

        mListView = findViewById(R.id.listViewHydro);
        mButtonAnalyze = findViewById(R.id.buttonAnalyseHydro);
        mFirstDate = findViewById(R.id.editTextFirstYearHydro);
        mSecondDate = findViewById(R.id.editTextSecondYearHydro);

        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Warszawa-Czajka");
        arrayList.add("Warszowice");
        arrayList.add("Wisła Wielka");
        arrayList.add("Goczałkowice-Zdrój");
        arrayList.add("Wisła-Malinka");
        arrayList.add("Szczyrk");
        arrayList.add("Wadowice");
        arrayList.add("Świdnik");
        arrayList.add("Huta");
        arrayList.add("Białka Tatrzańska");
        arrayList.add("Pilzno");
        arrayList.add("Sabótka");
        arrayList.add("Kamienna Góra");
        arrayList.add("Wałbrzych");
        arrayList.add("Oława");
        arrayList.add("Brzeg");
        arrayList.add("Starogard Gdański");
        arrayList.add("Wrocław-Stabłowice");
        arrayList.add("Szczecin-Pogodno");

//        Collections.sort(arrayList, new Comparator<String>() {
//            @Override
//            public int compare(String s, String t1) {
//                return s.compareToIgnoreCase(t1);
//            }
//        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mButtonAnalyze.setText("Wykonaj analizę dla " + arrayList.get(position));
                mPickedCity = true;
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
        if (Integer.parseInt(mFirstDate.getText().toString()) < 1950 || Integer.parseInt(mFirstDate.getText().toString()) > 2020) {
            Toast.makeText(this, "Zakres lat to 1950 - 2020. Proszę wpisać datę z zakresu", Toast.LENGTH_LONG).show();
            return false;
        }

        if (Integer.parseInt(mSecondDate.getText().toString()) < 1950 || Integer.parseInt(mSecondDate.getText().toString()) > 2020) {
            Toast.makeText(this, "Zakres lat to 1950 - 2020. Proszę wpisać datę z zakresu", Toast.LENGTH_LONG).show();
            return false;
        }

        if (Integer.parseInt(mFirstDate.getText().toString()) > Integer.parseInt(mSecondDate.getText().toString())) {
            Toast.makeText(this, "Pierwsza data nie może być większa od drugiej", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mPickedCity == false) {
            Toast.makeText(this, "Proszę wybrać miasto", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    public void startActivity() {
        Intent intent = new Intent(this, AnalyzeHydroActivity.class);
        intent.putExtra("keyCityName", mSendCityName);
        intent.putExtra("keyFirstDate", mFirstDate.getText().toString());
        intent.putExtra("keySecondDate", mSecondDate.getText().toString());
        startActivity(intent);
    }
}