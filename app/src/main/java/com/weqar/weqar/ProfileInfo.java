package com.weqar.weqar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.ImageUtil;
import com.weqar.weqar.JavaClasses.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.refactor.lib.colordialog.PromptDialog;


public class ProfileInfo extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{
EditText ET_fname,ET_mname,ET_lname,ET_mobile,ET_address,ET_city,ET_zipcode,ET_country,
    ET_Prof_cidno,ET_Prof_memno,ET_Prof_valid;
SearchableSpinner SP_mobilepin;
ScrollView scrollView_personal,scrollView_professional,scrollview_vendor_professional,scrollView_complete;
Button B_saveandcontinue_personal,B_professional_next,B_vendorprofessional_next;
ImageView IV_personal,IV_professional,IV_complete,IV_prof_uploadfile;
View view1,view2,view3,view4;
Button monthly_first,monthly_scond,monthly_third,but_complete;
Toolbar toolbar;
String s_fname,s_mname,s_lname,s_mobilepin,s_mobile,s_address,s_city,s_zipcode,s_country,
    s_prof_cidno,s_prof_memno,s_prof_valid,
    s_sweetmessage,s_prof_sweetmessage;
String s_lnw_userid,s_lnw_useremail,s_lnw_usertype,s_res_userprofimg,s_lnw_usertoken;
   com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Basic Info");
        ET_fname=findViewById(R.id.et_fname);
        ET_mname=findViewById(R.id.et_mname);
        ET_lname=findViewById(R.id.et_lname);
        ET_mobile=findViewById(R.id.et_mobile);
        ET_address=findViewById(R.id.et_address);
        ET_city=findViewById(R.id.et_city);
        ET_zipcode=findViewById(R.id.et_zipcode);
        SP_mobilepin=findViewById(R.id.basic_spiner_countrycode);
        ET_country=findViewById(R.id.et_selectcountry);

        ET_Prof_cidno=findViewById(R.id.et_prof_cidno);
        ET_Prof_memno=findViewById(R.id.et_prof_membernum);
        ET_Prof_valid=findViewById(R.id.et_prof_valid);

        scrollView_personal=findViewById(R.id.scrollview_personal);
        scrollView_professional=findViewById(R.id.scrollview_professional);
        scrollview_vendor_professional=findViewById(R.id.scrollview_vendorprofessional);
        scrollView_complete=findViewById(R.id.scrollview_complete);
        B_saveandcontinue_personal=findViewById(R.id.saveandcontinue_personal);
        B_professional_next=findViewById(R.id.professional_but_next);
        B_vendorprofessional_next=findViewById(R.id.vendorprofessional_but_next);
        IV_personal=findViewById(R.id.IV_personaL);
        IV_professional=findViewById(R.id.IV_professional);
        IV_complete=findViewById(R.id.IV_complete);
        IV_prof_uploadfile=findViewById(R.id.IV_prof_uploadfile);
        view1=findViewById(R.id.profile_view1);
        view2=findViewById(R.id.profile_view2);
        view3=findViewById(R.id.profile_view3);
        view4=findViewById(R.id.profile_view4);
        monthly_first=findViewById(R.id.monthly_first);
        monthly_scond=findViewById(R.id.monthly_second);
        monthly_third=findViewById(R.id.monthly_third);
        but_complete=findViewById(R.id.complete_but);

        ET_country.setText("Kuwait");
        ET_country.setFocusable(false);
        ET_country.setFocusableInTouchMode(false);
        ET_country.setClickable(false);
        SP_mobilepin.setPrompt("+965");

        Intent getuserdet=getIntent();
        s_lnw_userid=getuserdet.getStringExtra("w_userid");
        s_lnw_useremail=getuserdet.getStringExtra("w_useremail");
        s_lnw_usertype=getuserdet.getStringExtra("w_usertype");
        s_lnw_usertoken=getuserdet.getStringExtra("APIKey");

        Calendar now = Calendar.getInstance();
        dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                ProfileInfo.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        ET_fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus)
                {
                   // et_mail.setBackgroundResource( R.drawable.sel_bg_edit);
                    ET_fname.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_fname.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_mname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_mname.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_mname.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_lname.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_lname.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_mobile.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_mobile.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_address.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_address.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_city.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_city.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_zipcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_zipcode.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_zipcode.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_Prof_memno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_Prof_memno.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_Prof_memno.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_Prof_memno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_Prof_memno.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_Prof_memno.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_Prof_valid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_Prof_valid.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_Prof_valid.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        IV_prof_uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });


        B_saveandcontinue_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView_personal.setVisibility(View.INVISIBLE);

                if(s_lnw_usertype.equals("user")||s_lnw_usertype.matches("user"))
                {
                    scrollView_professional.setVisibility(View.VISIBLE);

                }
                else if(s_lnw_usertype.equals("vendor")||s_lnw_usertype.matches("vendor"))
                {
                    scrollview_vendor_professional.setVisibility(View.VISIBLE);

                }
                scrollView_complete.setVisibility(View.INVISIBLE);
                view2.setBackgroundResource(R.color.colorAccent);
                toolbar.setTitle("Verification");
                IV_personal.setImageResource(R.drawable.profile_basic_three);
                IV_professional.setImageResource(R.drawable.profile_professional_two);
                IV_complete.setImageResource(R.drawable.profile_complete_one);
             callmetouploadbasic();

            }
        });
        B_professional_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView_personal.setVisibility(View.INVISIBLE);
                scrollView_professional.setVisibility(View.INVISIBLE);
                scrollview_vendor_professional.setVisibility(View.INVISIBLE);
                scrollView_complete.setVisibility(View.VISIBLE);
                view3.setBackgroundResource(R.color.colorAccent);
                toolbar.setTitle("Subscription");
                IV_personal.setImageResource(R.drawable.profile_basic_three);
                IV_professional.setImageResource(R.drawable.profile_professional_three);
                IV_complete.setImageResource(R.drawable.profile_complete_two);
                but_complete.setVisibility(View.VISIBLE);
              //callmetouploadprofessional();

            }
        });
        B_vendorprofessional_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView_personal.setVisibility(View.INVISIBLE);
                scrollView_professional.setVisibility(View.INVISIBLE);
                scrollview_vendor_professional.setVisibility(View.INVISIBLE);
                scrollView_complete.setVisibility(View.VISIBLE);
                view3.setBackgroundResource(R.color.colorAccent);
                toolbar.setTitle("Subscription");
                IV_personal.setImageResource(R.drawable.profile_basic_three);
                IV_professional.setImageResource(R.drawable.profile_professional_three);
                IV_complete.setImageResource(R.drawable.profile_complete_two);
                but_complete.setVisibility(View.VISIBLE);


            }
        });
        but_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileInfo.this,HomeScreen.class));
            }
        });
        monthly_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PromptDialog(ProfileInfo.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("Confirm Transaction!")
                        .setContentText("You are Subscribed for monthly \n \u20B9 100 ")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                                IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_three);
                                IV_complete.setImageResource(R.drawable.profile_complete_three);
                                view4.setBackgroundResource(R.color.colorAccent);

                            }
                        }).show();
            }
        });
        monthly_scond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PromptDialog(ProfileInfo.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("Confirm Transaction!")
                        .setContentText("You are Subscribed for monthly \n \u20B9  200 ")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                                IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_three);
                                IV_complete.setImageResource(R.drawable.profile_complete_three);
                                view4.setBackgroundResource(R.color.colorAccent);

                            }
                        }).show();
            }
        });
        monthly_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PromptDialog(ProfileInfo.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("Confirm Transaction!")
                        .setContentText("You are Subscribed for monthly \n \u20B9  500 ")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                                IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_three);
                                IV_complete.setImageResource(R.drawable.profile_complete_three);
                                view4.setBackgroundResource(R.color.colorAccent);

                            }
                        }).show();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                List<Bitmap> listBitmap = new ArrayList<>();
//                listBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
                IV_prof_uploadfile.setImageBitmap(bitmap);
                uploadBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadBitmap(final Bitmap bitmap) {


        final String base64String = ImageUtil.convert(bitmap);
        String strOut = base64String.substring(0, 8);
        try {

           RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray= new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("extension", "JPG");
            jsonBody.put("content", strOut);


            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_uploadprofessionalimage, new Response.Listener<String>() {

                public void onResponse(String response) {

                    try {


                        JSONObject jObj = new JSONObject(response);


                        s_res_userprofimg=jObj.getString("Response");

                            Toast.makeText(ProfileInfo.this, s_res_userprofimg, Toast.LENGTH_SHORT).show();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
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
                   //  headers.put("content-Type", "application/json");
                       headers.put("x-api-key",s_lnw_usertoken);
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
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void callmetouploadbasic()
    {
        if(ET_fname.getText().toString().equals(""))
        {
            s_sweetmessage="Please Enter First Name";
        }else
        {
            if(ET_mname.getText().toString().equals(""))
            {
                s_sweetmessage="Please Enter Middle Name";
            }
            else
            {
                if(ET_lname.getText().toString().equals(""))
                {
                    s_sweetmessage="Please Enter Last Name";
                }
                else
                {
                    if(ET_mobile.getText().toString().equals(""))
                    {
                        s_sweetmessage="Please Enter Mobile Number";
                    }
                    else
                    {
                        if(ET_address.getText().toString().equals(""))
                        {
                            s_sweetmessage="Please Select Address";
                        }
                        else
                        {
                            if(ET_city.getText().toString().equals(""))
                            {
                                s_sweetmessage="Please Enter City Name";
                            }
                            else
                            {
                                if(ET_zipcode.getText().toString().equals(""))
                                {
                                    s_sweetmessage="Please Enter ZipCode";
                                }
                                else
                                {
                                    s_fname=ET_fname.getText().toString();
                                    s_mname=ET_mname.getText().toString();
                                    s_lname=ET_lname.getText().toString();
                                    s_mobilepin="+965";
                                    s_mobile=ET_mobile.getText().toString();
                                    s_address=ET_address.getText().toString();
                                    s_city=ET_city.getText().toString();
                                    s_zipcode=ET_zipcode.getText().toString();
                                    s_country="Kuwait";
                                    s_sweetmessage="Thanku";
                                    callmetouploadbasicurl(s_lnw_userid,s_lnw_useremail,s_fname,s_mname,s_lname,s_mobile,s_address,s_city,s_country);
                                }
                            }
                        }
                    }
                }
            }
        }
        new PromptDialog(ProfileInfo.this)
                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                .setAnimationEnable(true)
                .setTitleText(s_sweetmessage)
                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();

    }
    public void callmetouploadbasicurl(String user_uid,String user_email,String sfname,
                                       String smname,String slname,String smobile,String saddress,
                                        String scity,String scountry) {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Address1", saddress);
            jsonBody.put("Address2", scity);
            jsonBody.put("Id", user_uid);
            jsonBody.put("FirstName", sfname);
            jsonBody.put("LastName", slname);
            jsonBody.put("MiddleName", smname);
            jsonBody.put("Email", user_email);
            jsonBody.put("PhoneNumber",smobile);
            jsonBody.put("Country",scountry);


            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_insertbasicinfo, new Response.Listener<String>() {

                public void onResponse(String response) {
                   // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
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
    public void callmetouploadprofessional()
    {
        if(ET_Prof_cidno.getText().toString().equals(""))
        {
            s_prof_sweetmessage="Please Enter C.I.D.Number";
        }
        else
        {
            if(ET_Prof_memno.getText().toString().equals(""))
            {
                s_prof_sweetmessage="Please Enter Member Number";
            }
            else
            {
                if(ET_Prof_valid.getText().toString().equals(""))
                {
                    s_prof_sweetmessage="Please Enter Valid Date";
                }
                else
                {
                    s_prof_cidno=ET_Prof_cidno.getText().toString();
                    s_prof_memno=ET_Prof_memno.getText().toString();
                    s_prof_valid=ET_Prof_valid.getText().toString();
                    callmetouploadprofessionalurl(s_prof_cidno,s_prof_memno,s_lnw_userid,s_prof_valid,s_res_userprofimg);
                }
            }
        } new PromptDialog(ProfileInfo.this)
            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
            .setAnimationEnable(true)
            .setTitleText(s_prof_sweetmessage)
            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();
                }
            }).show();

    }

    public void callmetouploadprofessionalurl(String sprofcidno,String sprofmemno,String sprofuserid,String sprofvalid,String sprofusertoken)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("CIDNO", sprofcidno);
            jsonBody.put("MemberNo", sprofmemno);
            jsonBody.put("UserId", sprofuserid);
            jsonBody.put("ValidTo", sprofvalid);
            jsonBody.put("Image",sprofusertoken);



            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_insertprofessinalinfo, new Response.Listener<String>() {

                public void onResponse(String response) {
                   // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
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

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = (monthOfYear+1)+"/"+dayOfMonth+"/"+year;
        ET_Prof_valid.setText(date);
    }
}
