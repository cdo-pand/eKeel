package com.apedchenko.ekeel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    // region Declaring variables
    private EditText email, password;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // region   Initializing variables
        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passET);
        // endregion
    }

    public void onEnter(View view) {
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);
        finish(); // lock back to login page
    }
}