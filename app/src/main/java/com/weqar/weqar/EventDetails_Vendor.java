package com.weqar.weqar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class EventDetails_Vendor extends AppCompatActivity {
    ImageView event_vdet_back,event_vdet_image;
    String get_event_id,s_vendor_disc,s_vendor_token;
    String event_id,user_id,event_title,event_name,event_image,event_desc,event_location,event_lati,event_longi,
    event_start;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    TextView TV_title,TV_startdate,TV_place,TV_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        event_vdet_back=findViewById(R.id.event_vdet_back);
        event_vdet_image=findViewById(R.id.event_vdet_image);
        TV_title=findViewById(R.id.event_det_v_title);
        TV_startdate=findViewById(R.id.event_det_v_startdate);
        TV_place=findViewById(R.id.event_det_v_place);
        TV_desc=findViewById(R.id.event_det_v_desc);


        event_vdet_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventDetails_Vendor.this,Events_Display_Vendor.class));
            }
        });
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);

        Intent intent=getIntent();
        get_event_id=intent.getStringExtra("event_v_id");
        getallmyeventdetails(get_event_id);

    }
    public void getallmyeventdetails(String susername)
    {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", susername);



            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_show_event_onid, new Response.Listener<String>() {

                public void onResponse(String response) {
                    try {

                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("Success"))
                        {
                        try
                        {


                            JSONObject verification = jObj.getJSONObject("Response");
                            event_id=verification.getString("Id");
                            user_id=verification.getString("UserId");
                            event_title=verification.getString("Title");
                            event_name=verification.getString("Name");
                            event_image=verification.getString("Image");
                            event_desc=verification.getString("Description");
                            event_location=verification.getString("Location");
                            event_lati=verification.getString("Latitude");
                            event_longi=verification.getString("Longitude");
                            event_start=verification.getString("EventStart");
                            Geocoder geocoder = new Geocoder(EventDetails_Vendor.this, Locale.getDefault());
                            double latitude = Double.parseDouble(event_lati);
                            double longitude = Double.parseDouble(event_longi);
                            List<Address> addresses  = geocoder.getFromLocation(latitude,longitude, 1);

                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String zip = addresses.get(0).getPostalCode();
                            String country = addresses.get(0).getCountryName();

                            TV_title.setText(event_title);
                            TV_startdate.setText(event_start);
                            TV_place.setText(city+state);
                            TV_desc.setText(event_desc);
                            TV_title.setText(event_title);
                            Picasso.with(EventDetails_Vendor.this).load(Global_URL.Image_url_load+event_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(event_vdet_image);

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        }
                        else
                        {


                        }


                        //finish();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("x-api-type", "Android");
                    //headers.put("content-Type", "application/json");
                    headers.put("x-api-key",s_vendor_token);
                    return headers;

                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }




            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
