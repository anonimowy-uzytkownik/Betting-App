package com.example.praca_dyplomowa.ui.bets;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.praca_dyplomowa.BetFinished;
import com.example.praca_dyplomowa.BetsAdapter;
import com.example.praca_dyplomowa.LeaderboardTopThreeAdapter;
import com.example.praca_dyplomowa.LeaderboardUser;
import com.example.praca_dyplomowa.Message;
import com.example.praca_dyplomowa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BetsFragment extends Fragment {

    private BetsViewModel mViewModel;
    ListView listViewBets;

    public static BetsFragment newInstance() {
        return new BetsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bets, container, false);
        listViewBets = view.findViewById(R.id.listViewBets);

        final ArrayList<BetFinished> betFinishedList = new ArrayList<>();
        final BetsAdapter adapterBets = new BetsAdapter(getContext(),R.layout.adapter_bets,betFinishedList);
        listViewBets.setAdapter(adapterBets);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query reference = FirebaseDatabase.getInstance().getReference().child("BettingHistory").child(String.valueOf(user.getEmail().hashCode())).limitToLast(20);
        betFinishedList.clear();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    String team1Name = String.valueOf(snapshot.child("team1Name").getValue());
                    String team2Name = String.valueOf(snapshot.child("team2Name").getValue());
                    String coinsWon = String.valueOf(snapshot.child("coinsWon").getValue());
                    String coinsLost = String.valueOf(snapshot.child("coinsLost").getValue());
                    String result = String.valueOf(snapshot.child("result").getValue());

                    Log.d("coinswon",coinsWon);
                    BetFinished betFinished;
                    if(coinsWon=="null")
                    betFinished = new BetFinished(team1Name,team2Name,result,coinsLost);

                    else
                    betFinished = new BetFinished(team1Name,team2Name,result,coinsWon,true);

                    betFinishedList.add(betFinished);

                    Log.d(String.valueOf(dataSnapshot.getChildrenCount()),String.valueOf(betFinishedList.size()));
                    if(dataSnapshot.getChildrenCount()==betFinishedList.size())
                    Collections.reverse(betFinishedList);

                    adapterBets.notifyDataSetChanged();

                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BetsViewModel.class);
        // TODO: Use the ViewModel
    }

}