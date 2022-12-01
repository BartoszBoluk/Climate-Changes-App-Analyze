package com.example.climatechangesanalysis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AnalyzeActivity extends AppCompatActivity {

    private TextView mResult;
    private TextView mResultFirstDate, mResultFirstDate2, mResultFirstDate3, mResultFirstDate4, mResultFirstDate5,
            mResultFirstDate6, mResultFirstDate7, mResultFirstDate8, mResultFirstDate9,
            mResultFirstDate10, mResultFirstDate11, mResultFirstDate12;
    private TextView mResultSecondDate, mResultSecondDate2, mResultSecondDate3, mResultSecondDate4, mResultSecondDate5,
            mResultSecondDate6, mResultSecondDate7, mResultSecondDate8, mResultSecondDate9,
            mResultSecondDate10, mResultSecondDate11, mResultSecondDate12;
    private String mFirstDate, mSecondDate, mCityName;
    public static boolean noData = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analize);

        TextView mTextViewFirstDate, mTextViewSecondDate, mTextView;

        // TextView poszczególnych miesięcy 1-12 dla pierwszej daty
        mTextView = findViewById(R.id.textView);
        mResultFirstDate = findViewById(R.id.resultFirstDate);
        mResultFirstDate2 = findViewById(R.id.resultFirstDate2);
        mResultFirstDate3 = findViewById(R.id.resultFirstDate3);
        mResultFirstDate4 = findViewById(R.id.resultFirstDate4);
        mResultFirstDate5 = findViewById(R.id.resultFirstDate5);
        mResultFirstDate6 = findViewById(R.id.resultFirstDate6);
        mResultFirstDate7 = findViewById(R.id.resultFirstDate7);
        mResultFirstDate8 = findViewById(R.id.resultFirstDate8);
        mResultFirstDate9 = findViewById(R.id.resultFirstDate9);
        mResultFirstDate10 = findViewById(R.id.resultFirstDate10);
        mResultFirstDate11 = findViewById(R.id.resultFirstDate11);
        mResultFirstDate12 = findViewById(R.id.resultFirstDate12);

        // TextView poszczególnych miesięcy 1-12 dla drugiej daty
        mResultSecondDate = findViewById(R.id.resultSecondDate);
        mResultSecondDate2 = findViewById(R.id.resultSecondDate2);
        mResultSecondDate3 = findViewById(R.id.resultSecondDate3);
        mResultSecondDate4 = findViewById(R.id.resultSecondDate4);
        mResultSecondDate5 = findViewById(R.id.resultSecondDate5);
        mResultSecondDate6 = findViewById(R.id.resultSecondDate6);
        mResultSecondDate7 = findViewById(R.id.resultSecondDate7);
        mResultSecondDate8 = findViewById(R.id.resultSecondDate8);
        mResultSecondDate9 = findViewById(R.id.resultSecondDate9);
        mResultSecondDate10 = findViewById(R.id.resultSecondDate10);
        mResultSecondDate11 = findViewById(R.id.resultSecondDate11);
        mResultSecondDate12 = findViewById(R.id.resultSecondDate12);

        mResult = findViewById(R.id.result);

        Intent intent = getIntent();
        mCityName = intent.getStringExtra("keyCityName");
        mFirstDate = intent.getStringExtra("keyFirstDate");
        mSecondDate = intent.getStringExtra("keySecondDate");

        //TextView dat (Kolumny) i tytół activity
        mTextView.setText("Analiza dla miasta " + mCityName);
        mTextViewFirstDate = findViewById(R.id.textViewFirstDate);
        mTextViewSecondDate = findViewById(R.id.textViewSecondDate);
        mTextViewFirstDate.setText("Rok " + mFirstDate);
        mTextViewSecondDate.setText("Rok " + mSecondDate);

        Content content = new Content();
        content.execute();

    }

    class Content extends AsyncTask<Void, Void, Void> {

        ArrayList<String> ls = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String URL_FirstDate, URL_FirstDate2, URL_FirstDate3, URL_FirstDate4, URL_FirstDate5,
                    URL_FirstDate6, URL_FirstDate7, URL_FirstDate8, URL_FirstDate9, URL_FirstDate10,
                    URL_FirstDate11, URL_FirstDate12;
            String URL_SecondDate, URL_SecondDate2, URL_SecondDate3,
                    URL_SecondDate4, URL_SecondDate5, URL_SecondDate6, URL_SecondDate7, URL_SecondDate8,
                    URL_SecondDate9, URL_SecondDate10, URL_SecondDate11, URL_SecondDate12;
            String mZweryfikowany, mFileName, mYear1, mYear2;
            String link_w = "";

            org.jsoup.nodes.Document document = null;
            noData = false;

            if (mFirstDate.equals("1990")) {
                mYear1 = "90";
            } else if (mFirstDate.equals("1991")) {
                mYear1 = "91";
            } else if (mFirstDate.equals("1992")) {
                mYear1 = "92";
            } else if (mFirstDate.equals("1993")) {
                mYear1 = "93";
            } else if (mFirstDate.equals("1994")) {
                mYear1 = "94";
            } else if (mFirstDate.equals("1995")) {
                mYear1 = "95";
            } else if (mFirstDate.equals("1996")) {
                mYear1 = "96";
            } else if (mFirstDate.equals("1997")) {
                mYear1 = "97";
            } else if (mFirstDate.equals("1998")) {
                mYear1 = "98";
            } else if (mFirstDate.equals("1999")) {
                mYear1 = "99";
            } else if (mFirstDate.equals("2000")) {
                mYear1 = "00";
            } else if (mFirstDate.equals("2001")) {
                mYear1 = "01";
            } else if (mFirstDate.equals("2002")) {
                mYear1 = "02";
            } else if (mFirstDate.equals("2003")) {
                mYear1 = "03";
            } else if (mFirstDate.equals("2004")) {
                mYear1 = "04";
            } else if (mFirstDate.equals("2005")) {
                mYear1 = "05";
            } else if (mFirstDate.equals("2006")) {
                mYear1 = "06";
            } else if (mFirstDate.equals("2007")) {
                mYear1 = "07";
            } else if (mFirstDate.equals("2008")) {
                mYear1 = "08";
            } else if (mFirstDate.equals("2009")) {
                mYear1 = "09";
            } else if (mFirstDate.equals("2010")) {
                mYear1 = "10";
            } else if (mFirstDate.equals("2011")) {
                mYear1 = "11";
            } else if (mFirstDate.equals("2012")) {
                mYear1 = "12";
            } else if (mFirstDate.equals("2013")) {
                mYear1 = "13";
            } else if (mFirstDate.equals("2014")) {
                mYear1 = "14";
            } else if (mFirstDate.equals("2015")) {
                mYear1 = "15";
            } else if (mFirstDate.equals("2016")) {
                mYear1 = "16";
            } else {
                mYear1 = "00";
            }

            if (mSecondDate.equals("1990")) {
                mYear2 = "90";
            } else if (mSecondDate.equals("1991")) {
                mYear2 = "91";
            } else if (mSecondDate.equals("1992")) {
                mYear2 = "92";
            } else if (mSecondDate.equals("1993")) {
                mYear2 = "93";
            } else if (mSecondDate.equals("1994")) {
                mYear2 = "94";
            } else if (mSecondDate.equals("1995")) {
                mYear2 = "95";
            } else if (mSecondDate.equals("1996")) {
                mYear2 = "96";
            } else if (mSecondDate.equals("1997")) {
                mYear2 = "97";
            } else if (mSecondDate.equals("1998")) {
                mYear2 = "98";
            } else if (mSecondDate.equals("1999")) {
                mYear2 = "99";
            } else if (mSecondDate.equals("2000")) {
                mYear2 = "00";
            } else if (mSecondDate.equals("2001")) {
                mYear2 = "01";
            } else if (mSecondDate.equals("2002")) {
                mYear2 = "02";
            } else if (mSecondDate.equals("2003")) {
                mYear2 = "03";
            } else if (mSecondDate.equals("2004")) {
                mYear2 = "04";
            } else if (mSecondDate.equals("2005")) {
                mYear2 = "05";
            } else if (mSecondDate.equals("2006")) {
                mYear2 = "06";
            } else if (mSecondDate.equals("2007")) {
                mYear2 = "07";
            } else if (mSecondDate.equals("2008")) {
                mYear2 = "08";
            } else if (mSecondDate.equals("2009")) {
                mYear2 = "09";
            } else if (mSecondDate.equals("2010")) {
                mYear2 = "10";
            } else if (mSecondDate.equals("2011")) {
                mYear2 = "11";
            } else if (mSecondDate.equals("2012")) {
                mYear2 = "12";
            } else if (mSecondDate.equals("2013")) {
                mYear2 = "13";
            } else if (mSecondDate.equals("2014")) {
                mYear2 = "14";
            } else if (mSecondDate.equals("2015")) {
                mYear2 = "15";
            } else if (mSecondDate.equals("2016")) {
                mYear2 = "16";
            } else if (mSecondDate.equals("2017")) {
                mYear2 = "17";
            } else {
                mYear2 = "00";
            }

            if (mCityName.equals("Łódź")) {
                mFileName = "LDZ";
                mZweryfikowany = "Zweryfikowane";
            } else if (mCityName.equals("Łeba")) {
                mFileName = "LEB";
                mZweryfikowany = "Zweryfikowane";
            } else if (mCityName.equals("Śnieżka")) {
                mFileName = "SNZ";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Bielsko-Biała")) {
                mFileName = "BBA";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Gdynia")) {
                mFileName = "GDY";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Gorzów Wielkopolski")) {
                mFileName = "GOW";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Jelenia Góra")) {
                mFileName = "JEG";
                mZweryfikowany = "Zweryfikowane";
            } else if (mCityName.equals("Kasprowy Wierch")) {
                mFileName = "KAW";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Kołobrzeg")) {
                mFileName = "KLB";
                mZweryfikowany = "Zweryfikowane";
            } else if (mCityName.equals("Warszawa Bielany")) {
                mFileName = "WAB";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Legnica")) {
                mFileName = "LEG";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Toruń")) {
                mFileName = "TOR";
                mZweryfikowany = "Zweryfikowane";
            } else if (mCityName.equals("Wieluń")) {
                mFileName = "GSA";
                mZweryfikowany = "zweryfikowane";
            } else if (mCityName.equals("Zakopane")) {
                mFileName = "ZAK";
                mZweryfikowany = "zweryfikowane";
            } else {
                mFileName = "NoCity";
                mZweryfikowany = "NoCity";
            }

            //Adresy URL dla pierwszej daty
            URL_FirstDate = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "01", mYear1, link_w);
            URL_FirstDate2 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "02", mYear1, link_w);
            URL_FirstDate3 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "03", mYear1, link_w);
            URL_FirstDate4 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "04", mYear1, link_w);
            URL_FirstDate5 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "05", mYear1, link_w);
            URL_FirstDate6 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "06", mYear1, link_w);
            URL_FirstDate7 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "07", mYear1, link_w);
            URL_FirstDate8 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "08", mYear1, link_w);
            URL_FirstDate9 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "09", mYear1, link_w);
            URL_FirstDate10 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "10", mYear1, link_w);
            URL_FirstDate11 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "11", mYear1, link_w);
            URL_FirstDate12 = makeURL(mCityName, mZweryfikowany, mFirstDate, mFileName, "12", mYear1, link_w);


            //Adresy URL dla drugiej daty
            URL_SecondDate = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "01", mYear2, link_w);
            URL_SecondDate2 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "02", mYear2, link_w);
            URL_SecondDate3 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "03", mYear2, link_w);
            URL_SecondDate4 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "04", mYear2, link_w);
            URL_SecondDate5 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "05", mYear2, link_w);
            URL_SecondDate6 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "06", mYear2, link_w);
            URL_SecondDate7 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "07", mYear2, link_w);
            URL_SecondDate8 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "08", mYear2, link_w);
            URL_SecondDate9 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "09", mYear2, link_w);
            URL_SecondDate10 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "10", mYear2, link_w);
            URL_SecondDate11 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "11", mYear2, link_w);
            URL_SecondDate12 = makeURL(mCityName, mZweryfikowany, mSecondDate, mFileName, "12", mYear2, link_w);

            //Pobieranie danych dla pierwszej daty
            float firstDate1 = getData(URL_FirstDate, document, mResultFirstDate);
            float firstDate2 = getData(URL_FirstDate2, document, mResultFirstDate2);
            float firstDate3 = getData(URL_FirstDate3, document, mResultFirstDate3);
            float firstDate4 = getData(URL_FirstDate4, document, mResultFirstDate4);
            float firstDate5 = getData(URL_FirstDate5, document, mResultFirstDate5);
            float firstDate6 = getData(URL_FirstDate6, document, mResultFirstDate6);
            float firstDate7 = getData(URL_FirstDate7, document, mResultFirstDate7);
            float firstDate8 = getData(URL_FirstDate8, document, mResultFirstDate8);
            float firstDate9 = getData(URL_FirstDate9, document, mResultFirstDate9);
            float firstDate10 = getData(URL_FirstDate10, document, mResultFirstDate10);
            float firstDate11 = getData(URL_FirstDate11, document, mResultFirstDate11);
            float firstDate12 = getData(URL_FirstDate12, document, mResultFirstDate12);

            //Pobieranie danych dla drugiej daty
            float secondDate1 = getData(URL_SecondDate, document, mResultSecondDate);
            float secondDate2 = getData(URL_SecondDate2, document, mResultSecondDate2);
            float secondDate3 = getData(URL_SecondDate3, document, mResultSecondDate3);
            float secondDate4 = getData(URL_SecondDate4, document, mResultSecondDate4);
            float secondDate5 = getData(URL_SecondDate5, document, mResultSecondDate5);
            float secondDate6 = getData(URL_SecondDate6, document, mResultSecondDate6);
            float secondDate7 = getData(URL_SecondDate7, document, mResultSecondDate7);
            float secondDate8 = getData(URL_SecondDate8, document, mResultSecondDate8);
            float secondDate9 = getData(URL_SecondDate9, document, mResultSecondDate9);
            float secondDate10 = getData(URL_SecondDate10, document, mResultSecondDate10);
            float secondDate11 = getData(URL_SecondDate11, document, mResultSecondDate11);
            float secondDate12 = getData(URL_SecondDate12, document, mResultSecondDate12);

            System.out.println("TEST: " + noData);


            float finaResultFirstDate = firstDate1 + firstDate2 + firstDate3 + firstDate4 + firstDate5 +
                    firstDate6 + firstDate7 + firstDate8 + firstDate9 + firstDate10 + firstDate11 + firstDate12;
            float finalResultSecondDate = secondDate1 + secondDate2 + secondDate3 + secondDate4 + secondDate5 + secondDate6
                    + secondDate7 + secondDate8 + secondDate9 + secondDate10 + secondDate11 + secondDate12;
            float finalResult;

            if (finaResultFirstDate > finalResultSecondDate) {
                finalResult = 100 - ((finalResultSecondDate * 100) / finaResultFirstDate);

                if (noData == true) {
                    mResult.setText("Wynik niepewny.\nWartość rocznej uśrednionej wartości całkowitego promieniowania " +
                            "zmniejszyła się o " + new DecimalFormat("###.##").format(finalResult) + " %.");
                } else {
                    mResult.setText("Wartość rocznej uśrednionej wartości całkowitego promieniowania " +
                            "zmniejszyła się o " + new DecimalFormat("###.##").format(finalResult) + " %.");
                }

            } else {
                finalResult = 100 - ((finaResultFirstDate * 100) / finalResultSecondDate);

                if (noData == true) {
                    mResult.setText("Wynik niepewny.\nWartość rocznej uśrednionej wartości całkowitego promieniowania " +
                            "zwiększyła się o " + new DecimalFormat("###.##").format(finalResult) + " %.");
                } else {
                    mResult.setText("Wartość rocznej uśrednionej wartości całkowitego promieniowania " +
                            "zwiększyła się o " + new DecimalFormat("###.##").format(finalResult) + " %.");
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    /*
     * Funkcja sprawdza podany link. Jeśli istnieje to pobiera dane i zapisuje w podanym
     * TextView. Natomiast jeśli nie istnieje to ustawia text na "brak danych".
     */
    @SuppressLint("ResourceAsColor")
    public static float getData(String URL, Document document, TextView textView) {

        float averageData = 0;
        int twelveHour = 27;
        int day_counter = 19;

        try {
            Connection.Response response = Jsoup.connect(URL)
                    .timeout(10000)
                    .ignoreHttpErrors(true)
                    .execute();

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                document = Jsoup.connect(URL).get();
                org.jsoup.select.Elements elements;
                elements = document.getElementsByTag("Body");

                String s = elements.toString();
                String[] splitStr = s.split("\\s+");

                for (int i = 0; i < splitStr.length; i++) {
                    if (splitStr[i].equals("CM12-890154") || splitStr[i].equals("DR03\\78") ||
                            splitStr[i].equals("900197") || splitStr[i].equals("DR02\\38") ||
                            splitStr[i].equals("DR02\\50") || splitStr[i].equals("58") ||
                            splitStr[i].equals("DR02\\58") || splitStr[i].equals("DR02\\48") ||
                            splitStr[i].equals("80-70420") || splitStr[i].equals("740145") ||
                            splitStr[i].equals("DR03/75") || splitStr[i].equals("DR03\\75") ||
                            splitStr[i].equals("DR02\\49") || splitStr[i].equals("DR02\\29") ||
                            splitStr[i].equals("DR02/29") || splitStr[i].equals("7415323.") ||
                            splitStr[i].equals("74") || splitStr[i].equals("cc12") ||
                            splitStr[i].equals("12") || splitStr[i].equals("CC12") ||
                            splitStr[i].equals("xxxxxxx") || splitStr[i].equals("87_7123") ||
                            splitStr[i].equals("87-7123") || splitStr[i].equals("7524204") ||
                            splitStr[i].equals("7415323") || splitStr[i].equals("CC-12") ||
                            splitStr[i].equals("DR03\\74") || splitStr[i].equals("75-24204") ||
                            splitStr[i].equals("DR02\\44") || splitStr[i].equals("770344") ||
                            splitStr[i].equals("770342") || splitStr[i].equals("CC42") ||
                            splitStr[i].equals("..KFAP.") || splitStr[i].equals(".KFAP..") ||
                            splitStr[i].equals("...-...") || splitStr[i].equals("80-70419") ||
                            splitStr[i].equals("DR03\\80") || splitStr[i].equals("DR02/46") ||
                            splitStr[i].equals("DR02\\46") || splitStr[i].equals("DR02\\36") ||
                            splitStr[i].equals("COMBILOG") || splitStr[i].equals("DR02\\43") ||
                            splitStr[i].equals("12-890154") || splitStr[i].equals("ReIntegratorCM12890154") ||
                            splitStr[i].equals("CM12890154") || splitStr[i].equals("770349.") ||
                            splitStr[i].equals("770348..") || splitStr[i].equals(".770348") ||
                            splitStr[i].equals("770348.") || splitStr[i].equals("75-24218") ||
                            splitStr[i].equals(".......") || splitStr[i].equals("770348") ||
                            splitStr[i].equals("7524218") || splitStr[i].equals("DR03\\71") ||
                            splitStr[i].equals("DR02\\??") || splitStr[i].equals("DR02\\39") ||
                            splitStr[i].equals("DR02\\40") || splitStr[i].equals("79-570772") ||
                            splitStr[i].equals("79-57077") || splitStr[i].equals("7633939") ||
                            splitStr[i].equals("integratorCM12-890154") ||
                            splitStr[i].equals("IntegratorCM12-890154") || splitStr[i].equals("7957080") ||
                            splitStr[i].equals("DR03\\72") || splitStr[i].equals("CM-890154")) {
                        //if ((splitStr[i].equals("21") && splitStr[i + 1].equals("1")) || (splitStr[i].equals("21") && splitStr[i + 1].equals("01"))) {
                        for (int j = 0; j < 28; j++) {
                            averageData += Integer.parseInt(splitStr[i + 1 + twelveHour + j * day_counter]);
                            //System.out.println("TEST: " + averageData);
                        }
                        break;
                    }
                }

                averageData = averageData / 28;
                textView.setText("" + new DecimalFormat("##.##").format(averageData));
            } else {
                textView.setTextColor(Color.rgb(255, 0, 0));
                textView.setText("brak danych");
                noData = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return averageData / 28;
    }

    /*
     * Funckja tworzy link URL do strony.
     */
    public String makeURL(String cityName, String zweryfikowany, String date, String shortCityName, String month, String shortYear, String link_w) {

        String link_dan = ".DAN";

        if (cityName.equals("Łódź")) {
            if (Integer.parseInt(date) <= 1995) {
                link_w = "_w";
            } else {
                link_w = "";
            }

            if (Integer.parseInt(date) == 2012 && (month == "01" || month == "07")) {
                link_dan = ".dan";
            } else {
                link_dan = ".DAN";
            }
        } else if (cityName.equals("Łeba")) {
            if (Integer.parseInt(date) <= 1995) {
                link_w = "_w";
            } else {
                link_w = "";
            }
        } else if (cityName.equals("Śnieżka")) {
            if (Integer.parseInt(date) == 2000) {
                link_w = "_w";
            } else {
                link_w = "";
            }
        } else if (cityName.equals("Bielsko-Biała")) {
            if (Integer.parseInt(date) <= 1999) {
                link_w = "_w";
            } else {
                link_w = "";
            }
        } else if (cityName.equals("Gdynia")) {
            if (Integer.parseInt(date) <= 1999) {
                link_w = "_w";
            } else if (Integer.parseInt(date) >= 2014) {
                link_w = "cg";
            } else {
                link_w = "";
            }

            if (Integer.parseInt(date) == 1995 && (month == "01" || month == "02")) {
                link_w = "";
            } else if (Integer.parseInt(date) == 1996 && (month == "01" || month == "02" || month == "05")) {
                link_w = "";
            }

            if (Integer.parseInt(date) == 2008 && month == "05") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2014 && month == "08") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2015 && (month == "03" || month == "07" || month == "08" || month == "09")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2016 && (month == "02" || month == "03" || month == "07" || month == "08")) {
                link_dan = ".dan";
            } else {
                link_dan = ".DAN";
            }
        } else if (cityName.equals("Gorzów Wielkopolski")) {
            link_w = "";

            if (Integer.parseInt(date) == 2008 && (month == "02" || month == "05" || month == "07")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2010 && (month == "03" || month == "04" || month == "05"
                    || month == "06" || month == "07" || month == "08" || month == "09" || month == "12")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2011 && month != "09") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2012 && month != "04") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2013 && (month == "01" || month == "02" || month == "03"
                    || month == "04" || month == "05" || month == "06" || month == "07" || month == "08")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2014 && month == "06") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2015 && Integer.parseInt(month) >= 3) {
                link_dan = ".dan";
            } else {
                link_dan = ".DAN";
            }
        } else if (cityName.equals("Jelenia Góra")) {
            link_w = "_w";

            if (Integer.parseInt(date) > 2000) {
                link_dan = ".dan";
                link_w = "";
            }

            if (Integer.parseInt(date) == 2008 && (month == "09" || month == "12")) {
                link_dan = ".DAN";
            } else if (Integer.parseInt(date) == 2009 && month != "01") {
                link_dan = ".DAN";
            } else if (Integer.parseInt(date) >= 2010) {
                link_dan = ".DAN";
            }

            if (Integer.parseInt(date) == 2012 && month == "10") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2015 && (month == "11" || month == "12")) {
                link_w = "c";
            } else if (Integer.parseInt(date) == 2015 && month == "10") {
                link_dan = ".dan";
            }

            if (Integer.parseInt(date) == 2009 && (month == "03" || month == "06" || month == "08" || month == "10" || month == "12")) {
                shortCityName = "Jeg";
            } else if (Integer.parseInt(date) == 2009 && month == "11") {
                shortCityName = "jEG";
            } else if (Integer.parseInt(date) == 2010 && (month == "01" || month == "05")) {
                shortCityName = "Jeg";
            }
        } else if (cityName.equals("Kasprowy Wierch")) {
            if (Integer.parseInt(date) == 2000) {
                link_w = "_w";
            } else {
                link_w = "";
            }

            if (Integer.parseInt(date) == 2007 && month == "05") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2008 && (month == "01" || month == "02")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2009 && month == "09") {
                link_dan = ".dan";
            }
        } else if (cityName.equals("Kołobrzeg")) {
            if (Integer.parseInt(date) <= 2000) {
                link_w = "_w";
            } else {
                link_w = "";
            }

            if (Integer.parseInt(date) == 2015 && month == "08") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2016 && (month == "07" || month == "10")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2011 && month == "06") {
                link_dan = ".dan";
            }

            if (Integer.parseInt(date) == 2016 && Integer.parseInt(month) >= 10) {
                link_w = "cg";
            } else if (Integer.parseInt(date) == 2017) {
                link_w = "cg";
            }

            if (Integer.parseInt(date) == 2007) {
                shortCityName = "klb";
                link_dan = ".dan";
            }
        } else if (cityName.equals("Warszawa Bielany")) {

            if (Integer.parseInt(date) <= 2000) {
                link_w = "_w";
            } else {
                link_w = "";
            }

            if (Integer.parseInt(date) == 2001 || Integer.parseInt(date) == 2002) {
                link_dan = ".dan";
                shortCityName = "Wab";
            }

            if (Integer.parseInt(date) == 2002 && (month == "10" || month == "11")) {
                link_dan = ".DAN";
            } else if (Integer.parseInt(date) == 2002 && month == "08") {
                shortCityName = "wab";
            } else if (Integer.parseInt(date) == 2006 && month == "06") {
                shortCityName = "wab";
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2008 && (month == "07" || month == "08")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2009 && (month == "02" || month == "03" || month == "04" ||
                    month == "05" || month == "06" || month == "07" || month == "10" || month == "11" || month == "12")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2010) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2011 && (month == "01" || month == "02" || month == "03" ||
                    month == "06" || month == "07" || month == "09" || month == "11" || month == "12")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2012 && (month == "01" || month == "02" || month == "03" ||
                    month == "06" || month == "07" || month == "08" || month == "09" || month == "10" || month == "11" || month == "12")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2013 && month != "01") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2014 && (month == "01" || month == "02" || month == "03" ||
                    month == "05" || month == "08" || month == "09" || month == "10")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2015 && month != "03") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2016 && (month == "01" || month == "03" || month == "04" ||
                    month == "05" || month == "06" || month == "07" || month == "08" || month == "12")) {
                link_dan = ".dan";
            }
        } else if (cityName.equals("Legnica")) {
            if (Integer.parseInt(date) <= 1999) {
                link_w = "_w";
            } else {
                link_w = "";
            }

            if (Integer.parseInt(date) == 2007 && month == "07") {
                link_w = "uzupel bez QKDS";
            } else if (Integer.parseInt(date) == 2007 && month == "08") {
                link_w = "uzupel";
            } else if (Integer.parseInt(date) == 2008 && (month == "04" || month == "05" ||
                    month == "06" || month == "07" || month == "08" || month == "11")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2009 && (month == "02" || month == "04" ||
                    month == "09" || month == "10" || month == "11" || month == "12")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2010) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2011 && (month == "01" || month == "02" ||
                    month == "03")) {
                link_dan = ".dan";
            }
        } else if (cityName.equals("Toruń")) {
            link_w = "";
            link_dan = ".DAN";
        } else if (cityName.equals("Wieluń")) {
            link_w = "";
            link_dan = ".DAN";

            if (Integer.parseInt(date) == 2008 && month == "07") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2009 && month == "02") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2010 && month == "11") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2013) {
                link_w = "_w";
            } else if (Integer.parseInt(date) == 2014 && month == "07") {
                link_dan = ".dan";
            }
        } else if (cityName.equals("Zakopane")) {
            if (Integer.parseInt(date) <= 2000) {
                link_w = "_w";
            } else {
                link_w = "";
            }

            if (Integer.parseInt(date) == 2008 && month == "07") {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2008 && month == "05") {
                shortCityName = "Zak";
            } else if (Integer.parseInt(date) == 2015 && (month == "07" || month == "08")) {
                link_dan = ".dan";
            } else if (Integer.parseInt(date) == 2016 || Integer.parseInt(date) == 2017) {
                link_w = "cg";
            }

            if (Integer.parseInt(date) == 2016 && (month == "07" || month == "08" || month == "12")) {
                link_dan = ".dan";
            }
        }


        String URL = "https://danepubliczne.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_aktynometryczne/" +
                cityName + "/" + zweryfikowany + "/" + date + "/" + shortCityName + month + "-" + shortYear + link_w + link_dan;

        return URL;
    }
}