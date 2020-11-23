package com.example.praca_dyplomowa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;

public class User {
    String username;
    //String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    String email;
    //String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    String coins;
    //String coins = "100";

    public String getUsername() {
        return username;
    }

    public User(String username, String email, String coins) {
        this.username = username;
        this.email = email;
        this.coins = coins;
    }

    public User() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.username = user.getDisplayName();
        this.email = user.getEmail();
       // this.coins = setCoins();





    }

    public String getEmail() {
        return email;
    }

    public String getCoins() {
        return coins;
    }

    public String setCoins(){

        final String[] coins = {"20"};
        Query reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(Integer.parseInt(snapshot.getKey())==email.hashCode())
                    {
                        Log.d("UserHash2",snapshot.getKey());

                        coins[0] =snapshot.child("coins").getValue().toString();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return coins[0];

    }
}
