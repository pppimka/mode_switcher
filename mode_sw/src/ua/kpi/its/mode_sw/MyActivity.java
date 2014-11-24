package ua.kpi.its.mode_sw;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button schedule = (Button) findViewById(R.id.start);
        Button unschedule = (Button) findViewById(R.id.end);

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /**
                 * create new calendar instance
                 */
                Calendar midnightCalendar = Calendar.getInstance();

                /**
                 * set the time to midnight tonight
                 */
                midnightCalendar.set(Calendar.HOUR_OF_DAY, 21);
                midnightCalendar.set(Calendar.MINUTE, 28);
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
                 * set the time to 6AM
                 */
                sixCalendar.set(Calendar.HOUR_OF_DAY, 21);
                sixCalendar.set(Calendar.MINUTE, 28);
                sixCalendar.set(Calendar.SECOND, 15);

                /**
                 * create a pending intent to be called at 6 AM
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



