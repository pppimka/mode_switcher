package ua.kpi.its.mode_sw;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MyActivity extends Activity {

    TimePicker TPSilence;
    TimePicker TPUnsilence;
    SharedPreferences preferences;
    final String TIME_START_HOUR = "time_start_hour";
    final String TIME_START_MINUTE = "time_start_minute";
    final String TIME_END_HOUR = "time_end_hour";
    final String TIME_END_MINUTE = "time_end_minute";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        Button schedule = (Button) findViewById(R.id.start);
        Button unschedule = (Button) findViewById(R.id.end);

        TPSilence = (TimePicker) findViewById(R.id.SetSilenceTime);
        TPUnsilence = (TimePicker) findViewById(R.id.SetUnsilenceTime);

        /**
         * Make instance of SharedPreferences to save information about time
         * when to start and end silence mode
         * and make this information private
         */
        preferences = getSharedPreferences("goodnight", MODE_PRIVATE);

        /**
         * If the time of change mode is set, put it's value into TimePicker
         */
        if(preferences.contains(TIME_START_HOUR) && preferences.contains(TIME_START_MINUTE)
                && preferences.contains(TIME_END_HOUR) && preferences.contains(TIME_END_MINUTE)) {
            TPSilence.setCurrentHour(preferences.getInt(TIME_START_HOUR, 1));
            TPSilence.setCurrentMinute(preferences.getInt(TIME_START_MINUTE, 1));
            TPUnsilence.setCurrentHour(preferences.getInt(TIME_END_HOUR, 1));
            TPUnsilence.setCurrentMinute(preferences.getInt(TIME_END_MINUTE, 1));
        }

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /**
                 * Set user's settings into variable of SharedPreferences
                 */
                SharedPreferences.Editor ed = preferences.edit();
                ed.putInt(TIME_START_HOUR, TPSilence.getCurrentHour());
                ed.putInt(TIME_START_MINUTE, TPSilence.getCurrentMinute());
                ed.putInt(TIME_END_HOUR, TPUnsilence.getCurrentHour());
                ed.putInt(TIME_END_MINUTE, TPUnsilence.getCurrentMinute());
                ed.commit();

                /**
                 * create new calendar instance
                 */
                Calendar midnightCalendar = Calendar.getInstance();

                /**
                 * set the time to midnight tonight
                 */
                midnightCalendar.set(Calendar.HOUR_OF_DAY, TPSilence.getCurrentHour());
                midnightCalendar.set(Calendar.MINUTE, TPSilence.getCurrentMinute());
                midnightCalendar.set(Calendar.SECOND, 0);

                /**
                 * create new alarm manager instance
                 */
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                /**
                 * create a pending intent to be called at midnight
                 */
                Intent alarmIntentSilence = new Intent(MyActivity.this, SilenceBroadcastReceiver.class);
                PendingIntent midnightPI = PendingIntent.getBroadcast(MyActivity.this, 0, alarmIntentSilence,0);

                /**
                 * schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                 */
                am.setRepeating(AlarmManager.RTC_WAKEUP, midnightCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, midnightPI);

                Calendar sixCalendar = Calendar.getInstance();

                /**
                 * set the time to end silence mode
                 */
                sixCalendar.set(Calendar.HOUR_OF_DAY, TPUnsilence.getCurrentHour());
                sixCalendar.set(Calendar.MINUTE, TPUnsilence.getCurrentMinute());
                sixCalendar.set(Calendar.SECOND, 15);

                /**
                 * create a pending intent to be called at end of silence mode
                 */
                Intent alarmIntentUp = new Intent(MyActivity.this, UnsilenceBroadcastReceiver.class);
                PendingIntent sixPI = PendingIntent.getBroadcast(MyActivity.this, 0, alarmIntentUp, 0);

                /**
                 * schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                 */
                am.setRepeating(AlarmManager.RTC_WAKEUP, sixCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sixPI);
            }
        });


        unschedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /**
                 * get Alarm manager instance
                 */
                AlarmManager am = (AlarmManager) MyActivity.this.getSystemService(ALARM_SERVICE);

                /**
                 * Clear user's settings
                 */
                preferences = getSharedPreferences("goodnight", MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();
                ed.clear();
                ed.commit();

                /**
                 * build intent for midnight
                 */
                Intent alarmIntentSilence = new Intent(MyActivity.this, SilenceBroadcastReceiver.class);
                PendingIntent midnightPI = PendingIntent.getBroadcast(MyActivity.this, 0, alarmIntentSilence,0);

                /**
                 * cancel it
                 */
                am.cancel(midnightPI);

                /**
                 * build intent for 6 AM
                 */
                Intent alarmIntentUp = new Intent(MyActivity.this, UnsilenceBroadcastReceiver.class);
                PendingIntent sixPI = PendingIntent.getBroadcast(MyActivity.this, 0, alarmIntentUp, 0);

                /**
                 * cancel it
                 */
                am.cancel(sixPI);
            }
        });

        }

}



