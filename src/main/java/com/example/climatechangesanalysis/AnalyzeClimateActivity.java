package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AnalyzeClimateActivity extends AppCompatActivity {

    private TextView test;
    private Button buttonStart;

    // Progress Dialog
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/klimat/2001/2001_m_k.zip";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_climate);

        test = findViewById(R.id.textView2);
        buttonStart = findViewById(R.id.buttonStartAnalyze);

        test.setText("XD");
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

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    unzip(file, new File("/sdcard/Download"));
                    test.setText("Rozpakowano dane");

                    try {
                        File csvfile = new File("/sdcard/Download/k_m_t_2001.csv");
                        CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                        String[] nextLine;
                        while ((nextLine = reader.readNext()) != null) {
                            // nextLine[] is an array of values from the line
                            //System.out.println(nextLine[0] + nextLine[1] + "etc...");
                            System.out.println(nextLine[2]);
                            break;

//                            nextLine[0] = String.valueOf(reader.readAll());
                        }



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



//        String path = "/" + Environment.DIRECTORY_DCIM + "/" + title;
//        System.out.println(path);
//
//        File file = new File(Environment.DIRECTORY_DOWNLOADS + "/" + title);
//
//        unpackZip(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/", title);
//
//        System.out.println(file.getAbsolutePath());

//        try {
//            unzip(file, new File(Environment.DIRECTORY_DOWNLOADS));
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("XDDDDD");
//        }

//        unpackZip(path, title);

    }

    private boolean unpackZip(String path, String zipname)
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            is = new FileInputStream(path + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;

            while((ze = zis.getNextEntry()) != null)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                String filename = ze.getName();
                FileOutputStream fout = new FileOutputStream(path + filename);

                // reading and writing
                while((count = zis.read(buffer)) != -1)
                {
                    baos.write(buffer, 0, count);
                    byte[] bytes = baos.toByteArray();
                    fout.write(bytes);
                    baos.reset();
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
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

