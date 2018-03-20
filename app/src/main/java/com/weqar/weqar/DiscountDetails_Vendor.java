package com.weqar.weqar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class DiscountDetails_Vendor extends AppCompatActivity {
TextView TV_disc_desc_title,TV_disc_desc_desc;
RatingBar disc_desc_rating;
String s_disc_desc_title,s_disc_desc_desc,s_disc_desc_rating;
ImageView IV_disc_back_vendor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TV_disc_desc_title=findViewById(R.id.disc_desc_title);
        TV_disc_desc_desc=findViewById(R.id.disc_dec_det);
        disc_desc_rating=findViewById(R.id.disc_desc_rating);
        IV_disc_back_vendor=findViewById(R.id.disc_back_vendor);
        IV_disc_back_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        s_disc_desc_title=intent.getStringExtra("put_title");
        s_disc_desc_rating= intent.getStringExtra("put_per");
        s_disc_desc_desc= intent.getStringExtra("put_desc");
        Integer k=Integer.parseInt(s_disc_desc_rating);
        Integer kk=k/10;
        Float g=(float) kk;
        disc_desc_rating.setRating(g);
        TV_disc_desc_title.setText(s_disc_desc_desc+"% "+s_disc_desc_title);
        TV_disc_desc_desc.setText(s_disc_desc_desc);
    }

}
