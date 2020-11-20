package com.example.praca_dyplomowa;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //textViewDisplayNameNav.setText(currentUser.username);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_matches,R.id.nav_profile,R.id.nav_notifications,R.id.nav_scores,R.id.nav_settings,R.id.nav_leaderboard,R.id.nav_chat)
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
                        try {
                            if (snapshot.child("avatar").getValue() == null) {
                                return;
                            }
                            String linkToAvatar = snapshot.child("avatar").getValue().toString();
                            URL url = new URL(linkToAvatar);
                           Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageViewAvatarNav.setImageBitmap(image);
                        } catch (IOException e) {
                            Log.e("image error", e.getMessage());
                        }
                    }
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // Log.i("DataSnapshot","wyjebalem sie");


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