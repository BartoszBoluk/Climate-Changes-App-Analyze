package com.example.climatechangesanalysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

    private TextView mTextViewTitleHydro;
    private Button mButtonUnZipHydro;
    private String mCityName, mFirstYear, mSecondYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_hydro);

        mTextViewTitleHydro = findViewById(R.id.textViewTitleHydro);
        mButtonUnZipHydro = findViewById(R.id.buttonUnZipHydro);

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

            File file1 = new File("/sdcard/Download/" + title1);
            File file2 = new File("/sdcard/Download/" + title2);

            mButtonUnZipHydro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Wywoływanie funkcji getData, zapisywanie wyniku do zmiennej float.
                    float firstDate1 = getData(file1, mFirstYear, "01",
                            mHydroResultFirstDate1);
                    float firstDate2 = getData(file1, mFirstYear, "02",
                            mHydroResultFirstDate2);
                    float firstDate3 = getData(file1, mFirstYear, "03",
                            mHydroResultFirstDate3);
                    float firstDate4 = getData(file1, mFirstYear, "04",
                            mHydroResultFirstDate4);
                    float firstDate5 = getData(file1, mFirstYear, "05",
                            mHydroResultFirstDate5);
                    float firstDate6 = getData(file1, mFirstYear, "06",
                            mHydroResultFirstDate6);
                    float firstDate7 = getData(file1, mFirstYear, "07",
                            mHydroResultFirstDate7);
                    float firstDate8 = getData(file1, mFirstYear, "08",
                            mHydroResultFirstDate8);
                    float firstDate9 = getData(file1, mFirstYear, "09",
                            mHydroResultFirstDate9);
                    float firstDate10 = getData(file1, mFirstYear, "10",
                            mHydroResultFirstDate10);
                    float firstDate11 = getData(file1, mFirstYear, "11",
                            mHydroResultFirstDate11);
                    float firstDate12 = getData(file1, mFirstYear, "12",
                            mHydroResultFirstDate12);

                    // Wywoływanie funkcji getData, zapisywanie wyniku do zmiennej float.
                    float secondDate1 = getData(file2, mSecondYear, "01",
                            mHydroResultSecondDate1);
                    float secondDate2 = getData(file2, mSecondYear, "02",
                            mHydroResultSecondDate2);
                    float secondDate3 = getData(file2, mSecondYear, "03",
                            mHydroResultSecondDate3);
                    float secondDate4 = getData(file2, mSecondYear, "04",
                            mHydroResultSecondDate4);
                    float secondDate5 = getData(file2, mSecondYear, "05",
                            mHydroResultSecondDate5);
                    float secondDate6 = getData(file2, mSecondYear, "06",
                            mHydroResultSecondDate6);
                    float secondDate7 = getData(file2, mSecondYear, "07",
                            mHydroResultSecondDate7);
                    float secondDate8 = getData(file2, mSecondYear, "08",
                            mHydroResultSecondDate8);
                    float secondDate9 = getData(file2, mSecondYear, "09",
                            mHydroResultSecondDate9);
                    float secondDate10 = getData(file2, mSecondYear, "10",
                            mHydroResultSecondDate10);
                    float secondDate11 = getData(file2, mSecondYear, "11",
                            mHydroResultSecondDate11);
                    float secondDate12 = getData(file2, mSecondYear, "12",
                            mHydroResultSecondDate12);
                }
            });
        }
    }

    /*
     * Z powodu nieczytania przez CSVReader polskich znaków, trzeba było użyć ID stacji.
     * Funkcja sprawdza podaną nazwę miejscowości i zwraca jako Stringa odpowienie ID stacji.
     */
    public String returnStationCode(String cityName) {
        String stationCode = null;

        if (cityName.equals("Warszawa-Czajka")) {
            stationCode = "252200220";
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
     * Funckja odczytuje dane z pliku i zwraca wartość średniej miesięcznej temperatury, znajdującej
     * się w pliku .csv. Jako argumenty podajemy plik, rok, miesiąc, oraz TextView.
     */
    public float getData(File file, String year, String month, TextView textView) {
        float average = 0;

        try {
            unZip(file, new File("/sdcard/Download"));
            try {
                File csvfile = new File("/sdcard/Download/" + returnFileName(Integer.parseInt(year)));
                CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                List<String[]> nextLine = reader.readAll();
                ArrayList<String> dataList = new ArrayList<>();

                for (int i = 0; i < nextLine.size(); i++) {
                    String[] strings = nextLine.get(i);
                    for (int j = 0; j < strings.length; j++) {
                        dataList.add(strings[j]);
                    }
                }

                String stationCode = returnStationCode(mCityName);

                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).equals(stationCode) && dataList.get(i + 2).equals(year) && dataList.get(i + 3).equals(month)) {
                        average = Float.parseFloat(dataList.get(i + 4));
                        textView.setText(average + " mm");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AnalyzeHydroActivity.this, "Nie znaleziono pliku", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (textView.getText().equals(".")) {
            textView.setTextColor(Color.rgb(255, 0, 0));
            textView.setText("brak danych");
        }

        return average;
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