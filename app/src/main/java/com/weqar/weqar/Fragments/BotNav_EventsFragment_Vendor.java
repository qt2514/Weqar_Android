package com.weqar.weqar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.weqar.weqar.AddEvents_Vendor;
import com.weqar.weqar.R;

public class BotNav_EventsFragment_Vendor extends Fragment {
ImageView IV_adddiscount;
ListView LV_vendor_events;
    public static BotNav_EventsFragment_Vendor newInstance() {
        BotNav_EventsFragment_Vendor fragment= new BotNav_EventsFragment_Vendor();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_bot_nav__events_fragment__vendor, container, false);
        IV_adddiscount=v.findViewById(R.id.homescreen_addevent);
        LV_vendor_events=v.findViewById(R.id.events_vendor_listview);
        IV_adddiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddEvents_Vendor.class));
            }
        });
        return v;
    }

}
