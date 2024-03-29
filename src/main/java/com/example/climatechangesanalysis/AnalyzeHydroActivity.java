package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ProgressBar;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AnalyzeHydroActivity extends AppCompatActivity {

    // TextView poszczególnych miesięcy 1-12 dla pierwszej i drugiej daty
    private TextView mHydroResultFirstDate1, mHydroResultFirstDate2, mHydroResultFirstDate3,
            mHydroResultFirstDate4, mHydroResultFirstDate5, mHydroResultFirstDate6,
            mHydroResultFirstDate7, mHydroResultFirstDate8, mHydroResultFirstDate9,
            mHydroResultFirstDate10, mHydroResultFirstDate11, mHydroResultFirstDate12,
            mHydroResultSecondDate1, mHydroResultSecondDate2, mHydroResultSecondDate3,
            mHydroResultSecondDate4, mHydroResultSecondDate5, mHydroResultSecondDate6,
            mHydroResultSecondDate7, mHydroResultSecondDate8, mHydroResultSecondDate9,
            mHydroResultSecondDate10, mHydroResultSecondDate11, mHydroResultSecondDate12;

    private TextView mTextViewTitleHydro, mTextViewFirstDate, mTextViewSecondDate, mTextViewFinalResult;
    private String mCityName, mFirstYear, mSecondYear;
    private static boolean noData;
    private ProgressBar mProgressBar;
    public File file1, file2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_hydro);

        mTextViewTitleHydro = findViewById(R.id.textViewTitleHydro);
        mTextViewFirstDate = findViewById(R.id.HydroResultFirstDate);
        mTextViewSecondDate = findViewById(R.id.HydroResultSecondDate);
        mTextViewFinalResult = findViewById(R.id.textViewFinalResultHydro);
        mProgressBar = findViewById(R.id.progressBar);

        mHydroResultFirstDate1 = findViewById(R.id.HydroResultFirstDate1);
        mHydroResultFirstDate2 = findViewById(R.id.HydroResultFirstDate2);
        mHydroResultFirstDate3 = findViewById(R.id.HydroResultFirstDate3);
        mHydroResultFirstDate4 = findViewById(R.id.HydroResultFirstDate4);
        mHydroResultFirstDate5 = findViewById(R.id.HydroResultFirstDate5);
        mHydroResultFirstDate6 = findViewById(R.id.HydroResultFirstDate6);
        mHydroResultFirstDate7 = findViewById(R.id.HydroResultFirstDate7);
        mHydroResultFirstDate8 = findViewById(R.id.HydroResultFirstDate8);
        mHydroResultFirstDate9 = findViewById(R.id.HydroResultFirstDate9);
        mHydroResultFirstDate10 = findViewById(R.id.HydroResultFirstDate10);
        mHydroResultFirstDate11 = findViewById(R.id.HydroResultFirstDate11);
        mHydroResultFirstDate12 = findViewById(R.id.HydroResultFirstDate12);

        mHydroResultSecondDate1 = findViewById(R.id.HydroResultSecondDate1);
        mHydroResultSecondDate2 = findViewById(R.id.HydroResultSecondDate2);
        mHydroResultSecondDate3 = findViewById(R.id.HydroResultSecondDate3);
        mHydroResultSecondDate4 = findViewById(R.id.HydroResultSecondDate4);
        mHydroResultSecondDate5 = findViewById(R.id.HydroResultSecondDate5);
        mHydroResultSecondDate6 = findViewById(R.id.HydroResultSecondDate6);
        mHydroResultSecondDate7 = findViewById(R.id.HydroResultSecondDate7);
        mHydroResultSecondDate8 = findViewById(R.id.HydroResultSecondDate8);
        mHydroResultSecondDate9 = findViewById(R.id.HydroResultSecondDate9);
        mHydroResultSecondDate10 = findViewById(R.id.HydroResultSecondDate10);
        mHydroResultSecondDate11 = findViewById(R.id.HydroResultSecondDate11);
        mHydroResultSecondDate12 = findViewById(R.id.HydroResultSecondDate12);

        // Pobieraie nazwy miasta i dat z poprzedniego activity
        Intent intent = getIntent();
        mFirstYear = intent.getStringExtra("keyFirstDate");
        mSecondYear = intent.getStringExtra("keySecondDate");
        mCityName = intent.getStringExtra("keyCityName");

        mTextViewTitleHydro.setText("Analiza dla punktu pomiarowego " + mCityName);
        mTextViewFirstDate.setText(mFirstYear);
        mTextViewSecondDate.setText(mSecondYear);
        noData = false;

        // Uzyskiwanie permisji do dostępu do plików, by móc pobrać pliki
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        } else {
            // Pobieranie plików dla pierwszej daty za pomocą Download Managera.
            String URL1 = makeURL(Integer.parseInt(mFirstYear));
            String URL2 = makeURL(Integer.parseInt(mSecondYear));

            DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(URL1));
            String title1 = URLUtil.guessFileName(URL1, null, null);
            request1.setTitle(title1);
            request1.setDescription("Downloading file");
            request1.setNotificationVisibility(DownloadManager.
                    Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request1.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title1);

            DownloadManager downloadManager1 = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager1.enqueue(request1);

            // Pobieranie plików dla drugiej daty za pomocą Download Managera.
            DownloadManager.Request request2 = new DownloadManager.Request(Uri.parse(URL2));
            String title2 = URLUtil.guessFileName(URL2, null, null);
            request2.setTitle(title2);
            request2.setDescription("Downloading file");
            request2.setNotificationVisibility(DownloadManager.
                    Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request2.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title2);

            DownloadManager downloadManager2 = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager2.enqueue(request2);

            file1 = new File("/sdcard/Download/" + title1);
            file2 = new File("/sdcard/Download/" + title2);

            registerReceiver(onComplete, new IntentFilter(downloadManager2.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }

    /*
     * Sprawdza czy został pobrany plik. Jeśli tak wykonuje dalsze działania.
     */
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ArrayList<String> firstDateList = new ArrayList<>();
            ArrayList<String> secondDateList = new ArrayList<>();

            returnDatList(file1, mFirstYear, firstDateList);

            // Wywoływanie funkcji getData, zapisywanie wyniku do zmiennej float.
            float firstDate1 = getData(firstDateList, mFirstYear, "01",
                    mHydroResultFirstDate1);
            float firstDate2 = getData(firstDateList, mFirstYear, "02",
                    mHydroResultFirstDate2);
            float firstDate3 = getData(firstDateList, mFirstYear, "03",
                    mHydroResultFirstDate3);
            float firstDate4 = getData(firstDateList, mFirstYear, "04",
                    mHydroResultFirstDate4);
            float firstDate5 = getData(firstDateList, mFirstYear, "05",
                    mHydroResultFirstDate5);
            float firstDate6 = getData(firstDateList, mFirstYear, "06",
                    mHydroResultFirstDate6);
            float firstDate7 = getData(firstDateList, mFirstYear, "07",
                    mHydroResultFirstDate7);
            float firstDate8 = getData(firstDateList, mFirstYear, "08",
                    mHydroResultFirstDate8);
            float firstDate9 = getData(firstDateList, mFirstYear, "09",
                    mHydroResultFirstDate9);
            float firstDate10 = getData(firstDateList, mFirstYear, "10",
                    mHydroResultFirstDate10);
            float firstDate11 = getData(firstDateList, mFirstYear, "11",
                    mHydroResultFirstDate11);
            float firstDate12 = getData(firstDateList, mFirstYear, "12",
                    mHydroResultFirstDate12);

            returnDatList(file2, mSecondYear, secondDateList);

            // Wywoływanie funkcji getData, zapisywanie wyniku do zmiennej float.
            float secondDate1 = getData(secondDateList, mSecondYear, "01",
                    mHydroResultSecondDate1);
            float secondDate2 = getData(secondDateList, mSecondYear, "02",
                    mHydroResultSecondDate2);
            float secondDate3 = getData(secondDateList, mSecondYear, "03",
                    mHydroResultSecondDate3);
            float secondDate4 = getData(secondDateList, mSecondYear, "04",
                    mHydroResultSecondDate4);
            float secondDate5 = getData(secondDateList, mSecondYear, "05",
                    mHydroResultSecondDate5);
            float secondDate6 = getData(secondDateList, mSecondYear, "06",
                    mHydroResultSecondDate6);
            float secondDate7 = getData(secondDateList, mSecondYear, "07",
                    mHydroResultSecondDate7);
            float secondDate8 = getData(secondDateList, mSecondYear, "08",
                    mHydroResultSecondDate8);
            float secondDate9 = getData(secondDateList, mSecondYear, "09",
                    mHydroResultSecondDate9);
            float secondDate10 = getData(secondDateList, mSecondYear, "10",
                    mHydroResultSecondDate10);
            float secondDate11 = getData(secondDateList, mSecondYear, "11",
                    mHydroResultSecondDate11);
            float secondDate12 = getData(secondDateList, mSecondYear, "12",
                    mHydroResultSecondDate12);

            // Obliczanie średnich temperatur rocznych, oraz różnicy,
            // następnie wyświetlanie wyniku w TextView
            float averageFirstDate = (firstDate1 + firstDate2 + firstDate3 + firstDate4 +
                    firstDate5 + firstDate6 + firstDate7 + firstDate8 + firstDate9 +
                    firstDate10 + firstDate11 + firstDate12) / 12;
            float averageSecondDate = (secondDate1 + secondDate2 + secondDate3 + secondDate4 +
                    secondDate5 + secondDate6 + secondDate7 + secondDate8 + secondDate9 +
                    secondDate10 + secondDate11 + secondDate12) / 12;
            float finalAverageScore = averageFirstDate - averageSecondDate;

            if (averageFirstDate > averageSecondDate) {
                if (noData == false) {
                    mTextViewFinalResult.setText("Średnia roczna suma opadów zmalała o " +
                            new DecimalFormat("###.##").format(finalAverageScore) + " mm.");
                } else {
                    mTextViewFinalResult.setText(Html.fromHtml("<font color=red>" + "Wynik niepewny." +
                            "</font><br>" + "Średnia roczna suma opadów zmalała o " +
                            new DecimalFormat("###.##").format(finalAverageScore) + " mm."));
                }
            } else {
                finalAverageScore *= -1;

                if (noData == false) {
                    mTextViewFinalResult.setText("Średnia roczna suma opadów zwiększyła się o " +
                            new DecimalFormat("###.##").format(finalAverageScore) + " mm.");
                } else {
                    mTextViewFinalResult.setText(Html.fromHtml("<font color=red>" + "Wynik niepewny." +
                            "</font><br>" + "Średnia roczna suma opadów zwiększyła się o " +
                            new DecimalFormat("###.##").format(finalAverageScore) + " mm."));
                }
            }
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    };

    /*
     * Z powodu nieczytania przez CSVReader polskich znaków, trzeba było użyć ID stacji.
     * Funkcja sprawdza podaną nazwę miejscowości i zwraca jako Stringa odpowienie ID stacji.
     */
    public String returnStationCode(String cityName) {
        String stationCode = "";

        if (cityName.equals("Warszawa-Czajka")) {
            stationCode = "252200220";
        } else if (cityName.equals("Warszowice")) {
            stationCode = "249180020";
        } else if (cityName.equals("Wisła Wielka")) {
            stationCode = "249180040";
        } else if (cityName.equals("Goczałkowice-Zdrój")) {
            stationCode = "249180050";
        } else if (cityName.equals("Wisła-Malinka")) {
            stationCode = "249180240";
        } else if (cityName.equals("Szczyrk")) {
            stationCode = "249180210";
        } else if (cityName.equals("Wadowice")) {
            stationCode = "249190080";
        } else if (cityName.equals("Świdnik")) {
            stationCode = "249200160";
        } else if (cityName.equals("Huta")) {
            stationCode = "249200350";
        } else if (cityName.equals("Białka Tatrzańska")) {
            stationCode = "249200450";
        } else if (cityName.equals("Pilzno")) {
            stationCode = "249210010";
        } else if (cityName.equals("Sobótka")) {
            stationCode = "250160060";
        } else if (cityName.equals("Kamienna Góra")) {
            stationCode = "250160150";
        } else if (cityName.equals("Wałbrzych")) {
            stationCode = "250160160";
        } else if (cityName.equals("Oława")) {
            stationCode = "250170030";
        } else if (cityName.equals("Brzeg")) {
            stationCode = "250170050";
        } else if (cityName.equals("Starogard Gdańśki")) {
            stationCode = "253180030";
        } else if (cityName.equals("Wrocław-Stabłowice")) {
            stationCode = "251160260";
        } else if (cityName.equals("Szczecin-Pogodno")) {
            stationCode = "253140210";
        }

        return stationCode;
    }

    /*
     * Funkcja tworzy link URL do pobrania pliku .zip. Link zależy od podanego jako armument roku.
     */
    public String makeURL(int year) {
        String URL;

        if (year >= 1951 && year <= 1955) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1951_1955/1951_1955_m_o.zip";
        } else if (year >= 1956 && year <= 1960) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1956_1960/1956_1960_m_o.zip";
        } else if (year >= 1961 && year <= 1965) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1961_1965/1961_1965_m_o.zip";
        } else if (year >= 1966 && year <= 1970) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1966_1970/1966_1970_m_o.zip";
        } else if (year >= 1971 && year <= 1975) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1971_1975/1971_1975_m_o.zip";
        } else if (year >= 1976 && year <= 1980) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1976_1980/1976_1980_m_o.zip";
        } else if (year >= 1981 && year <= 1985) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1981_1985/1981_1985_m_o.zip";
        } else if (year >= 1986 && year <= 1990) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1986_1990/1986_1990_m_o.zip";
        } else if (year >= 1991 && year <= 1995) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1991_1995/1991_1995_m_o.zip";

        } else if (year >= 1996 && year <= 2000) {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/1996_2000/1996_2000_m_o.zip";
        } else {
            URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/miesieczne/opad/" +
                    year + "/" + year + "_m_o.zip";
        }

        return URL;
    }

    /*
     * Funckja zwraca Stringa. Jest to nazwa pliku .csv. Jego nazwa jest zależna od podanego jako
     * argmument roku.
     */
    public String returnFileName(int year) {
        String yearFile;

        if (year >= 1951 && year <= 1955) {
            yearFile = "o_m_1950_1955.csv";
        } else if (year >= 1956 && year <= 1960) {
            yearFile = "o_m_1956_1960.csv";
        } else if (year >= 1961 && year <= 1965) {
            yearFile = "o_m_1961_1965.csv";
        } else if (year >= 1966 && year <= 1970) {
            yearFile = "o_m_1966_1970.csv";
        } else if (year >= 1971 && year <= 1975) {
            yearFile = "o_m_1971_1975.csv";
        } else if (year >= 1976 && year <= 1980) {
            yearFile = "o_m_1976_1980.csv";
        } else if (year >= 1981 && year <= 1985) {
            yearFile = "o_m_1981_1985.csv";
        } else if (year >= 1986 && year <= 1990) {
            yearFile = "o_m_1986_1990.csv";
        } else if (year >= 1991 && year <= 1995) {
            yearFile = "o_m_1991_1995.csv";
        } else if (year >= 1996 && year <= 2000) {
            yearFile = "o_m_1996_2000.csv";
        } else {
            yearFile = "o_m_" + year + ".csv";
        }

        return yearFile;
    }

    /*
     * Funkcja rozpakowuje plik zip. Następnie zapisuje dane do ArrayListy.
     */
    public ArrayList<String> returnDatList(File file, String year, ArrayList<String> dataList) {
        try {
            unZip(file, new File("/sdcard/Download"));
            try {
                File csvfile = new File("/sdcard/Download/" + returnFileName(Integer.parseInt(year)));
                CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                List<String[]> nextLine = reader.readAll();


                int counter = 0;

                for (int i = 0; i < nextLine.size(); i++) {
                    String[] strings = nextLine.get(i);
                    for (int j = 0; j < strings.length; j++) {
                        dataList.add(strings[j]);

                        if (strings[j].equals(mCityName.toUpperCase()) && strings[j + 1].equals(year)) {
                            counter++;
                        }

                        if (counter == 13) {
                            break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AnalyzeHydroActivity.this, "Nie znaleziono pliku",
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AnalyzeHydroActivity.this, "Nie udało się wykonać polecenia",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    /*
     * Funckja porównuje ID stacji, miesiąc i rok. Jeśli się zgadzają zwraca wartość, oraz zapisuje
     * w TextView.
     */
    public float getData(ArrayList<String> list, String year, String month, TextView textView) {
        float value = 0;

        String stationCode = returnStationCode(mCityName);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(stationCode) && list.get(i + 2).equals(year) && list.get(i + 3).equals(month)) {
                value = Float.parseFloat(list.get(i + 4));
                textView.setText(value + " mm");
            }
        }

        if (textView.getText().equals(".")) {
            textView.setTextColor(Color.rgb(255, 0, 0));
            textView.setText("brak danych");
            noData = true;
        }

        return value;
    }

    /*
     * Funkcja rozpakowuje pliku .zip. Jako argumenty funkcji podajemy podajemy plik zip i miejsce
     * gdzie mają być te pliki wypakowane.
     */
    public static void unZip(File zipFile, File directory) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));

        try {
            ZipEntry zipEntry;
            int count;
            byte[] buffer = new byte[8192];

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                File file = new File(directory, zipEntry.getName());
                File dir = zipEntry.isDirectory() ? file : file.getParentFile();

                if (!dir.isDirectory() && !dir.mkdirs()) {
                    throw new FileNotFoundException("Nie udało się rozpakować pliku: " +
                            dir.getAbsolutePath());
                }

                if (zipEntry.isDirectory())
                    continue;
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                try {
                    while ((count = zipInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                } finally {
                    fileOutputStream.close();
                }
            }
        } finally {
            zipInputStream.close();
        }
    }

}