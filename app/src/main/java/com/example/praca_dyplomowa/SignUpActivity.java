package com.example.praca_dyplomowa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText emailId, password,displayName;
    Button btnSignUp;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.login);
        password = findViewById(R.id.password);
        displayName = findViewById(R.id.displayName);
        progressBar = findViewById(R.id.progressBar_signing_up);
        btnSignUp = findViewById(R.id.sign_up);


      /*  if (mFirebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } */

        // FirebaseAuth.getInstance().signOut();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                final String dspName = displayName.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    emailId.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    password.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(dspName)) {
                    displayName.setError("Display name is required");
                    return;
                }
                if (password.length() < 6) {
                    password.setError("password must be longer than 6 characters");
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);
                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(dspName).build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("displayname","user profile updated");
                                }
                            });

                           // Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                           // Toast.makeText(SignUpActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();



                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

                            String username = dspName;
                            String coins = "100";
                            usersRef.child(String.valueOf(email.hashCode())).setValue(new User(username,email,coins));
                            Log.d("account","user should've been created");
                            //usersRef.child(nazwa.getText().toString()).setValue(new Przepis());


                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}