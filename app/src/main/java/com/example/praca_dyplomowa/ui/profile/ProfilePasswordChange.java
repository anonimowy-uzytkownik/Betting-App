package com.example.praca_dyplomowa.ui.profile;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.example.praca_dyplomowa.R;
import com.example.praca_dyplomowa.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilePasswordChange extends Activity {

    EditText editTextNewPassword,editTextNewPasswordRepeated;
    Button buttonPasswordChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.window_password_change);

        buttonPasswordChange=(Button)findViewById(R.id.buttonPasswordChange);
        editTextNewPassword= findViewById(R.id.editTextNewPassword);
        editTextNewPasswordRepeated= findViewById(R.id.editTextNewPasswordRepeated);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height * 0.3));

        buttonPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String newPasswordTypedRepeated = editTextNewPasswordRepeated.getText().toString().trim();
                final String newPasswordTyped = editTextNewPassword.getText().toString().trim();

                if (TextUtils.isEmpty(newPasswordTyped)) {
                    editTextNewPassword.setError("Enter your new password");
                    return;
                }
                if (newPasswordTyped.length() < 6) {
                    editTextNewPassword.setError("password must be longer than 6 characters");
                    return;
                }

                if (!newPasswordTypedRepeated.equals(newPasswordTyped))
                {
                    editTextNewPasswordRepeated.setError("passwords must be identical");
                    return;
                }

                user.updatePassword(newPasswordTyped);


            }
        });
    }
}


