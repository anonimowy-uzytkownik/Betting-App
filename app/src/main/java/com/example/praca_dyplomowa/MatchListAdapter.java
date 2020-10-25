package com.example.praca_dyplomowa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends ArrayAdapter<Match> {

    private static final String TAG = "MatchListAdapter";

    private Context mContext;
    int mResource;

    public MatchListAdapter(Context context, int resource, ArrayList<Match> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String team1Name = getItem(position).getTeam1Name();
        String team2Name= getItem(position).getTeam2Name();
        String win1odds= getItem(position).getWin1odds();
        String win2odds= getItem(position).getWin2odds();
        String win3odds= getItem(position).getWin3odds();
        String result= getItem(position).getResult();

        Match match = new Match(team1Name,team2Name,win1odds,win2odds,win3odds,result);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvTeam1Name = (TextView) convertView.findViewById(R.id.textViewTeam1Name);
        TextView tvTeam2Name = (TextView) convertView.findViewById(R.id.textViewTeam2Name);
        TextView tvResult = (TextView) convertView.findViewById(R.id.textViewResult);
        Button btnTeam1Odds = (Button) convertView.findViewById(R.id.buttonTeam1);
        Button btnTeam2Odds = (Button) convertView.findViewById(R.id.buttonTeam2);
        Button btnDrawOdds = (Button) convertView.findViewById(R.id.buttonTeamDraw);

        ImageView imgTeam1 = (ImageView) convertView.findViewById(R.id.imageViewTeam1);
        ImageView imgTeam2 = (ImageView) convertView.findViewById(R.id.imageViewTeam2);


        tvTeam1Name.setText(team1Name);
        tvTeam2Name.setText(team2Name);
        tvResult.setText(result);
        btnTeam1Odds.setText(win1odds);
        btnTeam2Odds.setText(win3odds);
        btnDrawOdds.setText(win2odds);

        return convertView;
    }
}
