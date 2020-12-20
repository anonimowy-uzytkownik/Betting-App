package com.example.praca_dyplomowa.ui.profile;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.thekhaeng.pushdownanim.PushDownAnim;

public class ProfileDisplayNameChange extends Activity {

    EditText editTextNewDisplayName;
    Button buttonPasswordChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.window_displayname_change);

        buttonPasswordChange=(Button)findViewById(R.id.buttonPasswordChange);
        editTextNewDisplayName= findViewById(R.id.editTextNewDisplayName);
        PushDownAnim.setPushDownAnimTo( buttonPasswordChange);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.85),(int)(height * 0.22));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        buttonPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String dspName = editTextNewDisplayName.getText().toString().trim();

                if (TextUtils.isEmpty(dspName)) {
                    editTextNewDisplayName.setError("Display name is required");
                    //editTextNewDisplayName.setBackgroundResource(R.drawable.error_editext);
                    return;
                }

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(dspName).build();
                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("ProfileDisplayName", "Displayname changed");
                    }
                });

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(user.getEmail().hashCode()));
                mDatabase.child("username").setValue(dspName);

                Log.d("NotNullButton","true");
                finish();
            }
        });


    }
}


