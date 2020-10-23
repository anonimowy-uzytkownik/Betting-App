package com.example.praca_dyplomowa;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.example.praca_dyplomowa.ui.matches.MatchesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_matches,R.id.nav_profile,R.id.nav_notifications,R.id.nav_scores,R.id.nav_settings,R.id.nav_leaderboard)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").child("1").limitToFirst(2);
        Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").limitToFirst(1);
        //  Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").startAt("Team 1").endAt("Team 1");
        // Toast toast=Toast.makeText(getApplicationContext(),"Hello Javatpoint", Toast.LENGTH_SHORT);
        Log.i("test","test");
/*
        ListView mListView = (ListView) findViewById(R.id.listViewMatches);

        Match AjaxBarcelona = new Match("Ajax","Barcelona","1.2","2.5","1.3","0:0");
        Match AjaxBarcelona2 = new Match("Ajax2","Barcelona2","1.2","2.5","1.3","0:0");

        ArrayList<Match> matchList = new ArrayList<>();
        matchList.add(AjaxBarcelona);
        matchList.add(AjaxBarcelona2);

        MatchListAdapter adapter = new MatchListAdapter(this,R.layout.adapter_view_layout,matchList);
        mListView.setAdapter(adapter);  */

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    //textView5.setText(snapshot.getValue().toString());
                    Log.i("DataSnapshot",snapshot.getValue().toString());


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        });

        Log.i("DataSnapshot","wyjebalem sie");

        MatchesFragment fragment = new MatchesFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}