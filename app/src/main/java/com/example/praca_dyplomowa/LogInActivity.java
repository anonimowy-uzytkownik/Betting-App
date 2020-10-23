package com.example.praca_dyplomowa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    EditText emailId,password;
    Button btnSignUp,btnSignIn,btnSignInWithGoogle;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailId= findViewById(R.id.email);
        password= findViewById(R.id.password);
        btnSignUp =  findViewById(R.id.sign_up);
        btnSignIn =  findViewById(R.id.sign_in);
        btnSignInWithGoogle =  findViewById(R.id.sign_in_gplus);
        progressBar = findViewById(R.id.progressBar);
        mFirebaseAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);





        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailId.getText().toString().trim();
                String pwd = password.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    emailId.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    password.setError("Password is required");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user
                try{
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                Toast.makeText(LogInActivity.this, "Logged in Successfully! ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {
                                Toast.makeText(LogInActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }); }catch (Exception e){emailId.setText(e.getMessage().toString());}
            }
        });

        btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MatchesActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.INVISIBLE);
    }
}