package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class Settings_Profile_Edit_Vendor extends AppCompatActivity {
    ImageView IV_S_user_profile_edit,IV_S_user_profile_image,IV_edit_profile_back;
    EditText ET_S_firstname,ET_S_middlename,ET_S_lastname,ET_S_email,ET_S_mobile,ET_S_address,ET_S_country;
    Button B_S_update;
    String s_user_token,s_user_id,s_user_address,s_user_image,s_user_fname,s_user_mname,s_user_lname,s_user_email,s_user_mobile,s_user_country;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__profile__edit__user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
            IV_S_user_profile_edit = findViewById(R.id.IV_S_user_profile_edit);

        IV_S_user_profile_image = findViewById(R.id.IV_S_user_profile_image);
            IV_edit_profile_back=findViewById(R.id.IV_edit_profile_back);
            IV_S_user_profile_image.setVisibility(View.GONE);
            IV_S_user_profile_edit.setVisibility(View.GONE);
        ET_S_firstname = findViewById(R.id.ET_S_firstname);
        ET_S_middlename = findViewById(R.id.ET_S_middlename);
        ET_S_lastname = findViewById(R.id.ET_S_lastname);
        ET_S_email = findViewById(R.id.ET_S_email);
        ET_S_mobile = findViewById(R.id.ET_S_mobile);
        ET_S_address = findViewById(R.id.ET_S_address);
        ET_S_country = findViewById(R.id.ET_S_country);
        B_S_update = findViewById(R.id.B_S_update);
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);

        s_user_token = Shared_user_details.getString("sp_w_apikey", null);
        try {
            Intent intent = getIntent();
            s_user_id = intent.getStringExtra("put_s_userid");
            s_user_address = intent.getStringExtra("put_s_address");
            s_user_image = intent.getStringExtra("put_s_image");
            s_user_fname = intent.getStringExtra("put_s_fname");
            s_user_mname = intent.getStringExtra("put_s_mname");
            s_user_lname = intent.getStringExtra("put_s_lname");
            s_user_email = intent.getStringExtra("put_s_email");
            s_user_mobile = intent.getStringExtra("put_s_mobile");
            s_user_country = intent.getStringExtra("put_s_country");
            IV_edit_profile_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings_Profile_Edit_Vendor.this,Settings_ProfileActivity_Vendor.class));
                }
            });
            ET_S_firstname.setText(s_user_fname);
            ET_S_middlename.setText(s_user_mname);
            ET_S_lastname.setText(s_user_lname);
            ET_S_email.setText(s_user_email);
            ET_S_mobile.setText(s_user_mobile);
            ET_S_address.setText(s_user_address);
            ET_S_country.setText("Kuwait");
            ET_S_country.setFocusable(false);
            ET_S_country.setFocusableInTouchMode(false);
            ET_S_country.setClickable(false);
            Picasso.with(Settings_Profile_Edit_Vendor.this).load(Global_URL.Image_url_load + s_user_image).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(IV_S_user_profile_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
            B_S_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoupload_userdet();
                }
            });
    }
     else
    {
        setContentView(R.layout.content_if_nointernet);
        ImageView but_retry = findViewById(R.id.nointernet_retry);
        but_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_Profile_Edit_Vendor.this, Settings_ProfileActivity_Vendor.class);
                startActivity(intent);
            }
        });
    }
    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public void callmetoupload_userdet()
    {
        if(ET_S_firstname.getText().toString().equals(""))
        {
            new PromptDialog(Settings_Profile_Edit_Vendor.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Enter First Name")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
            if(ET_S_lastname.getText().toString().equals(""))
            {
                new PromptDialog(Settings_Profile_Edit_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Enter Last Name")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
            else
            {
                if(ET_S_email.getText().toString().equals(""))
                {
                    new PromptDialog(Settings_Profile_Edit_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Enter Email")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                else
                {
                    if(ET_S_mobile.getText().toString().equals(""))
                    {
                        new PromptDialog(Settings_Profile_Edit_Vendor.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Mobile Number")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        if(ET_S_address.getText().toString().equals(""))
                        {
                            new PromptDialog(Settings_Profile_Edit_Vendor.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Address")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else {
                            try
                            {
                                s_user_fname=ET_S_firstname.getText().toString();
                                s_user_mname=ET_S_middlename.getText().toString();
                                s_user_lname=ET_S_lastname.getText().toString();
                                s_user_email=ET_S_email.getText().toString().trim();
                                s_user_mobile=ET_S_mobile.getText().toString();
                                s_user_address=ET_S_address.getText().toString();
                                s_user_country=ET_S_country.getText().toString();
                                callmetoupload_userdet_url(s_user_address,s_user_id,s_user_fname,s_user_mname,
                                        s_user_lname,s_user_email,s_user_mobile,s_user_country);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        }
    }
    public void callmetoupload_userdet_url(final String address_u,final String userid_u,
                                           final String fname_u,
                                           final String mname_u,final String lname_u,
                                           final String email_u,final String mobile_u,
                                           final String country_u) {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Address1", address_u);
            jsonBody.put("Id", userid_u);
            jsonBody.put("FirstName", fname_u);
            jsonBody.put("LastName",lname_u );
            jsonBody.put("MiddleName", mname_u);
            jsonBody.put("Email", email_u);
            jsonBody.put("PhoneNumber",mobile_u);
            jsonBody.put("Country",country_u);


            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_insertbasicinfo, new Response.Listener<String>() {

                public void onResponse(String response) {
                    // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                    new PromptDialog(Settings_Profile_Edit_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Profile Edited Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(Settings_Profile_Edit_Vendor.this,Settings_ProfileActivity_Vendor.class));                                }
                            }).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // headers.put("content-Type", "application/json");
                    //   headers.put("X-API-TYPE", "Android");
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
        }catch (JSONException e){

        }
    }

}
