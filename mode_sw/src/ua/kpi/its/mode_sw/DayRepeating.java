package ua.kpi.its.mode_sw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by ann_ on 02.12.2014.
 */
public class DayRepeating extends BroadcastReceiver {

    private int silenceHour;
    private int silenceMinute;
    private int unsilenceHour;
    private int unsilenceMinute;
    private int idDay;


    private boolean checkNextDay(int silenceHour, int silenceMinute,
                                 int unsilenceHour, int unsilenceMinute) {
        if (silenceHour > unsilenceHour || (silenceHour == unsilenceHour &&
                                            silenceMinute > unsilenceMinute)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Receiver", "Ok");

        silenceHour = intent.getIntExtra("TPSilenceHour", 0);
        silenceMinute = intent.getIntExtra("TPSilenceMinute", 0);
        unsilenceHour = intent.getIntExtra("TPUnsilenceHour", 0);
        unsilenceMinute = intent.getIntExtra("TPUnsilenceMinute", 0);
        idDay = intent.getIntExtra("ID_DAY", 0);

        Calendar silenceCalendar = Calendar.getInstance();

        silenceCalendar.set(Calendar.DAY_OF_WEEK, idDay);
        silenceCalendar.set(Calendar.HOUR_OF_DAY, silenceHour);
        silenceCalendar.set(Calendar.MINUTE, silenceMinute);
        silenceCalendar.set(Calendar.SECOND, 0);

        AlarmManager AM = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intentSilence = new Intent(context, SilenceBroadcastReceiver.class);
        PendingIntent silencePI = PendingIntent.getBroadcast(context, idDay, intentSilence, 0);

        AM.setRepeating(AlarmManager.RTC_WAKEUP, silenceCalendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY * 7, silencePI);

        Calendar unsilenceCalendar = Calendar.getInstance();

        if (checkNextDay(silenceHour, silenceMinute, unsilenceHour, unsilenceMinute)){
            if (idDay >=7){
                unsilenceCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            } else {
                unsilenceCalendar.set(Calendar.DAY_OF_WEEK, idDay + 1);
            }
        } else {
            unsilenceCalendar.set(Calendar.DAY_OF_WEEK, idDay);
        }
        unsilenceCalendar.set(Calendar.HOUR_OF_DAY, unsilenceHour );
        unsilenceCalendar.set(Calendar.MINUTE,unsilenceMinute);
        unsilenceCalendar.set(Calendar.SECOND, 0);

        Intent unsilenceIntent = new Intent(context, UnsilenceBroadcastReceiver.class);
        PendingIntent unsilencePI = PendingIntent.getBroadcast(context, idDay, unsilenceIntent, 0);

        AM.setRepeating(AlarmManager.RTC_WAKEUP, unsilenceCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, unsilencePI);

    }
}
