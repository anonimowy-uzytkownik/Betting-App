package com.example.praca_dyplomowa.ui.matches;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.praca_dyplomowa.Match;
import com.example.praca_dyplomowa.MatchListAdapter;
import com.example.praca_dyplomowa.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MatchesFragment extends Fragment {

    private MatchesViewModel mViewModel;

    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d("test","onCreate: Started.");

        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
        ListView mListView = (ListView)rootView.findViewById(R.id.listViewMatches);

        Match AjaxBarcelona = new Match("Ajax","Barcelona","1.2","2.5","1.3","0:0");
        Match AjaxBarcelona2 = new Match("Ajax2","Barcelona2","1.2","2.5","1.3","0:0");

        ArrayList<Match> matchList = new ArrayList<>();
        matchList.add(AjaxBarcelona);
        matchList.add(AjaxBarcelona2);

        MatchListAdapter adapter = new MatchListAdapter(getActivity(),R.layout.adapter_view_layout,matchList);
        mListView.setAdapter(adapter);

        Log.d("test","onCreate: Started2.");

        return inflater.inflate(R.layout.fragment_matches, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        // TODO: Use the ViewModel


    }




    }
