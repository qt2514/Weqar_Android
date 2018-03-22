package com.weqar.weqar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class AddJobs_Vendor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageView IV_vaddjobs_back;
    EditText ET_vaddjobs_title,ET_vaddjobs_desc;
    TextView TV_vaddjobs_openingdate,TV_vaddjobs_closingdate,TV_vaddjobs_jobtype,TV_vaddjobs_jobfield;
    Button But_add;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int SECOND_ACTIVITY_REQUEST_CODE_two = 1;
    String Ssubjectkind,Ssubjectkinds,s_lnw_usertoken,s_lnw_userid;
     String subjectnamelist;
     String subjectnameid;
  String subjectnamelists;
    String subjectnameids;
    SimpleDateFormat simpleDateFormat;
    int one;
    String s_jobtitle,s_jobtype,s_jobfield,s_jobstartingdate,s_closingdate,s_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        IV_vaddjobs_back=findViewById(R.id.iv_vaddjobs_back);
        ET_vaddjobs_title=findViewById(R.id.et_vaddjobs_title);
        ET_vaddjobs_desc=findViewById(R.id.et_vaddjobs_desc);
        TV_vaddjobs_jobtype=findViewById(R.id.tv_vaddjobs_jobtype);
        TV_vaddjobs_jobfield=findViewById(R.id.tv_vaddjobs_jobfield);
        TV_vaddjobs_openingdate=findViewById(R.id.tv_vaddjobs_openingdate);
        TV_vaddjobs_closingdate=findViewById(R.id.tv_vaddjobs_closingdate);
        But_add=findViewById(R.id.but_vaddjobs_add);
        simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_lnw_usertoken= preferences.getString("sp_w_apikey","");
        s_lnw_userid= preferences.getString("sp_w_userid","");


        TV_vaddjobs_jobtype.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddJobs_Vendor.this, MultiSpinner_Vendor_JobType.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        TV_vaddjobs_jobfield.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddJobs_Vendor.this, MultiSpinner_Vendor_JobField.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE_two);
            }
        });
        TV_vaddjobs_openingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one=1;
                showDate(2018, 0, 1, R.style.DatePickerSpinner);
            }
        });
        TV_vaddjobs_closingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one=2;
                showDate(2018, 0, 1, R.style.DatePickerSpinner);
            }
        });
        But_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callmetoupload_addjob();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(this);
                Ssubjectkind=tinydb.getString("jobtype");
                if (Ssubjectkind.equals("job")) {
                    subjectnamelist = tinydb.getString("vendoraddjobs_name");
                    subjectnameid = tinydb.getString("vendoraddjobs_id");
                    StringBuilder builder = new StringBuilder();


                        builder.append("").append(subjectnamelist).append(",");



                    TV_vaddjobs_jobtype.setText(builder);
                }


            }
        }
        else if (requestCode == SECOND_ACTIVITY_REQUEST_CODE_two) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(this);
                Ssubjectkinds=tinydb.getString("jobfield");
                if (Ssubjectkinds.equals("jobF")) {
                    subjectnamelists = tinydb.getString("vendoraddjobsfield_name");
                    subjectnameids = tinydb.getString("vendoraddjobsfield_id");
                    StringBuilder builder = new StringBuilder();


                        builder.append("").append(subjectnamelists).append(",");



                    TV_vaddjobs_jobfield.setText(builder);
                }


            }
        }
        else
        {
            Toast.makeText(this, "Check me", Toast.LENGTH_SHORT).show();
        }
    }
    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(AddJobs_Vendor.this)
                .callback((DatePickerDialog.OnDateSetListener) AddJobs_Vendor.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }


    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        if(one==1)
        {
            TV_vaddjobs_openingdate.setText(date);

        }
        else  if(one==2)
        {
            TV_vaddjobs_closingdate.setText(date);

        }
    }
    public void callmetoupload_addjob()
    {
        if(ET_vaddjobs_title.getText().toString().equals(""))
        {

            new PromptDialog(AddJobs_Vendor.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                    .setAnimationEnable(true)
                    .setTitleText("Please Add Job Title")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
            if(TV_vaddjobs_jobtype.getText().toString().equals(""))
            {

                new PromptDialog(AddJobs_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("Please Select Job Type")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
            else
            {
                if(TV_vaddjobs_jobfield.getText().toString().equals(""))
                {

                    new PromptDialog(AddJobs_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Please Select Job Field")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                else
                {
                    if(TV_vaddjobs_openingdate.getText().toString().equals(""))
                    {

                        new PromptDialog(AddJobs_Vendor.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                .setAnimationEnable(true)
                                .setTitleText("Please Select Opening Date")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        if(TV_vaddjobs_closingdate.getText().toString().equals(""))
                        {

                            new PromptDialog(AddJobs_Vendor.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Select Closing Date")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            if(ET_vaddjobs_desc.getText().toString().equals(""))
                            {

                                new PromptDialog(AddJobs_Vendor.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                        .setAnimationEnable(true)
                                        .setTitleText("Please Enter Job Description")
                                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                            else
                            {
                                s_jobtitle=ET_vaddjobs_title.getText().toString();
                                s_jobtype=TV_vaddjobs_jobtype.getText().toString();
                                s_jobfield=TV_vaddjobs_jobfield.toString();
                                s_jobstartingdate=TV_vaddjobs_openingdate.getText().toString();
                                s_closingdate=TV_vaddjobs_closingdate.getText().toString();
                                s_desc=ET_vaddjobs_desc.getText().toString();
                               callmetoupload_addjob_url(s_lnw_userid,s_jobtitle,subjectnameid,subjectnameids,
                                       s_desc,s_jobstartingdate,s_closingdate);
                            }
                        }
                    }
                }
            }
        }
    }
 public void callmetoupload_addjob_url(String id, String title, String jobtype, String jobfield, String description,
                                       String startingdate, String closingdate)
 {

     try {


         RequestQueue requestQueue = Volley.newRequestQueue(this);
         JSONArray jsonArray = new JSONArray();
         JSONObject jsonBody = new JSONObject();
         jsonBody.put("UserId", id);
         jsonBody.put("Name", title);
         jsonBody.put("JobTypeId", jobtype);
         jsonBody.put("JobFieldId", jobfield);
         jsonBody.put("Description", description);
         jsonBody.put("ClosingDate", startingdate);
         jsonBody.put("OpeningDate", closingdate);



         final String requestBody = jsonBody.toString();

         StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_addjobs, new Response.Listener<String>() {

             public void onResponse(String response) {
                 // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                 Log.i("basic_details_response",response);


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

