package com.amit.care4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 101;
    TextView registration_btn;
    EditText email_edit, passwd_edit;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        email_edit = findViewById(R.id.email_edit);
        passwd_edit = findViewById(R.id.passwd_edit);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_edit.getText().toString().trim().equals(""))
                    email_edit.setError("Required field");
                else if(passwd_edit.getText().toString().trim().equals(""))
                    passwd_edit.setError("Required field");
                else if(email_edit.getText().toString().equals(sharedPreferences.getString("email", "none"))) {
                    if(passwd_edit.getText().toString().trim().equals(sharedPreferences.getString("password", "none"))) {
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();
                    }
                }
                else {
                    email_edit.setError("Email might be wrong");
                    passwd_edit.setError("Password might be wrong");
                }
            }
        });

        registration_btn = findViewById(R.id.registration_btn);
        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
    }
}