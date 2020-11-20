package com.example.praca_dyplomowa.ui.profile;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.praca_dyplomowa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private String selectedImagePath;
    static String imagePath = null;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    static  String obrazekURL=null;


    TextView textViewDisplayName, textViewCoins;
    ImageView imageViewAvatar;
    Button buttonChangePassword,buttonChangeDisplayName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        //http://newsapi.org/v2/top-headlines?country=pl&category=sports&apiKey=da1af89106904a70a0a61e0ab58c600f


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

                    try {
                            if(snapshot.child("avatar").getValue()==null){return;}
                            String linkToAvatar=snapshot.child("avatar").getValue().toString();
                            URL url = new URL(linkToAvatar);
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageViewAvatar.setImageBitmap(image);
                        }

                        catch(IOException e) {Log.e("image error",e.getMessage());}
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
                startActivity(new Intent(ProfileFragment.this.getActivity(),ProfileDisplayNameChange.class));
            }
        });
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileFragment.this.getActivity(),ProfilePasswordChange.class));
            }
        });

        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1234) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                imagePath=selectedImagePath;
                //Log.d("sciezka", selectedImagePath);
                System.out.println("Image Path : " + selectedImagePath);
                imageUri = data.getData();

                storage= FirebaseStorage.getInstance();
                storageReference= storage.getReference();
                uploadPicture(String.valueOf(user.getEmail().hashCode()));
            }
        }

    }


    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void uploadPicture(String nazwaPliku) {

        StorageReference riversRef = storageReference.child("images/" + nazwaPliku);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                obrazekURL = uri.toString();

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(user.getEmail().hashCode()));
                                mDatabase.child("avatar").setValue(uri.toString());

                                Log.d("obrazekURL",obrazekURL);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });


    }

}

