package com.weqar.weqar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weqar.weqar.R;

public class BotNav_EventsFragment_Vendor extends Fragment {

    public static BotNav_EventsFragment_Vendor newInstance() {
        BotNav_EventsFragment_Vendor fragment= new BotNav_EventsFragment_Vendor();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bot_nav__events_fragment__vendor, container, false);
    }

}
