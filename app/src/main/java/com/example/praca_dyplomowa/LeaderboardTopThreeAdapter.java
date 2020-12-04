package com.example.praca_dyplomowa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class LeaderboardTopThreeAdapter extends ArrayAdapter<LeaderboardUser> {

private Context mContext;
        int mResource;



    public LeaderboardTopThreeAdapter(Context context, int resource, ArrayList<LeaderboardUser> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }

    @NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String displayName = getItem(position).getDisplayName();
        String wins = getItem(position).getWins();
        String loses = getItem(position).getLoses();
        String coinsWon = getItem(position).getCoinsWon();
        String leaderboardRanking = getItem(position).getLeaderboardRanking();
        String avatar = getItem(position).getAvatar();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        ImageView imageViewAvatar = (ImageView) convertView.findViewById(R.id.imageViewAvatar);
        TextView textViewDisplayName =(TextView) convertView.findViewById(R.id.textViewDisplayName);
        TextView textViewCoinsWon =(TextView) convertView.findViewById(R.id.textViewCoinsWon);
        TextView textViewWins =(TextView) convertView.findViewById(R.id.textViewWins);
        TextView textViewLoses =(TextView) convertView.findViewById(R.id.textViewLoses);
        TextView textViewLeaderboardRanking =(TextView) convertView.findViewById(R.id.textViewleaderboardRanking);

        textViewDisplayName.setText(displayName);
        textViewCoinsWon.setText(coinsWon);
        textViewWins.setText(wins);
        textViewLoses.setText(loses);
        textViewLeaderboardRanking.setText(leaderboardRanking);

        try
        {
            URL url = new URL(avatar);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageViewAvatar.setImageBitmap(image);

        }
        catch(IOException e) {}

        return convertView;
        }

}