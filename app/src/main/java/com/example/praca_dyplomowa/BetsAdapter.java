package com.example.praca_dyplomowa;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class BetsAdapter extends ArrayAdapter<BetFinished> {

    private Context mContext;
    int mResource;

    public BetsAdapter(Context context, int resource, ArrayList<BetFinished> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String team1Name = getItem(position).getTeam1Name();
        String team2Name = getItem(position).getTeam2Name();
        String coinsWon = getItem(position).getCoinsWon();
        String coinsLost = getItem(position).getCoinsLost();
        boolean won = getItem(position).isWon();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView textViewTeam1Name =(TextView) convertView.findViewById(R.id.textViewTeam1Name);
        TextView textViewTeam2Name =(TextView) convertView.findViewById(R.id.textViewTeam2Name);
        TextView textViewCoins =(TextView) convertView.findViewById(R.id.textViewCoins);

        textViewTeam1Name.setText(team1Name);
        textViewTeam2Name.setText(team2Name);



        if(won)
        {
            textViewCoins.setText(coinsWon);
            textViewCoins.setBackgroundColor(Color.parseColor("#34e62f"));
        }
        else
        {
            textViewCoins.setText(coinsLost);
            textViewCoins.setBackgroundColor(Color.parseColor("#dd2424"));
        }


        return convertView;
    }

}

