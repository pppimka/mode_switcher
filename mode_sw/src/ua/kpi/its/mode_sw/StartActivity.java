package ua.kpi.its.mode_sw;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        changeSettings = (Button) findViewById(R.id.changeSettings);
        timeStart = (TextView) findViewById(R.id.timeStart);
        timeEnd = (TextView) findViewById(R.id.timeEnd);


        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                /**
                 * Go to activity where user can set it's own settings
                 */
                Intent intent = new Intent(StartActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();

        /**
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
    }
}
