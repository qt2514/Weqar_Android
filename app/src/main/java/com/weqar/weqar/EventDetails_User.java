package com.weqar.weqar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;


public class EventDetails_User extends AppCompatActivity {
    ImageView event_vdet_back,event_vdet_image;
    String get_event_id,s_vendor_disc,s_vendor_token;
    String event_id,user_id,event_title,event_name,event_image,event_desc,event_location,event_lati,event_longi,
            event_start,event_end,event_duration,event_amount;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    TextView TV_title,TV_startdate,TV_place,TV_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details__user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        event_vdet_back=findViewById(R.id.event_udet_back);
        event_vdet_image=findViewById(R.id.event_udet_image);
        TV_title=findViewById(R.id.event_det_u_title);
        TV_startdate=findViewById(R.id.event_det_u_startdate);
        TV_place=findViewById(R.id.event_det_u_place);
        TV_desc=findViewById(R.id.event_det_u_desc);


        event_vdet_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventDetails_User.this,Events_Display.class));
                finish();
            }
        });
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);
        try
        {


            Intent intent=getIntent();
            get_event_id=intent.getStringExtra("event_v_id");

            user_id=intent.getStringExtra("event_v_userid");
            event_title=intent.getStringExtra("event_v_title");
            event_name= intent.getStringExtra("event_v_name");
            event_image=intent.getStringExtra("event_v_image");
            event_desc=intent.getStringExtra("event_v_desc");
            event_location=intent.getStringExtra("event_v_location");
            event_lati=intent.getStringExtra("event_v_latitude");
            event_longi=intent.getStringExtra("event_v_longitude");
            event_start=intent.getStringExtra("event_v_startdate");
            event_end=intent.getStringExtra("event_v_enddate");
            event_duration=intent.getStringExtra("event_v_duration");
            event_amount=intent.getStringExtra("event_v_amount");
            TV_title.setText(event_title);
            TV_startdate.setText("Start Date : "+event_start);

            // TV_place.setText(city+state);
            TV_desc.setText(event_desc);
            TV_title.setText(event_title);
            Picasso.with(EventDetails_User.this).load(Global_URL.Image_url_load+event_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(event_vdet_image);
            TV_place.setText(event_location);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //   getallmyeventdetails(get_event_id);


    }

}
