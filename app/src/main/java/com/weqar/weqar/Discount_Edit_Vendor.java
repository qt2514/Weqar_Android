package com.weqar.weqar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class Discount_Edit_Vendor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    SearchableSpinner SP_plan_discedit,SP_offertype_discedit;
    TextView TV_startdate_discedit,TV_enddate_discedit;
    TextInputLayout TIV_user_disc_edit;
    EditText ET_title_discedit,ET_desc_discedit,ET_perc_discedit;
    ImageView IV_image_discedit;
    String s_discid,s_planid,s_plantype,s_offdertype,s_sdate,s_edate,s_title,s_desc,s_image,s_images,s_percentage,s_final_offer;
    private JSONArray result;
    ArrayList<String> vendor_plan = new ArrayList<String>();
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String s_lnw_userid,s_lnw_usertoken,check_discounttype_vendor_discount;
    String compl_vendor_offertype[] = {"Discount","Offer"};
    Button But_update;

    int one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount__edit__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SP_plan_discedit=findViewById(R.id.sp_plan_discedit);
                SP_offertype_discedit=findViewById(R.id.sp_offertype_discedit);
                TV_startdate_discedit=findViewById(R.id.tv_startdate_discedit);
                TV_enddate_discedit=findViewById(R.id.tv_enddate_discedit);
                TIV_user_disc_edit=findViewById(R.id.tiv_user_disc_edit);
                ET_title_discedit=findViewById(R.id.et_title_discedit);
                ET_desc_discedit=findViewById(R.id.et_desc_discedit);
                IV_image_discedit=findViewById(R.id.IV_image_discedit);
                 ET_perc_discedit=findViewById(R.id.et_vcomplete_percentage_s);
                 But_update=findViewById(R.id.Disc_edit_butupdate);
        Intent intent=getIntent();

        s_discid=intent.getStringExtra("put_discountid_fordisc_edit");
        s_offdertype=intent.getStringExtra("put_discounttype_fordisc_edit");
        s_title=intent.getStringExtra("put_discounttitle_fordisc_edit");
        s_desc=intent.getStringExtra("put_discountdesc_fordisc_edit");
        s_image=intent.getStringExtra("put_discountimage_fordisc_edit");
        s_percentage=intent.getStringExtra("put_discountper_fordisc_edit");
        s_sdate=intent.getStringExtra("put_discountsdate_fordisc_edit");
        s_edate=intent.getStringExtra("put_discountedate_fordisc_edit");
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        s_lnw_userid= Shared_user_details.getString("sp_w_userid", null);
        s_lnw_usertoken= Shared_user_details.getString("sp_w_apikey", null);
        getVendorplan();

        if(s_offdertype.equals("1"))
        {
            SP_offertype_discedit.setTitle("Discount");

        }
        else
        {
            SP_offertype_discedit.setTitle("Offer");
        }
        String sdatetrim=s_sdate.substring(0,10);
        String edatetrim=s_edate.substring(0,10);


        TV_startdate_discedit.setText(sdatetrim);
        TV_enddate_discedit.setText(edatetrim);
        ET_title_discedit.setText(s_title);
        ET_desc_discedit.setText(s_desc);
        ET_perc_discedit.setText(s_percentage);
       ET_desc_discedit.setText(s_desc);
        Picasso.with(this).load(Global_URL.Image_url_load+s_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(IV_image_discedit);

        SP_plan_discedit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                s_planid = parent.getItemAtPosition(position).toString();
                getvendor_plannameid(position);

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TV_startdate_discedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one=1;
                showDate(2018, 0, 1, R.style.DatePickerSpinner);
            }
        });
        TV_enddate_discedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one=2;
                showDate(2018, 0, 1, R.style.DatePickerSpinner);
            }
        });
        IV_image_discedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1006);
            }
        });
        ArrayAdapter<String> spinnerArrayAdapters = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, compl_vendor_offertype);
        spinnerArrayAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        SP_offertype_discedit.setAdapter(spinnerArrayAdapters);
        SP_offertype_discedit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                s_final_offer= parent.getItemAtPosition(position).toString();
                if(s_final_offer.equals("Discount")||s_final_offer.matches("Discount"))
                {
                    check_discounttype_vendor_discount="1";
                    ET_perc_discedit.setVisibility(View.VISIBLE);
                    TIV_user_disc_edit.setVisibility(View.VISIBLE);
                } else if(s_final_offer.equals("Offer")||s_final_offer.matches("Offer"))
                {
                    check_discounttype_vendor_discount="2";
                    ET_perc_discedit.setVisibility(View.INVISIBLE);
                    TIV_user_disc_edit.setVisibility(View.INVISIBLE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
But_update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        callmetoupload_seconddiscount();
    }
});
    }

    private void getvendor_plannameid(int position){

        try {
            JSONObject json = result.getJSONObject(position);
            s_plantype = json.getString("Id");
            Toast.makeText(Discount_Edit_Vendor.this,s_plantype , Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getVendorplan() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_complete_chooseplan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("Response");
                    getStudents(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-api-type", "Android");
                //headers.put("content-Type", "application/json");
                headers.put("x-api-key",s_lnw_usertoken);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void getStudents(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                vendor_plan.add(json.getString("Name"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SP_plan_discedit.setAdapter(new ArrayAdapter<String>(Discount_Edit_Vendor.this, android.R.layout.simple_dropdown_item_1line, vendor_plan));
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback(this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }
    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        String date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

        if (one == 1) {
            TV_startdate_discedit.setText(date);

        } else if (one == 2) {
            TV_enddate_discedit.setText(date);

        }
    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1006 && resultCode == RESULT_OK && data != null) {

                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    IV_image_discedit.setImageBitmap(bitmap);
                    upload_vendor_complete_image_s(bitmap);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    private void upload_vendor_complete_image_s(final Bitmap bitmap)
    {

        Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("extension", "JPG");
            jsonBody.put("content", imageEncoded);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_uploadprofessionalimage, new Response.Listener<String>() {
                public void onResponse(String response) {
                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_images=jObj.getString("Response");
                        Log.i("user_vendor_complete_image_response",response);


                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    public void callmetoupload_seconddiscount()
    {
        if(TV_startdate_discedit.getText().toString().equals(""))
        {
            new PromptDialog(Discount_Edit_Vendor.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Select Start Date")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else {
            if (TV_enddate_discedit.getText().toString().equals(""))
            {
                new PromptDialog(Discount_Edit_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Select End Date")
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


                    if (check_discounttype_vendor_discount.equals(""))
                    {
                        new PromptDialog(Discount_Edit_Vendor.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Select Offer Type")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        if(ET_title_discedit.getText().toString().equals(""))
                        {
                            new PromptDialog(Discount_Edit_Vendor.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Title")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            if(ET_desc_discedit.getText().toString().equals(""))
                            {
                                new PromptDialog(Discount_Edit_Vendor.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                        .setAnimationEnable(true)
                                        .setTitleText("Please Enter Description")
                                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                            else
                            {
                                if(s_images.equals(""))
                                {
                                    new PromptDialog(Discount_Edit_Vendor.this)
                                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                            .setAnimationEnable(true)
                                            .setTitleText("Please Select Image")
                                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                                @Override
                                                public void onClick(PromptDialog dialog) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                }
                                else
                                {
                                    String sdates=TV_startdate_discedit.getText().toString();
                                    String edates=TV_enddate_discedit.getText().toString();
                                    String spercs=ET_perc_discedit.getText().toString();
                                    String stitles=ET_title_discedit.getText().toString();
                                    String sdescs=ET_desc_discedit.getText().toString();


                                    callmetoupload_seconddiscount_url(s_discid,sdescs,sdates,edates,
                                            s_images,spercs,check_discounttype_vendor_discount,s_plantype,stitles);



                                }


                            }
                        }
                    }
                }catch (Exception e){}
            }
        }

    }
    public void callmetoupload_seconddiscount_url(final String id,final String desc,
                                                  final String sdate,final String senddate,
                                                  final String image,final String perc,
                                                  final String type,final String plan,final String title)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", id);
            jsonBody.put("Description", desc);
            jsonBody.put("StartDate", sdate);
            jsonBody.put("EndDate", senddate);
            jsonBody.put("Image", image);
            jsonBody.put("Percentage", perc);
            jsonBody.put("DiscountType", type);
            jsonBody.put("DiscountPlan", plan);
            jsonBody.put("Title", title);





            final String requestBody = jsonBody.toString();
            Log.i("checkandro",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_discount_update, new Response.Listener<String>() {

                public void onResponse(String response) {
                    // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));

                    Log.i("vendor_professional_response",response);

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
                    headers.put("x-api-type", "Android");
                    //headers.put("content-Type", "application/json");
                    headers.put("x-api-key",s_lnw_usertoken);
                    Log.i("checkandroheader",headers.toString());

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
