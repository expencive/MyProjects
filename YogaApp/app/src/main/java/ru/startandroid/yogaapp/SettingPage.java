package ru.startandroid.yogaapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Date;

import ru.startandroid.yogaapp.Database.YogaDB;

public class SettingPage extends AppCompatActivity {

    Button btnSave;
    RadioButton rdiEasy, rdiMedium, rdiHard;
    RadioGroup rdiGroup;
    YogaDB yogaDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        btnSave = (Button) findViewById(R.id.btnSave);
        rdiGroup = (RadioGroup) findViewById(R.id.rdiGroup);
        rdiMedium = (RadioButton) findViewById(R.id.rdiMedium);
        rdiHard = (RadioButton) findViewById(R.id.rdiHard);

        switchAlarm = (ToggleButton) findViewById(R.id.switchAlarm);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        yogaDB = new YogaDB(this);
        tv = (TextView) findViewById(R.id.tv);



        int mode = yogaDB.getSettingMode();
        setRadioButton(mode);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(SettingPage.this, "Настройки сохранены", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void saveAlarm(boolean checked) {
        if (checked)
        {
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            intent = new Intent(SettingPage.this, AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            //устанавливаем время на календаре

            Calendar calendar = Calendar.getInstance();
            Date toDay = Calendar.getInstance().getTime();
            calendar.set(toDay.getYear(), toDay.getMonth(), toDay.getDay(), timePicker.getHour(), timePicker.getMinute());

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("DEBUG", "Будильник установлен на: " + timePicker.getHour() + ":" + timePicker.getMinute());

        }
        else
        {
            // cancel Alarm
            Intent intent = new Intent(SettingPage.this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);



        }
    }


    private void saveWorkoutMode() {
        int selectedID = rdiGroup.getCheckedRadioButtonId();
        selectedID = selectedID - 2131427431;
        if (selectedID == 0)
            yogaDB.saveSettingMode(0);
        else if (selectedID == 1)
            yogaDB.saveSettingMode(1);
        else if (selectedID == 2)
            yogaDB.saveSettingMode(2);
    }

    private void setRadioButton(int mode) {

        if (mode == 0)
            rdiGroup.check(R.id.rdiEasy);
        else if (mode == 1)
            rdiGroup.check(R.id.rdiMedium);
        else if (mode == 2)
            rdiGroup.check(R.id.rdiHard);

    }

    public void  onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rdiEasy:
                if (checked)
                    Toast.makeText(SettingPage.this, "Easy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rdiMedium:
                if (checked)
                    Toast.makeText(SettingPage.this, "Medium", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rdiHard:
                if (checked)
                    Toast.makeText(SettingPage.this, "Hard", Toast.LENGTH_SHORT).show();
                break;

        }

    }
    private void setTextMode() {
        int selectedID = rdiGroup.getCheckedRadioButtonId();
        selectedID = selectedID - 2131427431;
        tv.setText("" + selectedID);
        if (selectedID == 0)
            yogaDB.saveSettingMode(0);
        else if (selectedID == 1)
            yogaDB.saveSettingMode(1);
        else if (selectedID == 2)
            yogaDB.saveSettingMode(2);
    }

    private void getRdi(RadioButton v) {
        switch (v.getId()) {
            case R.id.rdiEasy:
                tv.setText(" " + rdiEasy.getId());
                break;
            case R.id.rdiMedium:
                tv.setText(" " + rdiEasy.getId());
                break;
            case R.id.rdiHard:
                tv.setText(" " + rdiEasy.getId());
                break;

        }
    }



}

