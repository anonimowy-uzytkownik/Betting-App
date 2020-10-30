package com.example.praca_dyplomowa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
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

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends ArrayAdapter<Match> {

    private static final String TAG = "MatchListAdapter";

    private Context mContext;
    int mResource;


    private static class ViewHolder {

        TextView tvTeam1Name;
        TextView tvTeam2Name ;
        TextView tvResult;
        Button btnTeam1Odds;
        Button btnTeam2Odds;
        Button btnDrawOdds;
        ImageView imgTeam1;
        ImageView imgTeam2;
    }


    public MatchListAdapter(Context context, int resource, ArrayList<Match> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {




        final String team1Name = getItem(position).getTeam1Name();
        String team2Name= getItem(position).getTeam2Name();
        String win1odds= getItem(position).getWin1odds();
        String win2odds= getItem(position).getWin2odds();
        String win3odds= getItem(position).getWin3odds();
        String result= getItem(position).getResult();
        String image1= getItem(position).getImage1();
        String image2= getItem(position).getImage2();
 /*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

       ViewHolder viewHolder;

        if (convertView == null) {

            // If there's no view to re-use, inflate a brand new view for row

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource,parent,false);

            viewHolder.tvTeam1Name = (TextView) convertView.findViewById(R.id.textViewTeam1Name);
            viewHolder.tvTeam2Name = (TextView) convertView.findViewById(R.id.textViewTeam2Name);
            viewHolder.tvResult = (TextView) convertView.findViewById(R.id.textViewResult);
            viewHolder.btnDrawOdds = (Button) convertView.findViewById(R.id.buttonTeamDraw);
            viewHolder.btnTeam1Odds=(Button) convertView.findViewById(R.id.buttonTeam1);
            viewHolder.btnTeam2Odds = (Button) convertView.findViewById(R.id.buttonTeam2);
            viewHolder.imgTeam1= (ImageView) convertView.findViewById(R.id.imageViewTeam1);
            viewHolder.imgTeam2= (ImageView) convertView.findViewById(R.id.imageViewTeam2);


            // Cache the viewHolder object inside the fresh view

            convertView.setTag(viewHolder);

        } else {

            // View is being recycled, retrieve the viewHolder object from tag

            viewHolder = (ViewHolder) convertView.getTag();

        }

        // Populate the data from the data object via the viewHolder object

        // into the template view.

        viewHolder.tvTeam1Name.setText(team1Name);
        viewHolder.tvTeam2Name.setText(team2Name);
        viewHolder.tvResult.setText(result);
        viewHolder.btnDrawOdds.setText(win2odds);
        viewHolder.btnTeam1Odds.setText(win1odds);
        viewHolder.btnTeam2Odds.setText(win3odds);



        try {
            URL url = new URL(image1);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            viewHolder.imgTeam1.setImageBitmap(image);
            URL url2 = new URL(image2);
            image = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            viewHolder.imgTeam2.setImageBitmap(image);
        } catch(IOException e) {

        }
*/




        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvTeam1Name = (TextView) convertView.findViewById(R.id.textViewTeam1Name);
        TextView tvTeam2Name = (TextView) convertView.findViewById(R.id.textViewTeam2Name);
        TextView tvResult = (TextView) convertView.findViewById(R.id.textViewResult);
        final Button btnTeam1Odds = (Button) convertView.findViewById(R.id.buttonTeam1);
        Button btnTeam2Odds = (Button) convertView.findViewById(R.id.buttonTeam2);
        Button btnDrawOdds = (Button) convertView.findViewById(R.id.buttonTeamDraw);

        ImageView imgTeam1 = (ImageView) convertView.findViewById(R.id.imageViewTeam1);
        ImageView imgTeam2 = (ImageView) convertView.findViewById(R.id.imageViewTeam2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        tvTeam1Name.setText(team1Name);
        tvTeam2Name.setText(team2Name);
        tvResult.setText(result);
        btnTeam1Odds.setText(win1odds);
        btnTeam2Odds.setText(win3odds);
        btnDrawOdds.setText(win2odds);


        try {
            URL url = new URL(image1);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imgTeam1.setImageBitmap(image);

            URL url2 = new URL(image2);
            image = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            imgTeam2.setImageBitmap(image);
            }
        catch(IOException e) {}


        btnTeam1Odds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("btnTeam1Odds","OnClickListener");
                Log.d("btnTeam1Odds",btnTeam1Odds.getText().toString());
                Log.d("txbTeam1Name",team1Name);
                Toast.makeText(getContext(), "nazwa zespolu " + team1Name, Toast.LENGTH_SHORT).show();
                btnTeam1Odds.setEnabled(false);
            }
        });
/*
        try {
            URL url = new URL(image1);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG,1,stream);

            byte[] b = stream.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(b);
            Drawable d = Drawable.createFromStream(is, "bloodsample");
            imgTeam1.setImageDrawable(d);

            URL url2 = new URL(image2);
            Bitmap imagee = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            imagee.compress(Bitmap.CompressFormat.PNG,1,stream);
            b = stream.toByteArray();
            is = new ByteArrayInputStream(b);
            d = Drawable.createFromStream(is, "bloodsample");
            imgTeam2.setImageDrawable(d);
            }
        catch(IOException e) {} */






        return convertView;
    }






}


