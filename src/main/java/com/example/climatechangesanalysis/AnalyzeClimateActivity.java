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
import android.widget.Button;
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

public class AnalyzeClimateActivity extends AppCompatActivity {

    // TextView poszczególnych miesięcy 1-12 dla pierwszej i drugiej daty
    private TextView mClimateResultFirstDate1, mClimateResultFirstDate2, mClimateResultFirstDate3,
            mClimateResultFirstDate4, mClimateResultFirstDate5, mClimateResultFirstDate6,
            mClimateResultFirstDate7, mClimateResultFirstDate8, mClimateResultFirstDate9,
            mClimateResultFirstDate10, mClimateResultFirstDate11, mClimateResultFirstDate12,
            mFinalResultTextView, mClimateResultSecondDate1, mClimateResultSecondDate2,
            mClimateResultSecondDate3, mClimateResultSecondDate4, mClimateResultSecondDate5,
            mClimateResultSecondDate6, mClimateResultSecondDate7, mClimateResultSecondDate8,
            mClimateResultSecondDate9, mClimateResultSecondDate10, mClimateResultSecondDate11,
            mClimateResultSecondDate12;
    private String mCityName, mFirstYear, mSecondYear;
    private ProgressBar mProgressBar;
    public static boolean noData;
    public File file1, file2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_climate);

        TextView infoTextView, firstDateTextView, secondDateTextView;
        Button buttonStart;

        infoTextView = findViewById(R.id.textView2);
        firstDateTextView = findViewById(R.id.monthClimate21);
        secondDateTextView = findViewById(R.id.monthClimate22);
        mFinalResultTextView = findViewById(R.id.textViewFinalResultClimate);
        mProgressBar = findViewById(R.id.progressBarClimate);

        // Inicjalizacja TextView poszczególnych miesięcy 1-12 dla pierwszej daty
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

        // Inicjalizacja TextView poszczególnych miesięcy 1-12 dla drugiej daty
        mClimateResultSecondDate1 = findViewById(R.id.climateResultSecondDate1);
        mClimateResultSecondDate2 = findViewById(R.id.climateResultSecondDate2);
        mClimateResultSecondDate3 = findViewById(R.id.climateResultSecondDate3);
        mClimateResultSecondDate4 = findViewById(R.id.climateResultSecondDate4);
        mClimateResultSecondDate5 = findViewById(R.id.climateResultSecondDate5);
        mClimateResultSecondDate6 = findViewById(R.id.climateResultSecondDate6);
        mClimateResultSecondDate7 = findViewById(R.id.climateResultSecondDate7);
        mClimateResultSecondDate8 = findViewById(R.id.climateResultSecondDate8);
        mClimateResultSecondDate9 = findViewById(R.id.climateResultSecondDate9);
        mClimateResultSecondDate10 = findViewById(R.id.climateResultSecondDate10);
        mClimateResultSecondDate11 = findViewById(R.id.climateResultSecondDate11);
        mClimateResultSecondDate12 = findViewById(R.id.climateResultSecondDate12);

        // Pobieraie nazwy miasta i dat z poprzedniego activity
        Intent intent = getIntent();
        mFirstYear = intent.getStringExtra("keyFirstDate");
        mSecondYear = intent.getStringExtra("keySecondDate");
        mCityName = intent.getStringExtra("keyCityName");

        infoTextView.setText("Analiza dla punktu pomiarowego " + mCityName);
        firstDateTextView.setText(mFirstYear);
        secondDateTextView.setText(mSecondYear);
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
                    mClimateResultFirstDate1);
            float firstDate2 = getData(firstDateList, mFirstYear, "02",
                    mClimateResultFirstDate2);
            float firstDate3 = getData(firstDateList, mFirstYear, "03",
                    mClimateResultFirstDate3);
            float firstDate4 = getData(firstDateList, mFirstYear, "04",
                    mClimateResultFirstDate4);
            float firstDate5 = getData(firstDateList, mFirstYear, "05",
                    mClimateResultFirstDate5);
            float firstDate6 = getData(firstDateList, mFirstYear, "06",
                    mClimateResultFirstDate6);
            float firstDate7 = getData(firstDateList, mFirstYear, "07",
                    mClimateResultFirstDate7);
            float firstDate8 = getData(firstDateList, mFirstYear, "08",
                    mClimateResultFirstDate8);
            float firstDate9 = getData(firstDateList, mFirstYear, "09",
                    mClimateResultFirstDate9);
            float firstDate10 = getData(firstDateList, mFirstYear, "10",
                    mClimateResultFirstDate10);
            float firstDate11 = getData(firstDateList, mFirstYear, "11",
                    mClimateResultFirstDate11);
            float firstDate12 = getData(firstDateList, mFirstYear, "12",
                    mClimateResultFirstDate12);

            returnDatList(file2, mSecondYear, secondDateList);

            // Wywoływanie funkcji getData, zapisywanie wyniku do zmiennej float.
            float secondDate1 = getData(secondDateList, mSecondYear, "01",
                    mClimateResultSecondDate1);
            float secondDate2 = getData(secondDateList, mSecondYear, "02",
                    mClimateResultSecondDate2);
            float secondDate3 = getData(secondDateList, mSecondYear, "03",
                    mClimateResultSecondDate3);
            float secondDate4 = getData(secondDateList, mSecondYear, "04",
                    mClimateResultSecondDate4);
            float secondDate5 = getData(secondDateList, mSecondYear, "05",
                    mClimateResultSecondDate5);
            float secondDate6 = getData(secondDateList, mSecondYear, "06",
                    mClimateResultSecondDate6);
            float secondDate7 = getData(secondDateList, mSecondYear, "07",
                    mClimateResultSecondDate7);
            float secondDate8 = getData(secondDateList, mSecondYear, "08",
                    mClimateResultSecondDate8);
            float secondDate9 = getData(secondDateList, mSecondYear, "09",
                    mClimateResultSecondDate9);
            float secondDate10 = getData(secondDateList, mSecondYear, "10",
                    mClimateResultSecondDate10);
            float secondDate11 = getData(secondDateList, mSecondYear, "11",
                    mClimateResultSecondDate11);
            float secondDate12 = getData(secondDateList, mSecondYear, "12",
                    mClimateResultSecondDate12);

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
                    mFinalResultTextView.setText("Średnia roczna temperatura zmalała o " +
                            new DecimalFormat("###.##").format(finalAverageScore) + "°.");
                } else {
                    mFinalResultTextView.setText(Html.fromHtml("<font color=red>" +
                            "Wynik niepewny." + "</font><br>" + "Średnia roczna " +
                            "temperatura zmalała o " + new DecimalFormat
                            ("###.##").format(finalAverageScore) + "°."));
                }
            } else {
                finalAverageScore *= -1;
                if (noData == false) {
                    mFinalResultTextView.setText("Średnia roczna temperatura zwiększyła się o " +
                            new DecimalFormat("###.##").format(finalAverageScore) + "°.");
                } else {
                    mFinalResultTextView.setText(Html.fromHtml("<font color=red>" +
                            "Wynik niepewny." + "</font><br>" + "Średnia roczna temperatura " +
                            "zwiększyła się o " + new DecimalFormat("###.##").
                            format(finalAverageScore) + "°."));
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    };

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
                            System.out.println("TEST:  " + counter);
                        }

                        if (counter == 13) {
                            break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AnalyzeClimateActivity.this, "Nie znaleziono pliku",
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AnalyzeClimateActivity.this, "Nie udało się wykonać polecenia",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    /*
     * Z powodu nieczytania przez CSVReader polskich znaków, trzeba było użyć ID stacji.
     * Funkcja sprawdza podaną nazwę miejscowości i zwraca jako Stringa odpowienie ID stacji.
     */
    public String returnStationCode(String cityName) {
        String stationCode = null;

        if (cityName.equals("Pszczyna")) {
            stationCode = "249180010";
        } else if (cityName.equals("Cieszyn")) {
            stationCode = "249180130";
        } else if (cityName.equals("Brenna")) {
            stationCode = "249180160";
        } else if (cityName.equals("Wisła")) {
            stationCode = "249180230";
        } else if (cityName.equals("Maków Podhalański")) {
            stationCode = "249190190";
        } else if (cityName.equals("Tomaszów Bolesławiecki")) {
            stationCode = "251150180";
        } else if (cityName.equals("Tomaszów Lubelski")) {
            stationCode = "250230070";
        } else if (cityName.equals("Zgorzelec")) {
            stationCode = "251150250";
        } else if (cityName.equals("Katowice Pyrzowice")) {
            stationCode = "250190530";
        } else if (cityName.equals("Namysłów")) {
            stationCode = "251150180";
        } else if (cityName.equals("Jelcz-laskowice")) {
            stationCode = "251170320";
        } else if (cityName.equals("Puławy")) {
            stationCode = "251210120";
        } else if (cityName.equals("Gniezno")) {
            stationCode = "252170110";
        } else if (cityName.equals("Legionowo")) {
            stationCode = "252200120";
        } else if (cityName.equals("Gdańsk-Rębiechowo")) {
            stationCode = "254180090";
        } else if (cityName.equals("Grudziądz")) {
            stationCode = "253180150";
        } else if (cityName.equals("Bydgoszcz")) {
            stationCode = "253180220";
        } else if (cityName.equals("Gdynia")) {
            stationCode = "254180060";
        } else if (cityName.equals("Gorzyń")) {
            stationCode = "252150120";
        } else if (cityName.equals("Polkowice Dolne")) {
            stationCode = "251160150";
        } else if (cityName.equals("Jarocin")) {
            stationCode = "250220120";
        } else if (cityName.equals("Chorzelów")) {
            stationCode = "251150180";
        } else if (cityName.equals("Święty Krzyż")) {
            stationCode = "250210050";
        } else if (cityName.equals("Kraków-Obserwatorium")) {
            stationCode = "250190390";
        } else if (cityName.equals("Ząbkowice")) {
            stationCode = "250190250";
        } else if (cityName.equals("Warszawa-Bielany")) {
            stationCode = "252200150";
        } else if (cityName.equals("Warszawa-Obserwatorium")) {
            stationCode = "252210160";
        }

        return stationCode;
    }

    /*
     * Funkcja tworzy link URL do pobrania pliku .zip. Link zależy od podanego jako armument roku.
     */
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
                    year + "/" + year + "_m_k.zip";
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
            yearFile = "k_m_t_" + year + ".csv";
        }

        return yearFile;
    }

    /*
     * Funckja odczytuje dane z pliku i zwraca wartość średniej miesięcznej temperatury, znajdującej
     * się w pliku .csv. Jako argumenty podajemy plik, rok, miesiąc, oraz TextView.°"
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

