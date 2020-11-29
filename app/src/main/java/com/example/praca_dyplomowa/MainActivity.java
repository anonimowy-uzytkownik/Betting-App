package com.example.praca_dyplomowa;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praca_dyplomowa.ui.matches.MatchesFragment;
import com.example.praca_dyplomowa.ui.notifications.NotificationService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_matches,R.id.nav_profile,R.id.nav_notifications,R.id.nav_scores,R.id.nav_settings,R.id.nav_leaderboard,R.id.nav_chat,R.id.nav_bets)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View headerView = navigationView.getHeaderView(0);

        final User currentUser = new User();
        final TextView textViewDisplayNameNav = headerView.findViewById(R.id.textViewDisplayNameNav);
        final TextView textViewCoinsNav = headerView.findViewById(R.id.textViewCoinsNav);
        final ImageView imageViewAvatarNav = headerView.findViewById(R.id.imageViewAvatarNav);
        textViewDisplayNameNav.setText(currentUser.username);

        Query reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(Integer.parseInt(snapshot.getKey())==currentUser.getEmail().hashCode()) {
                        Log.d("UserHash2", snapshot.getKey());
                        textViewDisplayNameNav.setText(snapshot.child("username").getValue().toString());
                        textViewCoinsNav.setText(snapshot.child("coins").getValue().toString() + " coins left!");
                        try
                        {
                            if (snapshot.child("avatar").getValue() == null)
                            {
                                return;
                            }
                            String linkToAvatar = snapshot.child("avatar").getValue().toString();
                            URL url = new URL(linkToAvatar);
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageViewAvatarNav.setImageBitmap(image);
                        } catch (IOException e)

                        {
                            Log.e("image error", e.getMessage());
                        }
                    }
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }


        }); //wczytywanie obrazka, pieniedzy do nawigacji

        Query referenceNationsLeague = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague");
        referenceNationsLeague.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String Status = String.valueOf(snapshot.child("status").getValue());
                    final String Result = String.valueOf(snapshot.child("result").getValue());
                    final String team1Name = String.valueOf(snapshot.child("team1Name").getValue());
                    final String team2Name = String.valueOf(snapshot.child("team2Name").getValue());

                    if(Status.equals("done")){

                        Query referenceNationsLeagueBets = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague").child(snapshot.getKey()).child("bets");
                        Log.d("referencja",referenceNationsLeagueBets.toString());
                        referenceNationsLeagueBets.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (final DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    if(snapshot.child("email").getValue().toString().equals(currentUser.getEmail()))
                                    {

                                        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(currentUser.getEmail().hashCode()));
                                        if(Match.calculateResult(Result).equals(snapshot.child("betType").getValue().toString()))
                                        {
                                            snapshot.getRef().removeValue();

                                            final String coinsToWin = snapshot.child("coinsToWin").getValue().toString();
                                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    double currentCoins = Double.parseDouble(dataSnapshot.child("coins").getValue().toString());
                                                    double result = currentCoins + Double.parseDouble(coinsToWin);
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    mDatabase.child("coins").setValue(df.format(result));


                                                    if(dataSnapshot.child("bets").child("wins").getValue()==null)
                                                    {
                                                        int wonBets = 0;
                                                        double wonCoins = Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()));
                                                        mDatabase.child("bets").child("wins").setValue(wonBets+1);
                                                        mDatabase.child("bets").child("wonCoins").setValue(df.format(wonCoins));
                                                    }
                                                    else
                                                    {
                                                        Double currentWonCoins = Double.parseDouble(String.valueOf(dataSnapshot.child("bets").child("wonCoins").getValue()));
                                                        int wonBets = Integer.parseInt(String.valueOf(dataSnapshot.child("bets").child("wins").getValue()));
                                                        double wonCoins = Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()));
                                                        mDatabase.child("bets").child("wins").setValue(wonBets+1);
                                                        mDatabase.child("bets").child("wonCoins").setValue(currentWonCoins+wonCoins);
                                                    }

                                                    Log.d("Before adding","before adding");
                                                    String hashedEmail = String.valueOf(String.valueOf(snapshot.child("email").getValue()).hashCode());
                                                    DatabaseReference mDatabaseBettingHistory = FirebaseDatabase.getInstance().getReference("BettingHistory").child(hashedEmail);
                                                    mDatabaseBettingHistory.push().setValue(new BetFinished(String.valueOf(team1Name),String.valueOf(team2Name),String.valueOf(Result)
                                                            ,String.valueOf(Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()))),true));
                                                    Log.d("After adding","after adding");

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else
                                            {
                                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {

                                                        if(dataSnapshot.child("bets").child("loses").getValue()==null)
                                                        {
                                                            int lostBets = 0;
                                                            mDatabase.child("bets").child("loses").setValue(lostBets+1);
                                                            // Log.d("current loses",String.valueOf(lostBets));
                                                            snapshot.getRef().removeValue();
                                                        }
                                                        else
                                                        {
                                                            int lostBets = Integer.parseInt(String.valueOf(dataSnapshot.child("bets").child("loses").getValue()));
                                                            // Log.d("current loses",String.valueOf(lostBets));

                                                            mDatabase.child("bets").child("loses").setValue(lostBets+1);
                                                            snapshot.getRef().removeValue();
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("DataSnapshot",databaseError.getMessage());
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        }); //sprawdzanie betów użytkownika
        Query referenceChampionsLeague = FirebaseDatabase.getInstance().getReference().child("Matches").child("ChampionsLeague");
        referenceChampionsLeague.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String Status = String.valueOf(snapshot.child("status").getValue());
                    final String Result = String.valueOf(snapshot.child("result").getValue());
                    final String team1Name = String.valueOf(snapshot.child("team1Name").getValue());
                    final String team2Name = String.valueOf(snapshot.child("team2Name").getValue());

                    if(Status.equals("done")){

                        Query referenceChampionsLeagueBets = FirebaseDatabase.getInstance().getReference().child("Matches").child("ChampionsLeague").child(snapshot.getKey()).child("bets");
                        Log.d("referencja",referenceChampionsLeagueBets.toString());
                        referenceChampionsLeagueBets.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (final DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    if(snapshot.child("email").getValue().toString().equals(currentUser.getEmail()))
                                    {
                                        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(currentUser.getEmail().hashCode()));
                                        if(Match.calculateResult(Result).equals(snapshot.child("betType").getValue().toString()))
                                        {
                                            snapshot.getRef().removeValue();

                                            final String coinsToWin = snapshot.child("coinsToWin").getValue().toString();
                                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    double currentCoins = Double.parseDouble(dataSnapshot.child("coins").getValue().toString());
                                                    double result = currentCoins + Double.parseDouble(coinsToWin);
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    mDatabase.child("coins").setValue(df.format(result));

                                                    if(dataSnapshot.child("bets").child("wins").getValue()==null)
                                                    {
                                                        int wonBets = 0;
                                                        double wonCoins = Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()));
                                                        mDatabase.child("bets").child("wins").setValue(wonBets+1);
                                                        mDatabase.child("bets").child("wonCoins").setValue(df.format(wonCoins));
                                                    }
                                                    else
                                                    {
                                                        Double currentWonCoins = Double.parseDouble(String.valueOf(dataSnapshot.child("bets").child("wonCoins").getValue()));
                                                        int wonBets = Integer.parseInt(String.valueOf(dataSnapshot.child("bets").child("wins").getValue()));
                                                        double wonCoins = Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()));
                                                        mDatabase.child("bets").child("wins").setValue(wonBets+1);
                                                        mDatabase.child("bets").child("wonCoins").setValue(currentWonCoins+wonCoins);

                                                    }
                                                    String hashedEmail = String.valueOf(String.valueOf(snapshot.child("email").getValue()).hashCode());
                                                    DatabaseReference mDatabaseBettingHistory = FirebaseDatabase.getInstance().getReference("BettingHistory").child(hashedEmail);
                                                    mDatabaseBettingHistory.push().setValue(new BetFinished(String.valueOf(team1Name),String.valueOf(team2Name),String.valueOf(Result)
                                                            ,String.valueOf(Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()))),true));

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }else
                                            {
                                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {

                                                        if(dataSnapshot.child("bets").child("loses").getValue()==null)
                                                        {
                                                            int lostBets = 0;
                                                            mDatabase.child("bets").child("loses").setValue(lostBets+1);
                                                            // Log.d("current loses",String.valueOf(lostBets));
                                                            snapshot.getRef().removeValue();
                                                        }
                                                        else
                                                        {
                                                            int lostBets = Integer.parseInt(String.valueOf(dataSnapshot.child("bets").child("loses").getValue()));
                                                            // Log.d("current loses",String.valueOf(lostBets));

                                                            mDatabase.child("bets").child("loses").setValue(lostBets+1);
                                                            snapshot.getRef().removeValue();
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                    }
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("DataSnapshot",databaseError.getMessage());
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        }); //sprawdzanie betów użytkownika
        Query referenceEuropaLeague = FirebaseDatabase.getInstance().getReference().child("Matches").child("EuropaLeague");
        referenceEuropaLeague.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String Status = String.valueOf(snapshot.child("status").getValue());
                    final String Result = String.valueOf(snapshot.child("result").getValue());
                    final String team1Name = String.valueOf(snapshot.child("team1Name").getValue());
                    final String team2Name = String.valueOf(snapshot.child("team2Name").getValue());

                    if(Status.equals("done")){

                        Query referenceEuropaLeagueBets = FirebaseDatabase.getInstance().getReference().child("Matches").child("EuropaLeague").child(snapshot.getKey()).child("bets");
                        Log.d("referencja",referenceEuropaLeagueBets.toString());
                        referenceEuropaLeagueBets.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                for (final DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    if(snapshot.child("email").getValue().toString().equals(currentUser.getEmail()))
                                    {
                                        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(currentUser.getEmail().hashCode()));

                                        if(Match.calculateResult(Result).equals(snapshot.child("betType").getValue().toString()))
                                        {
                                            snapshot.getRef().removeValue();
                                           final String coinsToWin = snapshot.child("coinsToWin").getValue().toString();
                                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {


                                                    double currentCoins = Double.parseDouble(dataSnapshot.child("coins").getValue().toString());
                                                    double result = currentCoins + Double.parseDouble(coinsToWin);
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    mDatabase.child("coins").setValue(df.format(result));

                                                    if(dataSnapshot.child("bets").child("wins").getValue()==null)
                                                    {
                                                        int wonBets = 0;
                                                        double wonCoins = Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()));
                                                        mDatabase.child("bets").child("wins").setValue(wonBets+1);
                                                        mDatabase.child("bets").child("wonCoins").setValue(df.format(wonCoins));
                                                    }
                                                    else
                                                    {
                                                        Double currentWonCoins = Double.parseDouble(String.valueOf(dataSnapshot.child("bets").child("wonCoins").getValue()));
                                                        int wonBets = Integer.parseInt(String.valueOf(dataSnapshot.child("bets").child("wins").getValue()));
                                                        double wonCoins = Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()));
                                                        mDatabase.child("bets").child("wins").setValue(wonBets+1);
                                                        mDatabase.child("bets").child("wonCoins").setValue(currentWonCoins+wonCoins);
                                                    }

                                                    String hashedEmail = String.valueOf(String.valueOf(snapshot.child("email").getValue()).hashCode());
                                                    DatabaseReference mDatabaseBettingHistory = FirebaseDatabase.getInstance().getReference("BettingHistory").child(hashedEmail);
                                                    mDatabaseBettingHistory.push().setValue(new BetFinished(String.valueOf(team1Name),String.valueOf(team2Name),String.valueOf(Result)
                                                            ,String.valueOf(Double.parseDouble(String.valueOf(snapshot.child("coinsToWin").getValue()))),true));

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else
                                            {
                                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {

                                                        if(dataSnapshot.child("bets").child("loses").getValue()==null)
                                                        {
                                                            int lostBets = 0;
                                                            mDatabase.child("bets").child("loses").setValue(lostBets+1);
                                                           // Log.d("current loses",String.valueOf(lostBets));
                                                           // snapshot.getRef().removeValue();
                                                        }
                                                        else
                                                        {

                                                            int lostBets = Integer.parseInt(String.valueOf(dataSnapshot.child("bets").child("loses").getValue()));
                                                            Log.d("current loses",String.valueOf(lostBets));

                                                            mDatabase.child("bets").child("loses").setValue(lostBets+1);
                                                            snapshot.getRef().removeValue();
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                    }
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("DataSnapshot",databaseError.getMessage());
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        }); //sprawdzanie betów użytkownika

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referenceNotificationsNationsLeague =  firebaseDatabase.getReference().child("Matches").child("NationsLeague");
        DatabaseReference referenceNotificationsChampionsLeague =  firebaseDatabase.getReference().child("Matches").child("ChampionsLeague");
        DatabaseReference referenceNotificationsEuropaLeague =  firebaseDatabase.getReference().child("Matches").child("EuropaLeague");

        referenceNotificationsNationsLeague.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(String.valueOf(snapshot.child("status").getValue()).equals("done"))
                {

                    Intent activityMatches = new Intent(getApplicationContext(),MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(activityMatches);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                            .setContentTitle("test")
                            .setContentText(snapshot.child("team1Name").getValue().toString()+" vs "+snapshot.child("team2Name").getValue().toString()+" has finished!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(resultPendingIntent)
                            .setAutoCancel(true);;


                    Query referenceNationsLeagueBets = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague").child(snapshot.getKey()).child("bets"); //error

                    referenceNationsLeagueBets.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                           // Log.d("snapshotValue",dataSnapshot.getValue().toString());
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                Log.d("snapshotValue",String.valueOf(snapshot.child("email").getValue()));
                                Log.d("emailValue",currentUser.getEmail());
                                if (String.valueOf(snapshot.child("email").getValue()).equals(currentUser.getEmail()))
                                {
                                    NotificationManager notificationManager = (NotificationManager)getSystemService
                                            (
                                                    Context.NOTIFICATION_SERVICE
                                            ) ;
                                    notificationManager.notify(0,builder.build());
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referenceNotificationsChampionsLeague.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(String.valueOf(snapshot.child("status").getValue()).equals("done"))
                {

                    Intent activityMatches = new Intent(getApplicationContext(),MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(activityMatches);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                            .setContentTitle("test")
                            .setContentText(snapshot.child("team1Name").getValue().toString()+" vs "+snapshot.child("team2Name").getValue().toString()+" has finished!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(resultPendingIntent)
                            .setAutoCancel(true);


                    Query referenceChampionsLeagueBets = FirebaseDatabase.getInstance().getReference().child("Matches").child("ChampionsLeague").child(snapshot.getKey()).child("bets");

                    referenceChampionsLeagueBets.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                           // Log.d("snapshotValue",String.valueOf(dataSnapshot.getValue()));
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                Log.d("snapshotValue",String.valueOf(snapshot.child("email").getValue()));
                                Log.d("emailValue",currentUser.getEmail());
                                if (String.valueOf(snapshot.child("email").getValue()).equals(currentUser.getEmail()))
                                {
                                    NotificationManager notificationManager = (NotificationManager)getSystemService
                                            (
                                                    Context.NOTIFICATION_SERVICE
                                            ) ;
                                    notificationManager.notify(0,builder.build());
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referenceNotificationsEuropaLeague.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(String.valueOf(snapshot.child("status").getValue()).equals("done"))
                {

                    Intent activityMatches = new Intent(getApplicationContext(),MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                    stackBuilder.addParentStack(MainActivity.class);
                    stackBuilder.addNextIntent(activityMatches);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                            .setContentTitle("test")
                            .setContentText(snapshot.child("team1Name").getValue().toString()+" vs "+snapshot.child("team2Name").getValue().toString()+" has finished!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(resultPendingIntent)
                            .setAutoCancel(true);



                    Query referenceEuropaLeagueBets = FirebaseDatabase.getInstance().getReference().child("Matches").child("EuropaLeague").child(snapshot.getKey()).child("bets");

                    referenceEuropaLeagueBets.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                          //  Log.d("snapshotValue",dataSnapshot.getValue().toString());
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                Log.d("snapshotValue",String.valueOf(snapshot.child("email").getValue()));
                                Log.d("emailValue",currentUser.getEmail());
                                if (String.valueOf(snapshot.child("email").getValue()).equals(currentUser.getEmail()))
                                {
                                    NotificationManager notificationManager = (NotificationManager)getSystemService
                                            (
                                                    Context.NOTIFICATION_SERVICE
                                            ) ;

                                    notificationManager.notify(0,builder.build());
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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