package ua.kpi.its.mode_sw;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;


public class MyActivity extends Activity {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscreen);
    }


    protected void start(){
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

    }

    protected void end(){
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

    }

//    SharedPreferences preferences = getSharedPreferences("goodnight", MODE_PRIVATE);
//
//    SharedPreferences.Editor prefEditor =preferences.edit();

//    prefEditor.putBoolean("isEnabled",isEnabledCheckBox.isChecked());
//    prefEditor.putInt("startHour", startTimePicker.getCurrentHour());
//    prefEditor.putInt("startMinute", startTimePicker.getCurrentMinute());
//    prefEditor.putInt("endHour", endTimePicker.getCurrentHour());
//    prefEditor.putInt("endMinute", endTimePicker.getCurrentMinute());
//    prefEditor.commit();
//
//    public class GoodnightService extends Service {
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//            Log.i("GoodnightService", "Service created");
//        }
//        @Override
//        public void onDestroy() {
//            super.onDestroy();
//            Log.i("GoodnightService", "Service destroyed");
//        }
//
//        @Override
//        public IBinder onBind(Intent intent) {
//            return binder;
//        }
//
//        private final IGoodnightService.Stub binder = new IGoodnightService.Stub() {
//            @Override
//            public void UpdateSettings() {
//                Init();
//                Update();
//            }
//        };
//    }
//
//
//    final NotificationManager mgr = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//    Notification note = new Notification(R.drawable.status,
//            getResources().getString(R.string.startMessage),
//            System.currentTimeMillis());
//
//    note.flags |= Notification.FLAG_ONGOING_EVENT;
//    PendingIntent i = PendingIntent.getActivity(this, 0,
//            new Intent(this, Setup.class), 0);
//    String interval = String.format("%d:%02d – %d:%02d", startHour,
//            startMinute, endHour, endMinute);
//    note.setLatestEventInfo(this, interval,
//    getResources().getString(R.string.notificationMessage), i);
//
//    mgr.notify(NOTIFY_ME_ID, note);
//    mgr.cancel(NOTIFY_ME_ID);


}
