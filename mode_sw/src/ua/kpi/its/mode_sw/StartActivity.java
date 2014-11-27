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
    final String TIME_START = "time_start";
    final String TIME_END = "time_end";

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
                Intent intent = new Intent(StartActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences("goodnight", MODE_PRIVATE);
        String stringStart = preferences.getString(TIME_START, "Time's not set");
        String stringEnd = preferences.getString(TIME_END, "Time's not set");
        timeStart.setText(stringStart);
        timeEnd.setText(stringEnd);
    }
}
