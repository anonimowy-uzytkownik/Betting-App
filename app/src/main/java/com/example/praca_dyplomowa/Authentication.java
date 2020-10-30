package com.example.praca_dyplomowa;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Authentication {

    public void SaveUserToDatabase()
    {
        User currentUser = new User();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.child(currentUser.getEmail()).setValue(currentUser);

    }

}
