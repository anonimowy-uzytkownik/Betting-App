package com.example.praca_dyplomowa;

import com.google.firebase.auth.FirebaseAuth;

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

    public String getEmail() {
        return email;
    }

    public String getCoins() {
        return coins;
    }
}
