package com.example.praca_dyplomowa.ui.scores;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.praca_dyplomowa.Match;
import com.example.praca_dyplomowa.MatchListAdapter;
import com.example.praca_dyplomowa.R;
import com.example.praca_dyplomowa.ScoresAdapter;
import com.example.praca_dyplomowa.ScoresExtended;
import com.example.praca_dyplomowa.ui.matches.MatchesViewModel;
import com.example.praca_dyplomowa.ui.profile.ProfileFragment;
import com.example.praca_dyplomowa.ui.profile.ProfilePasswordChange;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScoresFragment extends Fragment {

    private ScoresViewModel mViewModel;

    public static ScoresFragment newInstance() {
        return new ScoresFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
        ListView mListView = (ListView)rootView.findViewById(R.id.listViewMatches);
        RadioButton radioButtonChampions = (RadioButton)rootView.findViewById(R.id.radioButtonUefaChampionsLeague);
        RadioButton radioButtonEuropa = (RadioButton)rootView.findViewById(R.id.radioButtonEuropaLeague);
        RadioButton radioButtonNations = (RadioButton)rootView.findViewById(R.id.radioButtonUefaNationsLeague);

        final ArrayList<Match> matchList = new ArrayList<>();
        final ScoresAdapter adapter = new ScoresAdapter(getContext(),R.layout.adapter_scores,matchList);
        mListView.setAdapter(adapter);


        radioButtonChampions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchList.clear();
                adapter.notifyDataSetChanged();

                final Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").child("ChampionsLeague");
                matchList.clear();
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            String Team1 = String.valueOf(snapshot.child("team1Name").getValue());
                            String Team2 = String.valueOf(snapshot.child("team2Name").getValue());
                            String Draw = String.valueOf(snapshot.child("win2odds").getValue());
                            String Win1 = String.valueOf(snapshot.child("win1odds").getValue());
                            String Win2 = String.valueOf(snapshot.child("win3odds").getValue());
                            String Result = String.valueOf(snapshot.child("result").getValue());
                            String Image1 = String.valueOf(snapshot.child("image1").getValue());
                            String Image2 = String.valueOf(snapshot.child("image2").getValue());
                            String League = "ChampionsLeague";
                            String MatchId = String.valueOf(snapshot.getKey());
                            String Status = String.valueOf(snapshot.child("status").getValue());
                            if(Status.equals("done")) {
                                Match match = new Match(Team1, Team2, Win1, Draw, Win2, Result, Image1, Image2, League, MatchId);
                                matchList.add(match);

                                adapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("DataSnapshot",databaseError.getMessage());
                    }
                });
            }
        });
        radioButtonEuropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchList.clear();
                adapter.notifyDataSetChanged();

                Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").child("EuropaLeague");
                matchList.clear();
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String Team1 = String.valueOf(snapshot.child("team1Name").getValue());
                            String Team2 = String.valueOf(snapshot.child("team2Name").getValue());
                            String Draw = String.valueOf(snapshot.child("win2odds").getValue());
                            String Win1 = String.valueOf(snapshot.child("win1odds").getValue());
                            String Win2 = String.valueOf(snapshot.child("win3odds").getValue());
                            String Result = String.valueOf(snapshot.child("result").getValue());
                            String Image1 = String.valueOf(snapshot.child("image1").getValue());
                            String Image2 = String.valueOf(snapshot.child("image2").getValue());
                            String League = "EuropaLeague";
                            String MatchId = String.valueOf(snapshot.getKey());
                            String Status = String.valueOf(snapshot.child("status").getValue());
                            if(Status.equals("done")) {
                                Match match = new Match(Team1, Team2, Win1, Draw, Win2, Result, Image1, Image2, League, MatchId);
                                matchList.add(match);

                                adapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("DataSnapshot",databaseError.getMessage());
                    }
                });
            }
        });
        radioButtonNations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchList.clear();
                adapter.notifyDataSetChanged();

                Query reference = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague");
                matchList.clear();
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            String Team1 = String.valueOf(snapshot.child("team1Name").getValue());
                            String Team2 = String.valueOf(snapshot.child("team2Name").getValue());
                            String Draw = String.valueOf(snapshot.child("win2odds").getValue());
                            String Win1 = String.valueOf(snapshot.child("win1odds").getValue());
                            String Win2 = String.valueOf(snapshot.child("win3odds").getValue());
                            String Result = String.valueOf(snapshot.child("result").getValue());
                            String Image1 = String.valueOf(snapshot.child("image1").getValue());
                            String Image2 = String.valueOf(snapshot.child("image2").getValue());
                            String League = "NationsLeague";
                            String MatchId = String.valueOf(snapshot.getKey());
                            String Status = String.valueOf(snapshot.child("status").getValue());

                            if(Status.equals("done")) {
                                Match match = new Match(Team1, Team2, Win1, Draw, Win2, Result, Image1, Image2, League, MatchId);
                                matchList.add(match);

                                adapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("DataSnapshot",databaseError.getMessage());
                    }
                });
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();



            }
        });

        radioButtonNations.callOnClick();


/*
        String testimage="https://upload.wikimedia.org/wikipedia/commons/d/d5/Japan_small_icon.png";

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague");
        usersRef.push().setValue(new Match("3Atletico Madrid","3Red Bull Salzburg","1.56","4.14","5.3","0:0",testimage,testimage));
        usersRef.push().setValue(new Match("3Porto","3Olympiacos","1.59","3.8","5.6","0:0",testimage,testimage));
        usersRef.push().setValue(new Match("3Barcelona","3Ferencvarosz","1.11","20","5.6","3:0",testimage,testimage));
        usersRef.push().setValue(new Match("3Lokomotiv Moscow","3Bayern Munich","15","3.8","1.13","1:1",testimage,testimage));
*/
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScoresViewModel.class);
        // TODO: Use the ViewModel

    }




}

