package com.weqar.weqar.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weqar.weqar.R;


public class BotNav_EventsFragment extends Fragment {

    public static BotNav_EventsFragment newInstance() {
        BotNav_EventsFragment fragment= new BotNav_EventsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bot_nav__events, container, false);
    }

}
