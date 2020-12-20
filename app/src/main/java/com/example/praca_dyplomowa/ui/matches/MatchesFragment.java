package com.example.praca_dyplomowa.ui.matches;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praca_dyplomowa.Match;
import com.example.praca_dyplomowa.MatchExtended;
import com.example.praca_dyplomowa.MatchListAdapter;
import com.example.praca_dyplomowa.Message;
import com.example.praca_dyplomowa.R;
import com.example.praca_dyplomowa.ui.bets.BetsFragment;
import com.example.praca_dyplomowa.ui.scores.ScoresFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collections;

public class MatchesFragment extends Fragment  {

    private MatchesViewModel mViewModel;

    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        final MatchListAdapter adapter = new MatchListAdapter(getContext(),R.layout.adapter_view_layout,matchList);
        mListView.setAdapter(adapter);


    /*
        try{
            if(getActivity().getIntent().getAction()=="OPEN_MY_BETS")
            {
                Fragment fragment = new BetsFragment();


        //        getActivity().getSupportFragmentManager().beginTransaction()
          //              .replace(R.id.relativeLayoutMatches, fragment).remove(MatchesFragment.this).commit();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.relativeLayoutMatches, fragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

               // setTargetFragment(this,fragment);

//R.id.relativeLayoutMatches
            }
        } catch (Error error){}


*/



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
                            if(!Status.equals("done")) {
                                Match match = new Match(Team1, Team2, Win1, Draw, Win2, Result, Image1, Image2, League, MatchId);
                                matchList.add(match);

                                if(snapshot.getChildrenCount()==matchList.size())
                                    Collections.reverse(matchList);
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
                            if(!Status.equals("done")) {
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
                            if(!Status.equals("done")) {
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
               // Toast.makeText(getContext(), "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        radioButtonNations.callOnClick();


/*
        String testimage="https://upload.wikimedia.org/wikipedia/commons/d/d5/Japan_small_icon.png";

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague");
        usersRef.push().setValue(new Match("3Atletico Madrid","3Red Bull Salzburg","1.56","4.14","5.3","0:0",testimage,testimage));
        usersRef.push().setValue(new Match("3Porto","3Olympiacos","1.59","3.8","5.6","0:0",testimage,testimage));
        usersRef.push().setValue(new Match("3Barcelona","3Ferencvarosz","1.11","20","5.6","3:0",testimage,testimage));
*/




/*
        //String testimage="https://upload.wikimedia.org/wikipedia/commons/d/d5/Japan_small_icon.png";
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Matches").child("EuropaLeague");


        usersRef.push().setValue(new Match("Leverkusen","Slavia Praha","1.65","4.00","4.55","0:0"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Fleverkusen.png?alt=media&token=f47f800a-ae63-4399-9793-b4019ff196f2"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Fslavia.png?alt=media&token=aa6a3681-92c7-4722-876f-ba824c90c0bc"));

        usersRef.push().setValue(new Match("CSKA Sofia","AS Roma","4.45","3.92","1.68","0:0"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Fcska.png?alt=media&token=f274a922-eb36-4f77-9d57-7423c8bfe576"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Froma.png?alt=media&token=065420de-4420-4a77-9b10-364ff2da5ee5"));

        usersRef.push().setValue(new Match("Standard Liege","Benfica","4.70","3.90","1.68","0:0"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Fliege.png?alt=media&token=6f65541f-d1a2-4641-b5a6-4faea134119d"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Fbenfica.png?alt=media&token=ccf1d9e5-75e2-4498-a69d-660f28efc7c4"));

        usersRef.push().setValue(new Match("Lech Poznan","Rangers","4.80","4.00","1.65","0:0"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Flech.png?alt=media&token=d52ed8b5-271e-4dab-9354-dcca5abce99a"
                ,"https://firebasestorage.googleapis.com/v0/b/projekty-dyplomowy.appspot.com/o/flags%2Frangers.png?alt=media&token=14c43163-c8af-488e-b49a-a2e9578eb91f"));
*/

/*
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague");


        usersRef.child("-MNLPtQPd7TCVX01vzPA").child("details").setValue(new MatchExtended(
                "12","20","8","7","44%","56%"
                ,"359","445","76%","81%","8","14"
                ,"2","3","0","0","1","1",
                "6","7"
        ));
        usersRef.child("-MNLPtQQMUqEcHU2KnSh").child("details").setValue(new MatchExtended(
                "9","12","4","5","52%","48%"
                ,"499","470","77%","78%","17","12"
                ,"4","1","0","0","3","6",
                "5","3"
        ));
       usersRef.child("-MNLPtQRfULwxrf9k8O_").child("details").setValue(new MatchExtended(
                "25","2","10","0","79%","21%"
                ,"767","206","90%","59%","14","17"
                ,"1","2","0","1","3","1",
                "8","2"
        ));
        usersRef.child("-MNLPtQSfxQlgBLPKc0Z").child("details").setValue(new MatchExtended(
                "9","11","4","1","62%","38%"
                ,"557","327","83%","70%","15","12"
                ,"2","2","1","0","4","4",
                "2","3"
        ));
        */

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        // TODO: Use the ViewModel

    }




    }


