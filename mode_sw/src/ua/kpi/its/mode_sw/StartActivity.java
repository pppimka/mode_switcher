package ua.kpi.its.mode_sw;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by ann_ on 27.11.2014.
 */
public class StartActivity extends Activity {

    Button changeSettings;
    TextView timeStart;
    TextView timeEnd;
    SharedPreferences preferences;
    final String TIME_START_HOUR = "time_start_hour";
    final String TIME_START_MINUTE = "time_start_minute";
    final String TIME_END_HOUR = "time_end_hour";
    final String TIME_END_MINUTE = "time_end_minute";
    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;
    CheckBox sunday;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        changeSettings = (Button) findViewById(R.id.changeSettings);
        timeStart = (TextView) findViewById(R.id.timeStart);
        timeEnd = (TextView) findViewById(R.id.timeEnd);
        monday = (CheckBox) findViewById(R.id.setMonday);
        tuesday = (CheckBox) findViewById(R.id.setTuesday);
        wednesday = (CheckBox) findViewById(R.id.setWednesday);
        thursday = (CheckBox) findViewById(R.id.setThursday);
        friday = (CheckBox) findViewById(R.id.setFriday);
        saturday = (CheckBox) findViewById(R.id.setSaturday);
        sunday = (CheckBox) findViewById(R.id.setSunday);

        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                /*
                 * Go to activity where user can set it's own settings
                 */
                Intent intent = new Intent(StartActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();

        /*
         * Show user's settings in case they are set
         * in other way there is no information to be shown
         */
        preferences = getSharedPreferences("goodnight", MODE_PRIVATE);
        if(preferences.contains(TIME_START_HOUR) && preferences.contains(TIME_START_MINUTE)
                && preferences.contains(TIME_END_HOUR) && preferences.contains(TIME_END_MINUTE)) {
            int intStartHour = preferences.getInt(TIME_START_HOUR, 0);
            int intStartMinute = preferences.getInt(TIME_START_MINUTE, 0);
            int intEndHour = preferences.getInt(TIME_END_HOUR, 0);
            int intEndMinute = preferences.getInt(TIME_END_MINUTE, 0);
            String stringStart = intStartHour + ":" + intStartMinute;
            String stringEnd = intEndHour + ":" + intEndMinute;
            timeStart.setText(stringStart);
            timeEnd.setText(stringEnd);
        } else {
            timeStart.setText("-");
            timeEnd.setText("-");
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
    }
}
