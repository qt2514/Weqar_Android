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
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.weqar.weqar.HomeScreen;
import com.weqar.weqar.R;

import static com.thefinestartist.utils.service.ServiceUtil.getSystemService;


public class BotNav_EventsFragment extends Fragment {
    private FloatingActionMenu dash_menu;

    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;
    private com.github.clans.fab.FloatingActionButton fab4;

    public static BotNav_EventsFragment newInstance() {
        BotNav_EventsFragment fragment= new BotNav_EventsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__events, container, false);

        dash_menu = view.findViewById(R.id.menu_red);

        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        fab3 = view.findViewById(R.id.fab3);
        fab4 = view.findViewById(R.id.fab4);





        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);
        fab4.setOnClickListener(clickListener);


            return view;
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab1:
                    Toast.makeText(getActivity(), "News", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fab2:
                    Toast.makeText(getActivity(), "Events", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fab3:
                    Toast.makeText(getActivity(), "Discount", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fab4:
                    Toast.makeText(getActivity(), "Job", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
