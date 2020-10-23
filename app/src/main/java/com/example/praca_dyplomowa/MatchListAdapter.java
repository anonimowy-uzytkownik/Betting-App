package com.example.praca_dyplomowa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        tvTeam1Name.setText(team1Name);
        tvTeam2Name.setText(team2Name);
        tvResult.setText(result);

        return convertView;
    }
}
