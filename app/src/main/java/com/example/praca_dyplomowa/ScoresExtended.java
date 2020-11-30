package com.example.praca_dyplomowa;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;


public class ScoresExtended extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_extended);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView imageViewTeam1=findViewById(R.id.imageViewTeam1);
        final ImageView imageViewTeam2=findViewById(R.id.imageViewTeam2);
        final TextView textViewTeam1Name = findViewById(R.id.textViewTeam1Name);
        final TextView textViewTeam2Name = findViewById(R.id.textViewTeam2Name);
        final TextView textViewResult=findViewById(R.id.textViewResult);

        final TextView textViewTeam1Shots = findViewById(R.id.textViewTeam1Shots);
        final TextView textViewTeam2Shots = findViewById(R.id.textViewTeam2Shots);
        final TextView textViewTeam1ShotsOnTarget =findViewById(R.id.textViewTeam1ShotsOnTarget);
        final TextView textViewTeam2ShotsOnTarget=findViewById(R.id.textViewTeam2ShotsOnTarget);
        final TextView textViewTeam1Possession=findViewById(R.id.textViewTeam1Possession);
        final TextView textViewTeam2Possession=findViewById(R.id.textViewTeam2Possession);
        final TextView textViewTeam1Passes=findViewById(R.id.textViewTeam1Passes);
        final TextView textViewTeam2Passes=findViewById(R.id.textViewTeam2Passes);
        final TextView textViewTeam1PassAccuracy=findViewById(R.id.textViewTeam1PassAccuracy);
        final TextView textViewTeam2PassAccuracy=findViewById(R.id.textViewTeam2PassAccuracy);
        final TextView textViewTeam1Fouls=findViewById(R.id.textViewTeam1Fouls);
        final TextView textViewTeam2Fouls=findViewById(R.id.textViewTeam2Fouls);
        final TextView textViewTeam1YellowCards=findViewById(R.id.textViewTeam1YellowCards);
        final TextView textViewTeam2YellowCards=findViewById(R.id.textViewTeam2YellowCards);
        final TextView textViewTeam1RedCards=findViewById(R.id.textViewTeam1RedCards);
        final TextView textViewTeam2RedCards=findViewById(R.id.textViewTeam2RedCards);
        final TextView textViewTeam1Offsides=findViewById(R.id.textViewTeam1Offsides);
        final TextView textViewTeam2Offsides=findViewById(R.id.textViewTeam2Offsides);
        final TextView textViewTeam1Corners=findViewById(R.id.textViewTeam1Corners);
        final TextView textViewTeam2Corners=findViewById(R.id.textViewTeam2Corners);



        final String league = getIntent().getStringExtra("league");
        final String matchId = getIntent().getStringExtra("matchId");

        final Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").child(league).child(matchId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Log.d("test",String.valueOf(snapshot.child("result").getValue()));
                textViewTeam1Name.setText(String.valueOf(snapshot.child("team1Name").getValue()));
                textViewTeam2Name.setText(String.valueOf(snapshot.child("team2Name").getValue()));
                textViewResult.setText(String.valueOf(snapshot.child("result").getValue()));
                textViewTeam1Shots.setText(String.valueOf(snapshot.child("details").child("shotsTeam1").getValue()));
                textViewTeam2Shots.setText(String.valueOf(snapshot.child("details").child("shotsTeam2").getValue()));
                textViewTeam1ShotsOnTarget.setText(String.valueOf(snapshot.child("details").child("shotsOnTargetTeam1").getValue()));
                textViewTeam2ShotsOnTarget.setText(String.valueOf(snapshot.child("details").child("shotsOnTargetTeam2").getValue()));
                textViewTeam1Possession.setText(String.valueOf(snapshot.child("details").child("possessionTeam1").getValue()));
                textViewTeam2Possession.setText(String.valueOf(snapshot.child("details").child("possessionTeam2").getValue()));
                textViewTeam1Passes.setText(String.valueOf(snapshot.child("details").child("passesTeam1").getValue()));
                textViewTeam2Passes.setText(String.valueOf(snapshot.child("details").child("passesTeam2").getValue()));
                textViewTeam1PassAccuracy.setText(String.valueOf(snapshot.child("details").child("passAccuracyTeam1").getValue()));
                textViewTeam2PassAccuracy.setText(String.valueOf(snapshot.child("details").child("passAccuracyTeam2").getValue()));
                textViewTeam1Fouls.setText(String.valueOf(snapshot.child("details").child("foulsTeam1").getValue()));
                textViewTeam2Fouls.setText(String.valueOf(snapshot.child("details").child("foulsTeam2").getValue()));
                textViewTeam1YellowCards.setText(String.valueOf(snapshot.child("details").child("yellowCardsTeam1").getValue()));
                textViewTeam2YellowCards.setText(String.valueOf(snapshot.child("details").child("yellowCardsTeam2").getValue()));
                textViewTeam1RedCards.setText(String.valueOf(snapshot.child("details").child("redCardsTeam1").getValue()));
                textViewTeam2RedCards.setText(String.valueOf(snapshot.child("details").child("redCardsTeam2").getValue()));
                textViewTeam1Offsides.setText(String.valueOf(snapshot.child("details").child("offsidesTeam1").getValue()));
                textViewTeam2Offsides.setText(String.valueOf(snapshot.child("details").child("offsidesTeam2").getValue()));
                textViewTeam1Corners.setText(String.valueOf(snapshot.child("details").child("cornersTeam1").getValue()));
                textViewTeam2Corners.setText(String.valueOf(snapshot.child("details").child("cornersTeam2").getValue()));

                try
                {
                    URL url = new URL(String.valueOf(snapshot.child("image1").getValue()));
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    imageViewTeam1.setImageBitmap(image);

                    URL url2 = new URL(String.valueOf(snapshot.child("image2").getValue()));
                    image = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                    imageViewTeam2.setImageBitmap(image);
                }
                catch(IOException e) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        Toast.makeText(getApplicationContext(), league, Toast.LENGTH_SHORT).show();

    }



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
