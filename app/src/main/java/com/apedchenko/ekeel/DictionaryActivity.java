package com.apedchenko.ekeel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DictionaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);
    }

    public void onFindTranslatePage(View view) {
        Intent findTranslate = new Intent(getApplicationContext(), FindTranslateActivity.class);
        startActivity(findTranslate);
    }

    public void onRandomWordPage(View view) {
        Intent randomWord = new Intent(getApplicationContext(), RandomWordActivity.class);
        startActivity(randomWord);
    }
}