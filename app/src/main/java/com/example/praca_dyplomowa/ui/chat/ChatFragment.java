package com.example.praca_dyplomowa.ui.chat;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.praca_dyplomowa.ChatAdapter;
import com.example.praca_dyplomowa.Message;
import com.example.praca_dyplomowa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ListView mListView = (ListView)rootView.findViewById(R.id.listViewChat);
        ImageButton btnSendMessage = (ImageButton)rootView.findViewById(R.id.buttontSendMessage);
        final EditText newMessage =(EditText)rootView.findViewById(R.id.editTextNewMessage);

        final ArrayList<Message> messagesList = new ArrayList<>();
        final ChatAdapter adapter = new ChatAdapter(getContext(),R.layout.adapter_chat_view_layout,messagesList);
        mListView.setAdapter(adapter);


        final Query reference = FirebaseDatabase.getInstance().getReference().child("Messages").limitToLast(20);
        messagesList.clear();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String displayName = String.valueOf(snapshot.child("displayName").getValue());
                    String messageTime = String.valueOf(snapshot.child("messageTime").getValue());
                    String messageText = String.valueOf(snapshot.child("message").getValue());

                    Message message = new Message(displayName,messageTime,messageText);

                    messagesList.add(message);
                    adapter.notifyDataSetChanged();
                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        });

        Query referenceSingle = FirebaseDatabase.getInstance().getReference().child("Messages").limitToLast(1);
        referenceSingle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    String displayName = String.valueOf(snapshot.child("displayName").getValue());
                    String messageTime = String.valueOf(snapshot.child("messageTime").getValue());
                    String messageText = String.valueOf(snapshot.child("message").getValue());

                    Message message = new Message(displayName,messageTime,messageText);

                    messagesList.add(message);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String message = String.valueOf(newMessage.getText());
                if(message.isEmpty()){return;}

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String nickname =  user.getDisplayName();
                SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
                final String currentTime = dateFormat.format(Calendar.getInstance().getTime());

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Messages");
                usersRef.push().setValue(new Message(nickname,currentTime,message));

                newMessage.setText("");
                v.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.blink));
            }
        });

        return rootView;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

}