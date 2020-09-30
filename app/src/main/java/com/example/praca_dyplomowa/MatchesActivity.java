package com.example.praca_dyplomowa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.praca_dyplomowa.ui.matches.MatchesFragment;

public class MatchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MatchesFragment.newInstance())
                    .commitNow();
        }

    }


}