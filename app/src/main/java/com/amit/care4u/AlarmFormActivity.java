package com.amit.care4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmFormActivity extends AppCompatActivity {

    EditText medicine_edit, med_desc_edit;
    TimePicker med_time_picker;
    Button set_reminder_btn;

    DatabaseHandler databaseHandler;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private static AlarmFormActivity inst;

    public static AlarmFormActivity instance() {
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_form);

        medicine_edit = findViewById(R.id.medicine_edit);
        med_desc_edit = findViewById(R.id.med_desc_edit);
        med_time_picker = findViewById(R.id.med_time_picker);
        set_reminder_btn = findViewById(R.id.set_reminder_btn);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        set_reminder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, med_time_picker.getCurrentHour());
                calendar.set(Calendar.MINUTE, med_time_picker.getCurrentMinute());
                Intent intent = new Intent(AlarmFormActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(AlarmFormActivity.this, 0, intent, 0);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                databaseHandler = new DatabaseHandler(getApplicationContext());
                long status = databaseHandler.addMedicine(new MedicineModel(medicine_edit.getText().toString().trim()
                        , "Everyday at "+med_time_picker.getCurrentHour()+":"+med_time_picker.getCurrentMinute()
                        , med_desc_edit.getText().toString().trim()));
                if(status > -1){
                    Toast.makeText(getApplicationContext(), "Alarm set", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}