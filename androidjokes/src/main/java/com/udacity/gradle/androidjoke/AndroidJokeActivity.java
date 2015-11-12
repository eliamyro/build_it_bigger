package com.udacity.gradle.androidjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidJokeActivity extends AppCompatActivity {

    public static String JOKE_KEY = "joke_key";
    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_joke);

        jokeTextView = (TextView)findViewById(R.id.joke_textview);

        if (getIntent().hasExtra(JOKE_KEY)){
            String joke = getIntent().getStringExtra(JOKE_KEY);
            jokeTextView.setText(joke);
        }
    }
}
