package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MeteologicalActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteological);

        mListView = findViewById(R.id.listView);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Wrocław");
        arrayList.add("Warszawa");
        arrayList.add("Gdańsk");
        arrayList.add("Szczecin");
        arrayList.add("Lublin");
        arrayList.add("Białystok");
        arrayList.add("Łódź");
        arrayList.add("Gdynia");
        arrayList.add("Kraków");
        arrayList.add("Katowice");
        arrayList.add("Rzeszów");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        mListView.setAdapter(arrayAdapter);

//        mListView.setOnClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(view.getContext().getApplicationContext(), "XD", Toast.LENGTH_LONG).show();
//            }
//        });

    }
}