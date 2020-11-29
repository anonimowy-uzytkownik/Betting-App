package com.example.praca_dyplomowa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.praca_dyplomowa.ui.bets.BetsFragment;

public class BetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bets_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BetsFragment.newInstance())
                    .commitNow();
        }
    }
}