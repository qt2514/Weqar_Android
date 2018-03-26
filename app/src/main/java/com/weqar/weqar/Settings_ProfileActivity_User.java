package com.weqar.weqar;

import android.content.SharedPreferences;
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
import com.weqar.weqar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Settings_ProfileActivity_User extends AppCompatActivity {
    ImageView IV_profile_account_back;
    CircleImageView CV_user_profileimage;
    TextView TV_user_profile_fname,TV_user_profile_name,TV_user_profile_email,
            TV_user_profile_mobile,TV_user_profile_address,TV_user_profile_country;
    String s_user_id,s_user_token,s_user_p_fname,s_user_p_name,s_user_p_email,
            s_user_p_mobile,s_user_p_address,s_user_p_country,s_user_p_image;
        SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        IV_profile_account_back=findViewById(R.id.account_setting_back);
        CV_user_profileimage=findViewById(R.id.CV_user_profileimage);
        TV_user_profile_fname=findViewById(R.id.TV_user_profilefname);
        TV_user_profile_name=findViewById(R.id.TV_user_profilename);
        TV_user_profile_email=findViewById(R.id.TV_user_profileemail);
        TV_user_profile_mobile=findViewById(R.id.TV_user_profilemobile);
        TV_user_profile_address=findViewById(R.id.TV_user_profileaddress);
        TV_user_profile_country=findViewById(R.id.TV_user_profilecountry);
        IV_profile_account_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Shared_user_details=getSharedPreferences("user_detail_mode",0);

        s_user_id= Shared_user_details.getString("sp_w_userid", null);
        s_user_token= Shared_user_details.getString("sp_w_apikey", null);
        getmydet(s_user_id);

    }
    public void getmydet(String susername)
    {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", susername);



            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.getDetails, new Response.Listener<String>() {

                public void onResponse(String response) {
                    try {

                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("success")||status.matches("success"))
                        {
                            JSONObject verification = jObj.getJSONObject("Response");
                            s_user_p_fname=verification.getString("FirstName");
                            s_user_p_name=verification.getString("UserName");
                            s_user_p_email=verification.getString("Email");
                            s_user_p_mobile=verification.getString("PhoneNumber");
                            s_user_p_address=verification.getString("Address1");
                            s_user_p_country=verification.getString("Country");
                            JSONObject verifications = verification.getJSONObject("vendorProfessional");
                            s_user_p_image=verifications.getString("Logo");
                            Picasso.with(Settings_ProfileActivity_User.this).load(Global_URL.Image_url_load+s_user_p_image).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CV_user_profileimage);
                            TV_user_profile_fname.setText(s_user_p_fname);
                            TV_user_profile_name.setText(s_user_p_name);
                            TV_user_profile_email.setText(s_user_p_email);
                            TV_user_profile_mobile.setText(s_user_p_mobile);
                            TV_user_profile_address.setText(s_user_p_address);
                            TV_user_profile_country.setText(s_user_p_country);
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
                    headers.put("x-api-key",s_user_token);
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
