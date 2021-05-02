package com.example.smutime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.CountDownTimer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SuttleBusActivity extends AppCompatActivity {

    Spinner mspinner1;
    TextView first;
    TextView second;
    TextView third;
    String[] list = {"두정역", "학교"};
    CountDownTimer countDownTimer1;
    CountDownTimer countDownTimer2;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suttlebus_main);

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }

        /*mspinner1 = findViewById(R.id.spinner);
        first = findViewById(R.id.textView2);
        second = findViewById(R.id.textView3);
        third = findViewById(R.id.textView4);
        timer = findViewById(R.id.textView5);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        mspinner1.setAdapter(adapter);

        countDownTimer1 = new CountDownTimer(200000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(getTime1());
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer2 = new CountDownTimer(200000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(getTime2());
            }

            @Override
            public void onFinish() {

            }
        };

        mspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    first.setText("  두정역\n\n08시 10분\n\n08시 20분\n\n08시 50분\n\n09시 10분\n\n09시 40분\n\n10시 30분");
                    second.setText("  천안TG\n\n08시 20분\n\n08시 30분\n\n09시 00분\n\n09시 20분\n\n09시 50분\n\n10시 40분");
                    third.setText("   학교\n\n08시 30분\n\n08시 40분\n\n09시 10분\n\n09시 30분\n\n10시 00분\n\n10시 50분");
                    countDownTimer1.start();
                }
                else
                {
                    first.setText("   학교\n\n15시 00분\n\n15시 30분\n\n16시 00분\n\n16시 30분\n\n17시 00분\n\n17시 30분");
                    second.setText("  터미널\n\n15시 10분\n\n15시 40분\n\n16시 10분\n\n16시 40분\n\n17시 10분\n\n17시 40분");
                    third.setText("  천안역\n\n15시 20분\n\n15시 50분\n\n16시 20분\n\n16시 50분\n\n17시 20분\n\n17시 50분");
                    countDownTimer2.start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                first.setText("");
            }
        });*/

    }
    /*private String getTime1(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);

        int[] b_hour = {8, 8, 8, 9, 9, 10};
        int[] b_min = {10, 20, 50, 10, 40, 30};
        String[] b_time = {"", "", "", "", "", ""};

        for (int i = 0; i<b_hour.length; i++){
            Calendar baseCal = new GregorianCalendar(year, month, day, c_hour, c_min, c_sec);
            Calendar targetCal = new GregorianCalendar(year, month, day, b_hour[i], b_min[i] , 0);

            long timeCal = targetCal.getTimeInMillis() - baseCal.getTimeInMillis();

            if (timeCal <= 0){
                targetCal = new GregorianCalendar(year, month, day+1, b_hour[i], b_min[i], 0);
            }
            long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
            long diffDays = diffSec / (24*60*60);

            targetCal.add(Calendar.DAY_OF_MONTH, (int)(-diffDays));

            int hourTime = (int)Math.floor((double)(diffSec/3600));
            int minTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) / 60)));
            int secTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) - (60 * minTime))));

            String hour = String.format("%02d", hourTime);
            String min = String.format("%02d", minTime);
            String sec = String.format("%02d", secTime);

            b_time[i] = hour + "시간 " +min + "분 "+ sec + "초";
        }
        String bus1 = b_time[0];
        String bus2 = b_time[1];
        String bus3 = b_time[2];
        String bus4 = b_time[3];
        String bus5 = b_time[4];
        String bus6 = b_time[5];

        return "     남은 시간\n\n" + bus1 + "\n\n"+ bus2 + "\n\n" + bus3 + "\n\n" + bus4 + "\n\n" + bus5 + "\n\n"+ bus6 + "\n\n";

    }
    private String getTime2(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        int c_sec = calendar.get(Calendar.SECOND);

        int[] b_hour = {15, 15, 16, 16, 17, 17};
        int[] b_min = {0, 30, 0, 30, 0, 30};
        String[] b_time = {"", "", "", "", "", ""};

        for (int i = 0; i<b_hour.length; i++){
            Calendar baseCal = new GregorianCalendar(year, month, day, c_hour, c_min, c_sec);
            Calendar targetCal = new GregorianCalendar(year, month, day, b_hour[i], b_min[i] , 0);

            long timeCal = targetCal.getTimeInMillis() - baseCal.getTimeInMillis();

            if (timeCal <= 0){
                targetCal = new GregorianCalendar(year, month, day+1, b_hour[i], b_min[i], 0);
            }
            long diffSec = (targetCal.getTimeInMillis() - baseCal.getTimeInMillis()) / 1000;
            long diffDays = diffSec / (24*60*60);

            targetCal.add(Calendar.DAY_OF_MONTH, (int)(-diffDays));

            int hourTime = (int)Math.floor((double)(diffSec/3600));
            int minTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) / 60)));
            int secTime = (int)Math.floor((double)(((diffSec - (3600 * hourTime)) - (60 * minTime))));

            String hour = String.format("%02d", hourTime);
            String min = String.format("%02d", minTime);
            String sec = String.format("%02d", secTime);

            b_time[i] = hour + "시간 " +min + "분 "+ sec + "초";
        }
        String bus1 = b_time[0];
        String bus2 = b_time[1];
        String bus3 = b_time[2];
        String bus4 = b_time[3];
        String bus5 = b_time[4];
        String bus6 = b_time[5];

        return "     남은 시간\n\n" + bus1 + "\n\n"+ bus2 + "\n\n" + bus3 + "\n\n" + bus4 + "\n\n" + bus5 + "\n\n"+ bus6 + "\n\n";

    }*/
}