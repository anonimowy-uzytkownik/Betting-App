package com.example.praca_dyplomowa;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.praca_dyplomowa.ui.matches.MatchesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MatchesActivity extends AppCompatActivity {

    private static final String TAG = "MatchesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.fragment_matches);
        setContentView(R.layout.matches_activity);






        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MatchesFragment.newInstance())
                    .commitNow();
        }
/*
        ListView mListView = (ListView) findViewById(R.id.listViewMatches);

        Match AjaxBarcelona = new Match("Ajax","Barcelona","1.2","2.5","1.3","0:0");
        Match AjaxBarcelona2 = new Match("Ajax2","Barcelona2","1.2","2.5","1.3","0:0");

        ArrayList<Match> matchList = new ArrayList<>();
        matchList.add(AjaxBarcelona);
        matchList.add(AjaxBarcelona2);

        MatchListAdapter adapter = new MatchListAdapter(this,R.layout.adapter_view_layout,matchList);
        mListView.setAdapter(adapter);
*/
       // Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").child("1").limitToFirst(2);
        Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").limitToFirst(1);
        //  Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").startAt("Team 1").endAt("Team 1");
        // Toast toast=Toast.makeText(getApplicationContext(),"Hello Javatpoint", Toast.LENGTH_SHORT);
        Log.i("test","test");

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


    }


}