package com.example.praca_dyplomowa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.praca_dyplomowa.ui.matches.MatchesFragment;
import com.example.praca_dyplomowa.ui.matches.MatchesPlaceBet;
import com.example.praca_dyplomowa.ui.profile.ProfileDisplayNameChange;
import com.example.praca_dyplomowa.ui.profile.ProfileFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends ArrayAdapter<Match> {

    private final Context mContext;
    int mResource;

    public MatchListAdapter(Context context, int resource, ArrayList<Match> objects)
    {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable  View convertView, @NonNull final ViewGroup parent)
    {
        final String team1Name = getItem(position).getTeam1Name();
        String team2Name= getItem(position).getTeam2Name();
        String win1odds= getItem(position).getWin1odds();
        String win2odds= getItem(position).getWin2odds();
        String win3odds= getItem(position).getWin3odds();
        String result= getItem(position).getResult();
        String image1= getItem(position).getImage1();
        String image2= getItem(position).getImage2();
        final String league = getItem(position).getLeague();
        final String matchId = getItem(position).getMatchId();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvTeam1Name = convertView.findViewById(R.id.textViewTeam1Name);
        TextView tvTeam2Name = convertView.findViewById(R.id.textViewTeam2Name);
        TextView tvResult = convertView.findViewById(R.id.textViewResult);

        final Button btnTeam1Odds = convertView.findViewById(R.id.buttonTeam1);
        final Button btnTeam2Odds = convertView.findViewById(R.id.buttonTeam2);
        final Button btnDrawOdds = convertView.findViewById(R.id.buttonTeamDraw);

        ImageView imgTeam1 = convertView.findViewById(R.id.imageViewTeam1);
        ImageView imgTeam2 = convertView.findViewById(R.id.imageViewTeam2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tvTeam1Name.setText(team1Name);
        tvTeam2Name.setText(team2Name);
        tvResult.setText(result);
        btnTeam1Odds.setText(win1odds);
        btnTeam2Odds.setText(win3odds);
        btnDrawOdds.setText(win2odds);

        try
        {
            URL url = new URL(image1);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imgTeam1.setImageBitmap(image);

            URL url2 = new URL(image2);
            image = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            imgTeam2.setImageBitmap(image);
        }
        catch(IOException e) {}


        final View finalConvertView = convertView;
        btnTeam1Odds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                final User currentUser = new User();
                Query reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if(Double.parseDouble(snapshot.getKey())==currentUser.email.hashCode())
                            {
                                String numberOfCoins = snapshot.child("coins").getValue().toString();

                                Intent windowPlaceBet = new Intent(mContext, MatchesPlaceBet.class);
                                windowPlaceBet.putExtra("email",currentUser.getEmail());
                                windowPlaceBet.putExtra("hashedEmail",String.valueOf(currentUser.getEmail().hashCode()));
                                windowPlaceBet.putExtra("numberOfCoins",numberOfCoins);
                                windowPlaceBet.putExtra("team1Odds",btnTeam1Odds.getText().toString());
                                windowPlaceBet.putExtra("league",league);
                                windowPlaceBet.putExtra("matchId",matchId);
                                mContext.startActivity(windowPlaceBet);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_LONG);
                    }
                });
            }
        });
        btnTeam2Odds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final User currentUser = new User();
                Query reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if(Double.parseDouble(snapshot.getKey())==currentUser.email.hashCode())
                            {

                                String numberOfCoins = snapshot.child("coins").getValue().toString();

                                  /*
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(currentUser.getEmail().hashCode()));
                                    mDatabase.child("coins").setValue(String.valueOf(Double.parseDouble(numberOfCoins)-10));

                                    DatabaseReference mDatabaseBets = FirebaseDatabase.getInstance().getReference().child("Matches").child(league).child(matchId).child("bets");
                                    String coinsToWin = String.valueOf(10 * Double.parseDouble(btnTeam2Odds.getText().toString()));
                                    mDatabaseBets.push().setValue(new Bet(currentUser.getEmail(),"Team2",coinsToWin));
                                  */
                                    Intent windowPlaceBet = new Intent(mContext, MatchesPlaceBet.class);
                                    windowPlaceBet.putExtra("email",currentUser.getEmail());
                                    windowPlaceBet.putExtra("hashedEmail",String.valueOf(currentUser.getEmail().hashCode()));
                                    windowPlaceBet.putExtra("numberOfCoins",numberOfCoins);
                                    windowPlaceBet.putExtra("team2Odds",btnTeam2Odds.getText().toString());
                                    windowPlaceBet.putExtra("league",league);
                                    windowPlaceBet.putExtra("matchId",matchId);
                                   // Toast.makeText(mContext,"bet placed",Toast.LENGTH_SHORT).show();
                                    mContext.startActivity(windowPlaceBet);


                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_LONG);
                    }
                });
            }
        });

        btnDrawOdds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final User currentUser = new User();
                Query reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if(Double.parseDouble(snapshot.getKey())==currentUser.email.hashCode())
                            {
                                String numberOfCoins = snapshot.child("coins").getValue().toString();
                                Intent windowPlaceBet = new Intent(mContext, MatchesPlaceBet.class);
                                windowPlaceBet.putExtra("email",currentUser.getEmail());
                                windowPlaceBet.putExtra("hashedEmail",String.valueOf(currentUser.getEmail().hashCode()));
                                windowPlaceBet.putExtra("numberOfCoins",numberOfCoins);
                                windowPlaceBet.putExtra("teamDrawOdds",btnDrawOdds.getText().toString());
                                windowPlaceBet.putExtra("league",league);
                                windowPlaceBet.putExtra("matchId",matchId);

                                mContext.startActivity(windowPlaceBet);


                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_LONG);
                    }
                });
            }
        });

        return convertView;
    }
}


