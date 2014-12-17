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

    /**
     * Button that change this activity to activity where user can set his own settings
     */
    private Button changeSettings;

    /**
     * Text that show time where silence mode is starts
     */
    private TextView timeStart;

    /**
     * Text that show time where unsilence mode is starts
     */
    private TextView timeEnd;

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
        setContentView(R.layout.first_page);

        // Save all buttons in it's variables
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

                // Go to activity where user can set it's own settings
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
        MyActivity.preferences = getSharedPreferences("goodnight", MODE_PRIVATE);
        if(MyActivity.preferences.contains(MyActivity.TIME_START_HOUR) && MyActivity.preferences.contains(MyActivity.TIME_START_MINUTE)
                && MyActivity.preferences.contains(MyActivity.TIME_END_HOUR) && MyActivity.preferences.contains(MyActivity.TIME_END_MINUTE)) {
            int intStartHour = MyActivity.preferences.getInt(MyActivity.TIME_START_HOUR, 0);
            int intStartMinute = MyActivity.preferences.getInt(MyActivity.TIME_START_MINUTE, 0);
            int intEndHour = MyActivity.preferences.getInt(MyActivity.TIME_END_HOUR, 0);
            int intEndMinute = MyActivity.preferences.getInt(MyActivity.TIME_END_MINUTE, 0);
            String stringStart = intStartHour + ":" + intStartMinute;
            String stringEnd = intEndHour + ":" + intEndMinute;
            timeStart.setText(stringStart);
            timeEnd.setText(stringEnd);
        } else {
            timeStart.setText("-");
            timeEnd.setText("-");
        }
        /*
         * If user's settings contain some day of repeating
         * set checkBoxes of these days checked
         */
        if(MyActivity.preferences.contains("MONDAY") && MyActivity.preferences.getBoolean("MONDAY", false)){
            monday.setChecked(true);
        }
        if(MyActivity.preferences.contains("TUESDAY") && MyActivity.preferences.getBoolean("TUESDAY", false)){
            tuesday.setChecked(true);
        }
        if(MyActivity.preferences.contains("WEDNESDAY") && MyActivity.preferences.getBoolean("WEDNESDAY", false)){
            wednesday.setChecked(true);
        }
        if(MyActivity.preferences.contains("THURSDAY") && MyActivity.preferences.getBoolean("THURSDAY", false)){
            thursday.setChecked(true);
        }
        if(MyActivity.preferences.contains("FRIDAY") && MyActivity.preferences.getBoolean("FRIDAY", false)){
            friday.setChecked(true);
        }
        if(MyActivity.preferences.contains("SATURDAY") && MyActivity.preferences.getBoolean("SATURDAY", false)){
            saturday.setChecked(true);
        }
        if(MyActivity.preferences.contains("SUNDAY") && MyActivity.preferences.getBoolean("SUNDAY", false)){
            sunday.setChecked(true);
        }
    }
}
