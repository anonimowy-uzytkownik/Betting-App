package com.example.praca_dyplomowa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.praca_dyplomowa.ui.matches.MatchesPlaceBet;
import com.example.praca_dyplomowa.ui.scores.ScoresFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ScoresAdapter extends ArrayAdapter<Match> {

    private final Context mContext;
    int mResource;

    public ScoresAdapter(Context context, int resource, ArrayList<Match> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        final String team1Name = getItem(position).getTeam1Name();
        final String team2Name = getItem(position).getTeam2Name();
        final String result = getItem(position).getResult();
        String image1 = getItem(position).getImage1();
        String image2 = getItem(position).getImage2();
        final String league = getItem(position).getLeague();
        final String matchId = getItem(position).getMatchId();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTeam1Name = convertView.findViewById(R.id.textViewTeam1Name);
        TextView tvTeam2Name = convertView.findViewById(R.id.textViewTeam2Name);
        TextView tvResult = convertView.findViewById(R.id.textViewResult);

        final Button buttonMatchExtended = convertView.findViewById(R.id.buttonMatchExtended);

        ImageView imgTeam1 = convertView.findViewById(R.id.imageViewTeam1);
        ImageView imgTeam2 = convertView.findViewById(R.id.imageViewTeam2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tvTeam1Name.setText(team1Name);
        tvTeam2Name.setText(team2Name);
        tvResult.setText(result);

        try {
            URL url = new URL(image1);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imgTeam1.setImageBitmap(image);

            URL url2 = new URL(image2);
            image = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            imgTeam2.setImageBitmap(image);
        } catch (IOException e) {
        }

        buttonMatchExtended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoresExtended = new Intent(mContext, ScoresExtended.class);
                scoresExtended.putExtra("league",league);
                scoresExtended.putExtra("matchId",matchId);
                mContext.startActivity(scoresExtended);
            }
        });

        return convertView;
    }
}