package com.example.praca_dyplomowa.ui.leaderboard;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.praca_dyplomowa.ChatAdapter;
import com.example.praca_dyplomowa.LeaderboardTopThreeAdapter;
import com.example.praca_dyplomowa.LeaderboardUser;
import com.example.praca_dyplomowa.Message;
import com.example.praca_dyplomowa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class LeaderboardFragment extends Fragment {

    private LeaderboardViewModel mViewModel;
 //   TextView textViewFirstPlayer;
 //   TextView textViewSecondPlayer;
//    TextView textViewThirdPlayer;
    TextView textViewDisplayNameTop3,textViewCoinsWonTop3,textViewWinRatioTop3;
    TextView textViewDisplayNameTop2,textViewCoinsWonTop2,textViewWinRatioTop2;
    TextView textViewDisplayNameTop1,textViewCoinsWonTop1,textViewWinRatioTop1;
    ListView listViewLeaderboard;
//    ListView listViewLeaderboardTopThree;
    ImageView imageViewAvatarTop1,imageViewAvatarTop2,imageViewAvatarTop3;
 //   RecyclerView recycleViewLeaderboardTopThree;

    public static LeaderboardFragment newInstance() {
        return new LeaderboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

      //  textViewFirstPlayer=view.findViewById(R.id.textViewFirstPlayer);
      //  textViewSecondPlayer=view.findViewById(R.id.textViewSecondPlayer);
      //  textViewThirdPlayer=view.findViewById(R.id.textViewThirdPlayer);
        listViewLeaderboard=view.findViewById(R.id.listViewLeaderboard);
     //   recycleViewLeaderboardTopThree=view.findViewById(R.id.recycleViewLeaderboardTopThree);
  //      listViewLeaderboardTopThree=view.findViewById(R.id.listViewLeaderboardTopThree);
        imageViewAvatarTop1=view.findViewById(R.id.imageViewAvatarTop1);
        imageViewAvatarTop2=view.findViewById(R.id.imageViewAvatarTop2);
        imageViewAvatarTop3=view.findViewById(R.id.imageViewAvatarTop3);

        textViewDisplayNameTop3 =view.findViewById(R.id.textViewDisplayNameTop3);
        textViewCoinsWonTop3=view.findViewById(R.id.textViewCoinsWonTop3);
        textViewWinRatioTop3=view.findViewById(R.id.textViewWinRatioTop3);

        textViewDisplayNameTop2 =view.findViewById(R.id.textViewDisplayNameTop2);
        textViewCoinsWonTop2=view.findViewById(R.id.textViewCoinsWonTop2);
        textViewWinRatioTop2=view.findViewById(R.id.textViewWinRatioTop2);

        textViewDisplayNameTop1 =view.findViewById(R.id.textViewDisplayNameTop1);
        textViewCoinsWonTop1=view.findViewById(R.id.textViewCoinsWonTop1);
        textViewWinRatioTop1=view.findViewById(R.id.textViewWinRatioTop1);


        final ArrayList<LeaderboardUser> LeaderboardUserTopThreeList = new ArrayList<>();
        final ArrayList<LeaderboardUser> LeaderboardUserList = new ArrayList<>();
 //       final LeaderboardTopThreeAdapter adapterLeaderboardTopThree = new LeaderboardTopThreeAdapter(getContext(),R.layout.adapter_leaderboard_top_three,LeaderboardUserTopThreeList);
        final LeaderboardTopThreeAdapter adapterLeaderboard = new LeaderboardTopThreeAdapter(getContext(),R.layout.adapter_leaderboard,LeaderboardUserList);
        //final ChatAdapter adapter = new ChatAdapter(getContext(),R.layout.adapter_chat_view_layout,messagesList);
        listViewLeaderboard.setAdapter(adapterLeaderboard);
//        listViewLeaderboardTopThree.setAdapter(adapterLeaderboardTopThree);


        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Leaderboard");
        Query queryBestThreePlayers = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("wonCoins");
        queryBestThreePlayers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot snapshot : dataSnapshot.getChildren())
                {


                    mDatabase.child(snapshot.getKey()).child("wonCoins").setValue(snapshot.child("bets").child("wonCoins").getValue());
                    double id = Double.parseDouble(String.valueOf(snapshot.child("bets").child("wonCoins").getValue())) * -1;
                    mDatabase.child(snapshot.getKey()).child("wonCoinsReversed").setValue(id);
                    mDatabase.child(snapshot.getKey()).child("wins").setValue(snapshot.child("bets").child("wins").getValue());
                    mDatabase.child(snapshot.getKey()).child("loses").setValue(snapshot.child("bets").child("loses").getValue());
                    mDatabase.child(snapshot.getKey()).child("displayName").setValue(snapshot.child("username").getValue());
                    mDatabase.child(snapshot.getKey()).child("avatar").setValue(snapshot.child("avatar").getValue());

                     Log.d("wonCoins",String.valueOf(snapshot.child("bets").child("wonCoins").getValue()));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        final Query leaderboardReference = FirebaseDatabase.getInstance().getReference("Leaderboard").orderByChild("wonCoinsReversed");
        final ArrayList<Double> bestScores = new ArrayList<>();

        leaderboardReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    bestScores.add(Double.parseDouble(String.valueOf(snapshot.child("wonCoins").getValue())));
                    String displayName=String.valueOf(snapshot.child("displayName").getValue());
                    String wins=String.valueOf(snapshot.child("wins").getValue());
                    String loses=String.valueOf(snapshot.child("loses").getValue());
                    String coinsWon=String.valueOf(snapshot.child("wonCoins").getValue());
                    String avatar=String.valueOf(snapshot.child("avatar").getValue());
                  //  String avatar = "";


                    String leaderboardRanking="0";
                    leaderboardRanking = String.valueOf(LeaderboardUserList.size()+4)+".";

                    LeaderboardUser leaderboardUser = new LeaderboardUser(displayName,wins,loses,coinsWon,leaderboardRanking);

                    if(LeaderboardUserTopThreeList.size()==0)
                    {
                        try
                        {
                            URL url = new URL(avatar);
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageViewAvatarTop1.setImageBitmap(image);

                        }
                        catch(IOException e) {}

                        textViewDisplayNameTop1.setText(displayName);
                        textViewCoinsWonTop1.setText(coinsWon + " coins won");
                        textViewWinRatioTop1.setText(wins + ":" + loses +" w/l");
                    }
                    else if(LeaderboardUserTopThreeList.size()==1)
                    {
                        try
                        {
                            URL url = new URL(avatar);
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageViewAvatarTop2.setImageBitmap(image);

                        }
                        catch(IOException e) {}

                        textViewDisplayNameTop2.setText(displayName);
                        textViewCoinsWonTop2.setText(coinsWon + " coins won");
                        textViewWinRatioTop2.setText(wins + ":" + loses +" w/l");
                    }
                    else if(LeaderboardUserTopThreeList.size()==2)
                    {
                        try
                        {
                            URL url = new URL(avatar);
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageViewAvatarTop3.setImageBitmap(image);

                        }
                        catch(IOException e) {}

                        textViewDisplayNameTop3.setText(displayName);
                        textViewCoinsWonTop3.setText(coinsWon + " coins won");
                        textViewWinRatioTop3.setText(wins + ":" + loses +" w/l");
                    }



                        if(LeaderboardUserTopThreeList.size()<3)
                        {
                            LeaderboardUserTopThreeList.add(leaderboardUser);
                        }
                        else
                            {
                                LeaderboardUserList.add(leaderboardUser);
                                adapterLeaderboard.notifyDataSetChanged();
                            }


 /*
                    LeaderboardUserList.add(leaderboardUser);
                    adapterLeaderboardTopThree.notifyDataSetChanged();
                 //
                     */


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LeaderboardViewModel.class);
        // TODO: Use the ViewModel
    }

}