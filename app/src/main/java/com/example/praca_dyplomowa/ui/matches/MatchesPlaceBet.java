package com.example.praca_dyplomowa.ui.matches;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.praca_dyplomowa.Bet;
import com.example.praca_dyplomowa.BetFinished;
import com.example.praca_dyplomowa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchesPlaceBet extends Activity {

    EditText editTextBettedCoins;
    Button buttonPlaceBet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.window_place_bet);
        final String email = getIntent().getStringExtra("email");
        final String hashedEmail = getIntent().getStringExtra("hashedEmail");
        final String numberOfCoins = getIntent().getStringExtra("numberOfCoins");
        final String team2Odds = getIntent().getStringExtra("team2Odds");
        final String team1Odds = getIntent().getStringExtra("team1Odds");
        final String teamDrawOdds = getIntent().getStringExtra("teamDrawOdds");
        final String league = getIntent().getStringExtra("league");
        final String matchId = getIntent().getStringExtra("matchId");
        final String team1Name = getIntent().getStringExtra("team1Name");
        final String team2Name = getIntent().getStringExtra("team2Name");
        final String result = getIntent().getStringExtra("result");

        buttonPlaceBet=(Button)findViewById(R.id.buttonPlaceBet);
        editTextBettedCoins= findViewById(R.id.editTextBettedCoins);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height * 0.25));


        //getWindow().set

        buttonPlaceBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String coins=editTextBettedCoins.getText().toString();

                if (TextUtils.isEmpty(coins)) {
                    editTextBettedCoins.setError("Provide number of coins");
                    return;
                }

                Double coinsToBet=Double.parseDouble(coins);
                Double coinsInDatabase=Double.parseDouble(numberOfCoins);

                if (!(coinsToBet<=coinsInDatabase)) {
                    editTextBettedCoins.setError("you don't have have enough coins");
                    return;
                }

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(hashedEmail));
                DatabaseReference mDatabaseBettingHistory = FirebaseDatabase.getInstance().getReference("BettingHistory").child(String.valueOf(hashedEmail));

                mDatabase.child("coins").setValue(String.valueOf(Double.parseDouble(numberOfCoins)-coinsToBet));
                DatabaseReference mDatabaseBets = FirebaseDatabase.getInstance().getReference().child("Matches").child(league).child(matchId).child("bets");

                if(!(team1Odds==null))
                {
                    String coinsToWin = String.valueOf(coinsToBet * Double.parseDouble(team1Odds));
                    mDatabaseBets.push().setValue(new Bet(email,"Team1",coinsToWin));
                }
                if(!(team2Odds==null))
                {
                    String coinsToWin = String.valueOf(coinsToBet * Double.parseDouble(team2Odds));
                    mDatabaseBets.push().setValue(new Bet(email,"Team2",coinsToWin));
                }

                if(!(teamDrawOdds==null))
                {
                    String coinsToWin = String.valueOf(coinsToBet * Double.parseDouble(teamDrawOdds));
                    mDatabaseBets.push().setValue(new Bet(email,"Draw",coinsToWin));
                }
                mDatabaseBettingHistory.push().setValue(new BetFinished(String.valueOf(team1Name),String.valueOf(team2Name),String.valueOf(result),String.valueOf(coinsToBet)));
                Toast.makeText(getApplicationContext(),"bet placed",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}


