package com.example.praca_dyplomowa;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;


public class ScoresExtended extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final String league = getIntent().getStringExtra("league");
        final String matchId = getIntent().getStringExtra("matchId");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_extended);

        Toast.makeText(getApplicationContext(), league, Toast.LENGTH_SHORT).show();

    }

    /*
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
