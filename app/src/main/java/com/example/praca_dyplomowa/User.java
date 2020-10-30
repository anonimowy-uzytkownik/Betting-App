package com.example.praca_dyplomowa;

import com.google.firebase.auth.FirebaseAuth;

public class User {
    String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    String coins = "100";

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCoins() {
        return coins;
    }
}
