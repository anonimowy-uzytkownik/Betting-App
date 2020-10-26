package com.example.praca_dyplomowa.ui.matches;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.EditText;
import android.widget.ListView;

import com.example.praca_dyplomowa.Match;
import com.example.praca_dyplomowa.MatchListAdapter;
import com.example.praca_dyplomowa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        final ArrayList<Match> matchList = new ArrayList<>();
        final MatchListAdapter adapter = new MatchListAdapter(getContext(),R.layout.adapter_view_layout,matchList);
        mListView.setAdapter(adapter);



        Query reference = FirebaseDatabase.getInstance().getReference().child("Matches");
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

                    Match match = new Match(Team1,Team2,Win1,Draw,Win2,Result, Image1, Image2);
                    matchList.add(match);

                    adapter.notifyDataSetChanged();

                    Log.i("DataSnapshot",snapshot.getValue().toString());


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DataSnapshot",databaseError.getMessage());
            }
        });


        /*
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Matches");
        usersRef.child("3").setValue(new Match("Atletico Madrid","Red Bull Salzburg","1.56","4.14","5.3","0:0","https://logos-world.net/wp-content/uploads/2020/06/atletico-madrid-Logo.png","https://www.logofootball.net/wp-content/uploads/FC-Red-Bull-Salzburg-HD-Logo.png"));
        usersRef.child("4").setValue(new Match("Porto","Olympiacos","1.59","3.8","5.6","0:0","https://i.pinimg.com/originals/62/18/71/621871d5af93bcd67f63d056ad6e7af1.png","https://seeklogo.com/images/O/Olympiacos_FC-logo-8F8F1A05DD-seeklogo.com.png"));
        usersRef.child("5").setValue(new Match("Barcelona","Ferencvarosz","1.11","20","5.6","3:0","https://logos-world.net/wp-content/uploads/2020/04/Barcelona-Logo.png","https://upload.wikimedia.org/wikipedia/commons/5/5c/Ferencv%C3%A1rosiTClog%C3%B3.png"));
        usersRef.child("6").setValue(new Match("Lokomotiv Moscow","Bayern Munich","15","3.8","1.13","1:1","https://logos-world.net/wp-content/uploads/2020/06/atletico-madrid-Logo.png","https://www.logofootball.net/wp-content/uploads/FC-Red-Bull-Salzburg-HD-Logo.png"));
        */

     //   return inflater.inflate(R.layout.fragment_matches, container, false);  crashuje się gdy returnuje inną wersje iflatera niz ta zdeklarowania u góry
        return rootView;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        // TODO: Use the ViewModel

    }




    }


