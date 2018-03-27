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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.BatchUpdateException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class AddEvents_Vendor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView TV_eventstart, TV_evenetend;
    EditText ET_contactname, ET_title, ET_amount, ET_descrition;
    ImageView IV_imageupload;
    Button But_upolad;
    String s_eventstart, s_eventend, s_contactname, s_title, s_amount, s_descrition, s_image;
    int one;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String s_lnw_userid, s_lnw_usertoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TV_eventstart = findViewById(R.id.et_addevent_startdate);
        TV_evenetend = findViewById(R.id.et_addevent_enddate);
        ET_contactname = findViewById(R.id.et_addevent_contactperson);
        ET_title = findViewById(R.id.et_addevent_title);
        ET_amount = findViewById(R.id.et_addevent_amount);
        ET_descrition = findViewById(R.id.et_addevent_desc);
        IV_imageupload = findViewById(R.id.iv_addevent_image);
        But_upolad = findViewById(R.id.but_addevent_add);
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);


        s_lnw_userid = Shared_user_details.getString("sp_w_userid", null);
        s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);
        TV_eventstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one = 1;
                showDate(2018, 0, 1, R.style.DatePickerSpinner);
            }
        });
        TV_evenetend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one = 2;
                showDate(2018, 0, 1, R.style.DatePickerSpinner);
            }
        });
        IV_imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1006);
            }
        });
        But_upolad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callmetoupload_addevnts();
            }
        });
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback((DatePickerDialog.OnDateSetListener) this)
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
            TV_eventstart.setText(date);

        } else if (one == 2) {
            TV_evenetend.setText(date);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1006 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                IV_imageupload.setImageBitmap(bitmap);
                upload_vendor_complete_image_s(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void upload_vendor_complete_image_s(final Bitmap bitmap) {

        Bitmap immagex = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("extension", "JPG");
            jsonBody.put("content", imageEncoded);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_uploadprofessionalimage, new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        s_image = jObj.getString("Response");
                        Log.i("user_vendor_complete_image_response", response);


                    } catch (JSONException e) {
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
                    headers.put("x-api-key", s_lnw_usertoken);
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

    public void callmetoupload_addevnts() {

        if (TV_eventstart.getText().toString().equals("")) {
            new PromptDialog(AddEvents_Vendor.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Choose Start Date")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            if (TV_evenetend.equals("")) {
                new PromptDialog(AddEvents_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Choose End Date")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            } else {
                if (ET_contactname.getText().toString().equals("")) {
                    new PromptDialog(AddEvents_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Enter Contact Name")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    if (ET_title.getText().toString().equals("")) {
                        new PromptDialog(AddEvents_Vendor.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Event title")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else {
                        if(ET_amount.getText().toString().equals("")) {
                            new PromptDialog(AddEvents_Vendor.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Amount")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }


                        else{
                        s_eventstart = TV_eventstart.getText().toString();
                        s_eventend = TV_evenetend.getText().toString();
                        s_contactname = ET_contactname.getText().toString();
                        s_title = ET_title.getText().toString();
                        s_amount= ET_amount.getText().toString();
                        s_descrition= ET_descrition.getText().toString();
                        callmetouploadvendorcomplete_url(s_lnw_userid, s_eventstart, s_eventend
                                ,s_contactname, s_title, s_descrition, s_image,s_amount);
                        Intent intent = new Intent(AddEvents_Vendor.this, HomeScreen_vendor.class);
                        startActivity(intent);
                    }
                    }

                }
            }
        }
    }

    public void callmetouploadvendorcomplete_url(String s_lnw_userid, String start, String end,
                                                 String name, String title,
                                                 String desc,String image, String amount)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserId", s_lnw_userid);
            jsonBody.put("EventStart", start);
            jsonBody.put("EventEnd", end);
            jsonBody.put("Name", name);
            jsonBody.put("Title", title);
            jsonBody.put("Description", desc);
            jsonBody.put("Image", image);
            jsonBody.put("Amount", amount);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_add_event, new Response.Listener<String>() {
                public void onResponse(String response) {
                    Log.i("vendor_complete_response",response);

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
                    headers.put("X-API-TYPE", "Android");
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
        }catch (JSONException e){

        }
    }
}
