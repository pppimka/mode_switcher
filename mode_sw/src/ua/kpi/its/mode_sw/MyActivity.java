package ua.kpi.its.mode_sw;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MyActivity extends Activity {

    TimePicker TPSilence;
    TimePicker TPUnsilence;
    SharedPreferences preferences;
    final String TIME_START_HOUR = "time_start_hour";
    final String TIME_START_MINUTE = "time_start_minute";
    final String TIME_END_HOUR = "time_end_hour";
    final String TIME_END_MINUTE = "time_end_minute";
    private static String ACTION = "ua.kpi.its.mode_sw.DayRepeating";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button schedule = (Button) findViewById(R.id.start);
        Button unschedule = (Button) findViewById(R.id.end);
        final CheckBox monday = (CheckBox) findViewById(R.id.monday);
        final CheckBox tuesday = (CheckBox) findViewById(R.id.tuesday);
        final CheckBox wednesday = (CheckBox) findViewById(R.id.wednesday);
        final CheckBox thursday = (CheckBox) findViewById(R.id.thursday);
        final CheckBox friday = (CheckBox) findViewById(R.id.friday);
        final CheckBox saturday = (CheckBox) findViewById(R.id.saturday);
        final CheckBox sunday = (CheckBox) findViewById(R.id.sunday);
        TPSilence = (TimePicker) findViewById(R.id.SetSilenceTime);
        TPUnsilence = (TimePicker) findViewById(R.id.SetUnsilenceTime);

        /*
        * Make instance of SharedPreferences to save information about time
        * when to start and end silence mode
        * and make this information private
        */
        preferences = getSharedPreferences("goodnight", MODE_PRIVATE);

        /*
        * If the time of change mode is set, put it's value into TimePicker
        */
        if(preferences.contains(TIME_START_HOUR) && preferences.contains(TIME_START_MINUTE)
                && preferences.contains(TIME_END_HOUR) && preferences.contains(TIME_END_MINUTE)) {
            TPSilence.setCurrentHour(preferences.getInt(TIME_START_HOUR, 1));
            TPSilence.setCurrentMinute(preferences.getInt(TIME_START_MINUTE, 1));
            TPUnsilence.setCurrentHour(preferences.getInt(TIME_END_HOUR, 1));
            TPUnsilence.setCurrentMinute(preferences.getInt(TIME_END_MINUTE, 1));
        }
        if(preferences.contains("MONDAY") && preferences.getBoolean("MONDAY", false) == true){
            monday.setChecked(true);
        }
        if(preferences.contains("TUESDAY") && preferences.getBoolean("TUESDAY", false) == true){
            tuesday.setChecked(true);
        }
        if(preferences.contains("WEDNESDAY") && preferences.getBoolean("WEDNESDAY", false) == true){
            wednesday.setChecked(true);
        }
        if(preferences.contains("THURSDAY") && preferences.getBoolean("THURSDAY", false) == true){
            thursday.setChecked(true);
        }
        if(preferences.contains("FRIDAY") && preferences.getBoolean("FRIDAY", false) == true){
            friday.setChecked(true);
        }
        if(preferences.contains("SATURDAY") && preferences.getBoolean("SATURDAY", false) == true){
            saturday.setChecked(true);
        }
        if(preferences.contains("SUNDAY") && preferences.getBoolean("SUNDAY", false) == true){
            sunday.setChecked(true);
        }

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                clearSchedule();

                SharedPreferences.Editor ed = preferences.edit();
                ed.clear();
                ed.putInt(TIME_START_HOUR, TPSilence.getCurrentHour());
                ed.putInt(TIME_START_MINUTE, TPSilence.getCurrentMinute());
                ed.putInt(TIME_END_HOUR, TPUnsilence.getCurrentHour());
                ed.putInt(TIME_END_MINUTE, TPUnsilence.getCurrentMinute());


                Intent intent = new Intent(ACTION);
                intent.putExtra("TPSilenceHour", TPSilence.getCurrentHour());
                intent.putExtra("TPUnsilenceHour", TPUnsilence.getCurrentHour());
                intent.putExtra("TPSilenceMinute", TPSilence.getCurrentMinute());
                intent.putExtra("TPUnsilenceMinute", TPUnsilence.getCurrentMinute());

                if (monday.isChecked()) {
                    intent.putExtra("ID_DAY", Calendar.MONDAY);
                    ed.putBoolean("MONDAY", true);
                    sendBroadcast(intent);
                }

                if (tuesday.isChecked()) {
                    intent.putExtra("ID_DAY", Calendar.TUESDAY);
                    ed.putBoolean("TUESDAY", true);
                    sendBroadcast(intent);
                }

                if (wednesday.isChecked()) {
                    intent.putExtra("ID_DAY",Calendar.WEDNESDAY);
                    ed.putBoolean("WEDNESDAY", true);
                    sendBroadcast(intent);
                }

                if (thursday.isChecked()) {
                    intent.putExtra("ID_DAY", Calendar.THURSDAY);
                    ed.putBoolean("THURSDAY", true);
                    sendBroadcast(intent);
                }

                if (friday.isChecked()) {
                    intent.putExtra("ID_DAY", Calendar.FRIDAY);
                    ed.putBoolean("FRIDAY", true);
                    sendBroadcast(intent);
                }

                if (saturday.isChecked()) {
                    intent.putExtra("ID_DAY", Calendar.SUNDAY);
                    ed.putBoolean("SATURDAY", true);
                    sendBroadcast(intent);
                }

                if (sunday.isChecked()) {
                    intent.putExtra("ID_DAY", Calendar.SUNDAY);
                    ed.putBoolean("SUNDAY", true);
                    sendBroadcast(intent);
                }
                ed.commit();
                Toast.makeText(MyActivity.this, "Changed settings", Toast.LENGTH_SHORT).show();
                Intent startActivity = new Intent(MyActivity.this, StartActivity.class);
                startActivity(startActivity);
            }
        });

        unschedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                preferences = getSharedPreferences("goodnight", MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();
                ed.clear();
                ed.commit();

                clearSchedule();

                Toast.makeText(MyActivity.this, "Canceled settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearSchedule(){
        AlarmManager am = (AlarmManager) MyActivity.this.getSystemService(ALARM_SERVICE);

        for (int i = 0; i < 7; i++){
            Intent alarmIntent = new Intent(MyActivity.this, SilenceBroadcastReceiver.class);
            PendingIntent cancelIntent = PendingIntent.getBroadcast(MyActivity.this, i, alarmIntent,0);
            am.cancel(cancelIntent);

            alarmIntent = new Intent(MyActivity.this, UnsilenceBroadcastReceiver.class);
            cancelIntent = PendingIntent.getBroadcast(MyActivity.this, i, alarmIntent, 0);
            am.cancel(cancelIntent);
        }
    }

}