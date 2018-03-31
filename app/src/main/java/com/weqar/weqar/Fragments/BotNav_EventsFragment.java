package com.weqar.weqar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.weqar.weqar.HomeScreen;
import com.weqar.weqar.R;

import static com.thefinestartist.utils.service.ServiceUtil.getSystemService;


public class BotNav_EventsFragment extends Fragment {
    public static BotNav_EventsFragment newInstance() {
        BotNav_EventsFragment fragment= new BotNav_EventsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__events, container, false);




            return view;
    }

}
