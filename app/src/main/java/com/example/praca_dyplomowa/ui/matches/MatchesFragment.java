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
        String testimage="https://upload.wikimedia.org/wikipedia/commons/d/d5/Japan_small_icon.png";
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Matches").child("NationsLeague");


        usersRef.push().setValue(new Match("Serbia","Russia","2.90","3.45","2.46","5:0"
                ,"https://lh3.googleusercontent.com/proxy/zKEAu-uC0HImqiBWHT-LESdsFJeoVParsgFCaY0BjaPow0p8_tbgKV4kN9nV6cct4JF8vFluUtyq7cyKGt4QdAkxh5b_GUZSHATfvy6JgvR_2_StnWkM7jc6MqHZuEp5Ugr1_7tvtNhoeBX4926jp2nzhD2h42sjsKB0lOAVo9t2svfJoVLbNtNp"
                ,"https://www.shareicon.net/data/2015/07/23/73664_flag_256x256.png"));

        usersRef.push().setValue(new Match("Hungary","Turkey","3.61","3.51","2.07","2:0"
                ,"https://2.bp.blogspot.com/-oAY4T76_BiU/UL0u507y0vI/AAAAAAAB4xA/_q_xwY2VmJo/s1600/Hungary_Flag2.png"
                ,"https://njq-ip.com/wp-content/uploads/2014/256/Turkey-Flag.png"));

        usersRef.push().setValue(new Match("England","Iceland","1.15","8.14","17.98","4:0"
                ,"https://lh3.googleusercontent.com/proxy/QnSDYlTMm50efsXQJvn-YlCLT4G9DwMTNJvB71dmsuwTliPOuOUl_bg6dhpC1dotfTddVGOHa6qFUDJnP59mysnIh2Rkejqv0_HnuJavAw-J4iTPiX0ve035MgmLrX93FMS2JC9ZtzVSkHVy9wJMAzGabc1tM0FgAgwqs8Dnr2y9"
                ,"https://lh3.googleusercontent.com/proxy/5K_MLonVezq_vycWJT5lDzGGmmQQTr2qv6AH3q-wLIDbLfoCdYE82UM4c8GeprONWKxJzapYT3toOcpoTzeekmOvA-bIkhSP_frm1KNHkNbG7g"));

        usersRef.push().setValue(new Match("Kosovo","Moldova","1.47","4.03","8.04","1:0"
                ,"https://lh3.googleusercontent.com/proxy/kWV6HQpMJjXHFYoyAcjh1JgY91i7n5bonwKNGVg04rJADoG7upVDXE_cuIKl4JRLSoseYaYdyhXWC-dh_4DaoyOupVhAlodsHZq88Mkn2QJotHo"
                ,"https://i.pinimg.com/originals/7c/c7/dc/7cc7dccd619de51abe6dcc78b63eaca8.png"));

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


