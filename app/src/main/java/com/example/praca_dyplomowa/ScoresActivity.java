package com.example.praca_dyplomowa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.praca_dyplomowa.ui.scores.ScoresFragment;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ScoresFragment.newInstance())
                    .commitNow();
        }
    }
}