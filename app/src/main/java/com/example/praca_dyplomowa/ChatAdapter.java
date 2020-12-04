package com.example.praca_dyplomowa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<Message> {

    private Context mContext;
    int mResource;

    public ChatAdapter(Context context, int resource, ArrayList<Message> objects) {
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
        String messageTime = getItem(position).getMessageTime();
        String message = getItem(position).getMessage();
        //String avatar = getItem(position).getAvatar();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvDisplayName =(TextView) convertView.findViewById(R.id.textViewDisplayName);
        TextView tvMessageTime =(TextView) convertView.findViewById(R.id.textViewMessageTime);
        TextView tvMessage =(TextView) convertView.findViewById(R.id.textViewMessage);
       // ImageView imageViewAvatar = convertView.findViewById(R.id.imageViewAvatar);

        tvDisplayName.setText(displayName);
        tvMessageTime.setText(messageTime);
        tvMessage.setText(message);





        return convertView;
    }

}
