package com.amit.care4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MobileSaveActivity extends AppCompatActivity {

    EditText mobile1_edit, mobile2_edit, mobile3_edit, mobile4_edit;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_save);

        mobile1_edit = findViewById(R.id.mobile1_edit);
        mobile2_edit = findViewById(R.id.mobile2_edit);
        mobile3_edit = findViewById(R.id.mobile3_edit);
        mobile4_edit = findViewById(R.id.mobile4_edit);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        submit_btn = findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile1_edit.getText().toString().trim().equals(""))
                    mobile1_edit.setError("Required field");
                else if(mobile2_edit.getText().toString().trim().equals(""))
                    mobile2_edit.setError("Required field");
                else {
                    SharedPreferences.Editor user = sharedPreferences.edit();
                    user.putString("mobile1", mobile1_edit.getText().toString());
                    user.putString("mobile2", mobile2_edit.getText().toString());
                    user.apply();
                    user.commit();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                }
            }
        });
    }
}