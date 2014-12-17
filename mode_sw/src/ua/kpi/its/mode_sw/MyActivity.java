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

    /**
     * String that saves value where could be found user's
     * value of hour when silence mode should be started
     */
    final static String TIME_START_HOUR = "time_start_hour";

    /**
     * String that saves value where could be found user's
     * value of minutes when silence mode should be started
     */
    final static String TIME_START_MINUTE = "time_start_minute";

    /**
     * String that saves value where could be found user's
     * value of hour when unsilence mode should be started
     */
    final static String TIME_END_HOUR = "time_end_hour";

    /**
     * String that saves value where could be found user's
     * value of minutes when unsilence mode should be started
     */
    final static String TIME_END_MINUTE = "time_end_minute";

    /**
     * String that points what action should be done
     */
    private static String ACTION = "ua.kpi.its.mode_sw.DayRepeating";

    /**
     * Variable saves Time Picker from XML file
     * Value of time ro set silence mode
     */
    TimePicker TPSilence;

    /**
     * Variable saves Time Picker from XML file
     * Value of time ro set unsilence mode
     */
    TimePicker TPUnsilence;

    /**
     * Save all user's settings
     */
    static SharedPreferences preferences;

    /**
     * CheckBox contains if monday box is checked
     */
    private CheckBox monday;

    /**
     * CheckBox contains if tuesday box is checked
     */
    private CheckBox tuesday;

    /**
     * CheckBox contains if wednesday box is checked
     */
    private CheckBox wednesday;

    /**
     * CheckBox contains if thursday box is checked
     */
    private CheckBox thursday;

    /**
     * CheckBox contains if friday box is checked
     */
    private CheckBox friday;

    /**
     * CheckBox contains if saturday box is checked
     */
    private CheckBox saturday;

    /**
     * CheckBox contains if sunday box is checked
     */
    private CheckBox sunday;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        // Save all buttons in it's variables
        Button schedule = (Button) findViewById(R.id.start);
        Button unschedule = (Button) findViewById(R.id.end);
        monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
        wednesday = (CheckBox) findViewById(R.id.wednesday);
        thursday = (CheckBox) findViewById(R.id.thursday);
        friday = (CheckBox) findViewById(R.id.friday);
        saturday = (CheckBox) findViewById(R.id.saturday);
        sunday = (CheckBox) findViewById(R.id.sunday);
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
        /*
         * If user's settings contain some day of repeating
         * set checkBoxes of these days checked
         */
        if(preferences.contains("MONDAY") && preferences.getBoolean("MONDAY", false)){
            monday.setChecked(true);
        }
        if(preferences.contains("TUESDAY") && preferences.getBoolean("TUESDAY", false)){
            tuesday.setChecked(true);
        }
        if(preferences.contains("WEDNESDAY") && preferences.getBoolean("WEDNESDAY", false)){
            wednesday.setChecked(true);
        }
        if(preferences.contains("THURSDAY") && preferences.getBoolean("THURSDAY", false)){
            thursday.setChecked(true);
        }
        if(preferences.contains("FRIDAY") && preferences.getBoolean("FRIDAY", false)){
            friday.setChecked(true);
        }
        if(preferences.contains("SATURDAY") && preferences.getBoolean("SATURDAY", false)){
            saturday.setChecked(true);
        }
        if(preferences.contains("SUNDAY") && preferences.getBoolean("SUNDAY", false)){
            sunday.setChecked(true);
        }

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Before save new information clear old
                clearSchedule();

                //Save new user's information in variable
                SharedPreferences.Editor ed = preferences.edit();
                ed.clear();
                ed.putInt(TIME_START_HOUR, TPSilence.getCurrentHour());
                ed.putInt(TIME_START_MINUTE, TPSilence.getCurrentMinute());
                ed.putInt(TIME_END_HOUR, TPUnsilence.getCurrentHour());
                ed.putInt(TIME_END_MINUTE, TPUnsilence.getCurrentMinute());


                // Save information in intent variable about time
                Intent intent = new Intent(ACTION);
                intent.putExtra("TPSilenceHour", TPSilence.getCurrentHour());
                intent.putExtra("TPUnsilenceHour", TPUnsilence.getCurrentHour());
                intent.putExtra("TPSilenceMinute", TPSilence.getCurrentMinute());
                intent.putExtra("TPUnsilenceMinute", TPUnsilence.getCurrentMinute());

                /*
                 * If user check day when program should change mode
                 * Set repeating of function at these days
                 */
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
                // Save information about time and days in user's variable
                ed.commit();
                // Show user small information window about changes that was done
                Toast.makeText(MyActivity.this, "Changed settings", Toast.LENGTH_SHORT).show();
                // Go to Start Acrivity where user can see it's own settings
                Intent startActivity = new Intent(MyActivity.this, StartActivity.class);
                startActivity(startActivity);
            }
        });

        /*
         * Function that will be done when user click on button unscadule
         * It will clear all user's settings and cancel all repeating
         */
        unschedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Get variable about user's information
                preferences = getSharedPreferences("goodnight", MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();
                // Clear user's information
                ed.clear();
                ed.commit();

                // Cancel  schedule
                clearSchedule();
                // Show user small information window about changes that was done
                Toast.makeText(MyActivity.this, "Canceled settings", Toast.LENGTH_SHORT).show();
                // Go to Start Acrivity where user can see it's own settings
                Intent intent = new Intent(MyActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Function that cancel all repeating
     * from all days of the week
     */
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