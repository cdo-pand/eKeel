package com.apedchenko.ekeel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onLang(MenuItem item) {

    }

    public void onTrainings(View view) {
        Intent trainings = new Intent(getApplicationContext(), TraingsActivity.class);
        startActivity(trainings);
    }

    public void onDictionary(View view) {
        Intent dictionary = new Intent(getApplicationContext(), DictionaryActivity.class);
        startActivity(dictionary);
    }


}