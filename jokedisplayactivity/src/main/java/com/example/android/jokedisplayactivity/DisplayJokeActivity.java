package com.example.android.jokedisplayactivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);
        Intent intent = getIntent();
        TextView joke;
        if (intent.hasExtra("jokes")) {
            joke = findViewById(R.id.joke_text);
            joke.setText(intent.getStringExtra("jokes"));
        } else {
            joke = findViewById(R.id.joke_text);
            joke.setText(R.string.no_joking);
        }
    }
}
