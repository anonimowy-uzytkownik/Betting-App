package com.example.praca_dyplomowa.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.praca_dyplomowa.Authentication;
import com.example.praca_dyplomowa.R;
import com.example.praca_dyplomowa.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    TextView textViewDisplayName, textViewCoins;
    ImageView imageViewAvatar;
    Button buttonChangePassword,buttonChangeDisplayName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        textViewDisplayName=(TextView) rootView.findViewById(R.id.textViewDisplayName);
        textViewCoins=(TextView) rootView.findViewById(R.id.textViewCoins);
        buttonChangeDisplayName = (Button) rootView.findViewById(R.id.buttonDisplayNameChange);
        buttonChangePassword = (Button) rootView.findViewById(R.id.buttonPasswordChange);
        imageViewAvatar = (ImageView) rootView.findViewById(R.id.imageViewAvatar);

        Query reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(Integer.parseInt(snapshot.getKey())==user.getEmail().hashCode())
                    {Log.d("UserHash2",snapshot.getKey());
                    textViewDisplayName.setText(snapshot.child("username").getValue().toString());
                    textViewCoins.setText(snapshot.child("coins").getValue().toString()+" coins left!");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonChangeDisplayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       /*         UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(dspName).build();
                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("displayname","user profile updated");
                    }
                }); */
            }
        });
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user.updateEmail()
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel




    }

}