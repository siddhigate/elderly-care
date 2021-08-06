package com.amit.care4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText name_edit, email_edit, passwd_edit, c_passwd_edit;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name_edit = findViewById(R.id.name_edit);
        email_edit = findViewById(R.id.email_edit);
        passwd_edit = findViewById(R.id.passwd_edit);
        c_passwd_edit = findViewById(R.id.c_passwd_edit);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_edit.getText().toString().trim().equals(""))
                    name_edit.setError("Required field");
                else if(email_edit.getText().toString().trim().equals(""))
                    email_edit.setError("Required field");
                else if(passwd_edit.getText().toString().trim().equals(""))
                    passwd_edit.setError("Required field");
                else if(!passwd_edit.getText().toString().trim().equals(c_passwd_edit.getText().toString().trim()))
                    c_passwd_edit.setError("Password does not match");
                else {
                    SharedPreferences.Editor user = sharedPreferences.edit();
                    user.putString("name", name_edit.getText().toString().trim());
                    user.putString("email", email_edit.getText().toString().trim());
                    user.putString("password", passwd_edit.getText().toString().trim());
                    user.apply();
                    user.commit();
                    startActivity(new Intent(getApplicationContext(), MobileSaveActivity.class));
                    finish();
                }
            }
        });
    }
}