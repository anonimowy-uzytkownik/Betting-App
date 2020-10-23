package com.example.praca_dyplomowa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.praca_dyplomowa.ui.notifications.NotificationsFragment;

public class NotificationsActivity extends AppCompatActivity {

    private static final String TAG = "MatchesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NotificationsFragment.newInstance())
                    .commitNow();
        }

        Log.d(TAG,"onCreate: Started.");
    }
}