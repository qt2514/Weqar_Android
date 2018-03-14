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
import android.widget.TextView;

import com.weqar.weqar.R;
import com.weqar.weqar.Settings_AccountActivity;
import com.weqar.weqar.Settings_ProfileActivity;

public class BotNav_SettingsFragment_Vendor extends Fragment {
    public static BotNav_SettingsFragment_Vendor newInstance() {
        BotNav_SettingsFragment_Vendor fragment = new BotNav_SettingsFragment_Vendor();
        return fragment;
    }

    ImageView IV_set_profile, IV_set_account;
    TextView TV_set_profile, TV_set_account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot_nav__settings_fragment__vendor, container, false);
        IV_set_profile = view.findViewById(R.id.WIV_set_profile);
        TV_set_profile = view.findViewById(R.id.WTV_set_profile);
        IV_set_account = view.findViewById(R.id.WIV_set_account);
        TV_set_account = view.findViewById(R.id.WTV_set_account);
        IV_set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_ProfileActivity.class));
            }
        });
        TV_set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_ProfileActivity.class));
            }
        });
        IV_set_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_AccountActivity.class));
            }
        });
        TV_set_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_AccountActivity.class));
            }
        });


        return view;
    }
}
