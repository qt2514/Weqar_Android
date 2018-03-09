package com.weqar.weqar.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.weqar.weqar.DiscountDetails;
import com.weqar.weqar.R;

public class BotNav_DiscountsFragment extends Fragment {
    public static BotNav_DiscountsFragment newInstance() {
        BotNav_DiscountsFragment fragment= new BotNav_DiscountsFragment();
        return fragment;
    }
ImageView IV_roundimg_one,IV_roundimg_two,IV_roundimg_three;
    RelativeLayout R_one,R_two,R_three;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__discounts, container, false);
        R_one=view.findViewById(R.id.relative_discountone);
        R_two=view.findViewById(R.id.relative_discounttwo);
        R_three=view.findViewById(R.id.relative_discountthree);

        R_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DiscountDetails.class));

            }
        });
        R_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DiscountDetails.class));

            }
        });
        R_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DiscountDetails.class));

            }
        });
        return view;
    }


}
