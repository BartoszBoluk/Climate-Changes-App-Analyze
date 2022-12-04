package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
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
            mClimateResultFirstDate10, mClimateResultFirstDate11, mClimateResultFirstDate12;
    private Button mButtonStart;
    private String cityName = "Pszczyna";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_climate);

        infoTextView = findViewById(R.id.textView2);
        mButtonStart = findViewById(R.id.buttonStartAnalyze);
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

        infoTextView.setText("XD");
        cityName = cityName.toUpperCase();
        String URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/2001/2001_m_k.zip";

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
                try {
                    unzip(file, new File("/sdcard/Download"));
                    infoTextView.setText("Rozpakowano dane");

                    try {
                        File csvfile = new File("/sdcard/Download/k_m_t_2001.csv");
                        CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                        List<String[]> nextLine2 = reader.readAll();

                        ArrayList<String> dataList = new ArrayList<>();

                        for (int i = 0; i < nextLine2.size(); i++) {
                            String[] strings = nextLine2.get(i);
                            for (int j = 0; j < strings.length; j++) {
                                //System.out.println(strings[j] + " ");
                                dataList.add(strings[j]);
                            }
                            //System.out.println();
                        }

                        System.out.println("XD: " + nextLine2.size());
                        System.out.println(cityName + ": " + dataList.size());

                        String[] nextLine;
                        float average = 0;


                        for (int i = 0; i < dataList.size(); i++) {
                            if (dataList.get(i).equals(cityName)) {
                                average += Float.parseFloat(dataList.get(i+3));
                            }
                        }

                        System.out.println("Średnia: " + average / 12);


//                        while ((nextLine = reader.readNext()) != null) {
//                            // nextLine[] is an array of values from the line
//                            //System.out.println(nextLine[0] + nextLine[1] + "etc...");
//                            System.out.println(nextLine[0]);
//                            System.out.println(nextLine[1]);
//                            System.out.println(nextLine[2]);
//                            System.out.println(nextLine[3]);
//                            System.out.println(nextLine[4]);
//                            System.out.println(nextLine[5]);
//                            System.out.println(nextLine[6]);
//                            System.out.println(nextLine[7]);
//                            System.out.println(nextLine[8]);
//                            System.out.println(nextLine[9]);
//                            System.out.println(nextLine[10]);
//                            System.out.println(nextLine[11]);
//                            System.out.println(nextLine[12]);
//
//                            break;
//
////                            nextLine[0] = String.valueOf(reader.readAll());
//                        }

//                        String[] splitStr = nextLine[0].split("\\,");
//
//                        System.out.println(splitStr[0]);
//
////                        String s = elements.toString();
////                        String[] splitStr = s.split("\\s+");

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AnalyzeClimateActivity.this, "The specified file was not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


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

