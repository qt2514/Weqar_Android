package com.weqar.weqar.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBHandlers.SessionManager;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.LoginActivity;
import com.weqar.weqar.R;
import com.weqar.weqar.Settings_AccountActivity;
import com.weqar.weqar.Settings_ProfileActivity_User;
import com.weqar.weqar.Settings_ProfileActivity_Vendor;

import de.hdodenhof.circleimageview.CircleImageView;

public class BotNav_SettingsFragment_Vendor extends Fragment {
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String s_lnw_usermailid,s_lnw_usertype,s_lnw_userid,s_lnw_usertoken,s_lnw_companyname,s_lnw_image;
    CircleImageView CV_uersset_image;

    public static BotNav_SettingsFragment_Vendor newInstance() {
        BotNav_SettingsFragment_Vendor fragment = new BotNav_SettingsFragment_Vendor();
        return fragment;
    }

    ImageView IV_set_profile, IV_set_account,IV_set_logout;
    TextView TV_set_profile, TV_set_account,TV_set_logout,TV_user_name,TV_user_email;
    private SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot_nav__settings_fragment__vendor, container, false);
        IV_set_profile = view.findViewById(R.id.WIV_set_profile);
        TV_set_profile = view.findViewById(R.id.WTV_set_profile);
        IV_set_account = view.findViewById(R.id.WIV_set_account);
        TV_set_account = view.findViewById(R.id.WTV_set_account);

        IV_set_logout = view.findViewById(R.id.IV_set_v_logout);
        TV_set_logout = view.findViewById(R.id.TV_set_v_logout);
        TV_user_name=view.findViewById(R.id.Settings_company_name);
        TV_user_email=view.findViewById(R.id.Settings_company_email);
        CV_uersset_image=view.findViewById(R.id.Settings_company_image);
        session = new SessionManager(getActivity());
        session = new SessionManager(getActivity());
        Shared_user_details=getActivity().getSharedPreferences("user_detail_mode",0);
        s_lnw_usermailid=  Shared_user_details.getString("sp_w_useremail", null);
        s_lnw_usertype=  Shared_user_details.getString("sp_w_usertype", null);
        s_lnw_userid= Shared_user_details.getString("sp_w_userid", null);
        s_lnw_usertoken= Shared_user_details.getString("sp_w_apikey", null);
        s_lnw_companyname= Shared_user_details.getString("sp_w_username", null);
        s_lnw_image=Shared_user_details.getString("sp_w_image",null);
        TV_user_name.setText(s_lnw_companyname);
        TV_user_email.setText(s_lnw_usermailid);

//        try {
//
//            Picasso.with(getActivity()).load(Global_URL.Image_url_load+s_lnw_image).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CV_uersset_image);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
        IV_set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_ProfileActivity_Vendor.class));
            }
        });
        TV_set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_ProfileActivity_Vendor.class));
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
        IV_set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                session.setLogin(false);
                PreferenceManager.getDefaultSharedPreferences(getActivity()).
                        edit().clear().apply();
                startActivity(intent);


            }
        });
        TV_set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);

                session.setLogin(false);
                PreferenceManager.getDefaultSharedPreferences(getActivity()).
                        edit().clear().apply();
                startActivity(intent);

            }
        });

        return view;
    }
}
