package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AnalyzeClimateActivity extends AppCompatActivity {

    private TextView infoTextView, mClimateResultFirstDate1, mClimateResultFirstDate2, mClimateResultFirstDate3,
            mClimateResultFirstDate4, mClimateResultFirstDate5, mClimateResultFirstDate6,
            mClimateResultFirstDate7, mClimateResultFirstDate8, mClimateResultFirstDate9,
            mClimateResultFirstDate10, mClimateResultFirstDate11, mClimateResultFirstDate12, mFinalResultTextView;
    private Button mButtonStart;
    private String cityName = "LIMANOWA";
    private String year;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_climate);

        infoTextView = findViewById(R.id.textView2);
        mButtonStart = findViewById(R.id.buttonStartAnalyze);
        mFinalResultTextView = findViewById(R.id.textViewFinalResultClimate);

        mClimateResultFirstDate1 = findViewById(R.id.climateResultFirstDate1);
        mClimateResultFirstDate2 = findViewById(R.id.climateResultFirstDate2);
        mClimateResultFirstDate3 = findViewById(R.id.climateResultFirstDate3);
        mClimateResultFirstDate4 = findViewById(R.id.climateResultFirstDate4);
        mClimateResultFirstDate5 = findViewById(R.id.climateResultFirstDate5);
        mClimateResultFirstDate6 = findViewById(R.id.climateResultFirstDate6);
        mClimateResultFirstDate7 = findViewById(R.id.climateResultFirstDate7);
        mClimateResultFirstDate8 = findViewById(R.id.climateResultFirstDate8);
        mClimateResultFirstDate9 = findViewById(R.id.climateResultFirstDate9);
        mClimateResultFirstDate10 = findViewById(R.id.climateResultFirstDate10);
        mClimateResultFirstDate11 = findViewById(R.id.climateResultFirstDate11);
        mClimateResultFirstDate12 = findViewById(R.id.climateResultFirstDate12);


        Intent intent = getIntent();
        year = intent.getStringExtra("keyFirstDate");

        infoTextView.setText("XD");
        cityName = cityName.toUpperCase();
        String URL = makeURL(Integer.parseInt(year));

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
        String title = URLUtil.guessFileName(URL, null, null);
        request.setTitle(title);
        request.setDescription("Downloading file");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);


        File file = new File("/sdcard/Download/" + title);

        if (file.exists()) {
            System.out.println("XD " + file.getAbsolutePath());
        } else {
            System.out.println("Sadge");
        }

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getData(file, "01");

                float firstDate1 = getData(file, year, "01", mClimateResultFirstDate1);
                float firstDate2 = getData(file, year, "02", mClimateResultFirstDate2);
                float firstDate3 = getData(file, year, "03", mClimateResultFirstDate3);
                float firstDate4 = getData(file, year, "04", mClimateResultFirstDate4);
                float firstDate5 = getData(file, year, "05", mClimateResultFirstDate5);
                float firstDate6 = getData(file, year, "06", mClimateResultFirstDate6);
                float firstDate7 = getData(file, year, "07", mClimateResultFirstDate7);
                float firstDate8 = getData(file, year, "08", mClimateResultFirstDate8);
                float firstDate9 = getData(file, year, "09", mClimateResultFirstDate9);
                float firstDate10 = getData(file, year, "10", mClimateResultFirstDate10);
                float firstDate11 = getData(file, year, "11", mClimateResultFirstDate11);
                float firstDate12 = getData(file, year, "12", mClimateResultFirstDate12);


                float averageFirstDate = (firstDate1 + firstDate2 + firstDate3 + firstDate4 +
                        firstDate5 + firstDate6 + firstDate7 + firstDate8 + firstDate9 +
                        firstDate10 + firstDate11 + firstDate12) / 12;

                mFinalResultTextView.setText(String.valueOf(averageFirstDate));
            }
        });


    }

    public String makeURL(int year) {
        String URL;

        if (year >= 1951 && year <= 1955) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1951_1955/1951_1955_m_k.zip";
        } else if (year >= 1956 && year <= 1960) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1956_1960/1956_1960_m_k.zip";
        } else if (year >= 1961 && year <= 1965) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1961_1965/1961_1965_m_k.zip";
        } else if (year >= 1966 && year <= 1970) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1966_1970/1966_1970_m_k.zip";
        } else if (year >= 1971 && year <= 1975) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1971_1975/1971_1975_m_k.zip";
        } else if (year >= 1976 && year <= 1980) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1976_1980/1976_1980_m_k.zip";
        } else if (year >= 1981 && year <= 1985) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1981_1985/1981_1985_m_k.zip";
        } else if (year >= 1986 && year <= 1990) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1986_1990/1986_1990_m_k.zip";
        } else if (year >= 1991 && year <= 1995) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1991_1995/1991_1995_m_k.zip";
        } else if (year >= 1996 && year <= 2000) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/1996_2000/1996_2000_m_k.zip";
        } else {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/" +
                    String.valueOf(year) + "/" + String.valueOf(year) + "_m_k.zip";
        }

        return URL;
    }

    public String returnFileName(int year) {
        String yearFile;

        if (year >= 1951 && year <= 1955) {
            yearFile = "k_m_t_1951_1955.csv";
        } else if (year >= 1956 && year <= 1960) {
            yearFile = "k_m_t_1956_1960.csv";
        } else if (year >= 1961 && year <= 1965) {
            yearFile = "k_m_t_1961_1965.csv";
        } else if (year >= 1966 && year <= 1970) {
            yearFile = "k_m_t_1966_1970.csv";
        } else if (year >= 1971 && year <= 1975) {
            yearFile = "k_m_t_1971_1975.csv";
        } else if (year >= 1976 && year <= 1980) {
            yearFile = "k_m_t_1976_1980.csv";
        } else if (year >= 1981 && year <= 1985) {
            yearFile = "k_m_t_1981_1985.csv";
        } else if (year >= 1986 && year <= 1990) {
            yearFile = "k_m_t_1986_1990.csv";
        } else if (year >= 1991 && year <= 1995) {
            yearFile = "k_m_t_1991_1995.csv";
        } else if (year >= 1996 && year <= 2000) {
            yearFile = "k_m_t_1996_2000.csv";
        } else {
            yearFile = "k_m_t_" + String.valueOf(year) + ".csv";
        }

        return yearFile;
    }

    public float getData(File file, String year, String month, TextView textView) {
        float average = 0;

        try {
            unzip(file, new File("/sdcard/Download"));
            infoTextView.setText("Rozpakowano dane");
            try {
                File csvfile = new File("/sdcard/Download/" + returnFileName(Integer.parseInt(year)));
                CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                List<String[]> nextLine2 = reader.readAll();
                ArrayList<String> dataList = new ArrayList<>();

                for (int i = 0; i < nextLine2.size(); i++) {
                    String[] strings = nextLine2.get(i);
                    for (int j = 0; j < strings.length; j++) {
                        dataList.add(strings[j]);
                    }
                }

                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).equals(cityName) && dataList.get(i+1).equals(year) && dataList.get(i+2).equals(month)) {
                        average = Float.parseFloat(dataList.get(i+3));
                        //System.out.println(dataList.get(i));
                    }
                }
                //System.out.println("Średnia: " + average / 12);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AnalyzeClimateActivity.this, "The specified file was not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        textView.setText(String.valueOf(average));
        return average;
    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }
}

