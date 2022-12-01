package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;

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

public class MeteorologicalActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButtonAnalyze;
    private EditText mFirstDate, mSecondDate;
    private String mSendCityName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteorological2);

        mListView = findViewById(R.id.listView);
        mButtonAnalyze = findViewById(R.id.buttonAnalyse4);

        mFirstDate = findViewById(R.id.editTextFirstYear4);
        mSecondDate = findViewById(R.id.editTextSecondYear4);

        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Łódź");
        arrayList.add("Warszawa");
        arrayList.add("Śnieżka");
        arrayList.add("Bielsko-Biała");
        arrayList.add("Gdynia");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        if (Integer.parseInt(mFirstDate.getText().toString()) < 1950 || Integer.parseInt(mFirstDate.getText().toString()) > 2020) {
            Toast.makeText(this, "Zakres lat to 1950 - 2020. Proszę wpisać datę z zakresu", Toast.LENGTH_LONG).show();
            return false;
        }

        if (Integer.parseInt(mSecondDate.getText().toString()) < 1950 || Integer.parseInt(mSecondDate.getText().toString()) > 2020) {
            Toast.makeText(this, "Zakres lat to 1950 - 2020. Proszę wpisać datę z zakresu", Toast.LENGTH_LONG).show();
            return false;
        }

        if (Integer.parseInt(mFirstDate.getText().toString()) > Integer.parseInt(mSecondDate.getText().toString())) {
            Toast.makeText(this, "Pierwsza data nie może być większa od drugiej", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    public void startActivity() {
        Intent intent = new Intent(this, AnalyzeClimateActivity.class);
        intent.putExtra("keyCityName", mSendCityName);
        intent.putExtra("keyFirstDate", mFirstDate.getText().toString());
        intent.putExtra("keySecondDate", mSecondDate.getText().toString());
        startActivity(intent);
    }


}