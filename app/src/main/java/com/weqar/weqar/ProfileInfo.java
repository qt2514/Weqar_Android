package com.weqar.weqar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.android.volley.AuthFailureError;
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
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter;

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
import java.util.UUID;


import cn.refactor.lib.colordialog.PromptDialog;


public class ProfileInfo extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{
    EditText ET_fname,ET_mname,ET_lname,ET_emailid,ET_mobile,ET_address,ET_country,
    ET_Prof_cidno,ET_Prof_memno,ET_vprof_businescontect,ET_vprof_businesemail,ET_vprof_websitel;
    TextView ET_vprof_category,ET_Prof_valid;
    SearchableSpinner SP_mobilepin,SP_vendor_com_planchoose;
    ScrollView scrollView_personal,scrollView_professional,scrollview_vendor_professional,scrollView_complete,scrollView_vendor_complete;
    Button B_saveandcontinue_personal,B_professional_next,B_vendorprofessional_next;
    ImageView IV_basic_image,IV_personal,IV_professional,IV_complete,IV_prof_uploadfile,IV_vendor_professional_companylogo;
    View view1,view2,view3,view4;
    Button but_complete;
    Toolbar toolbar;
    String s_fname,s_mname,s_lname,s_mobilepin,s_mobile,s_address,s_emailid,s_country,
    s_prof_cidno,s_prof_memno,s_prof_valid,s_vprof_category,s_vprof_buscontact,s_vprof_busemail,s_vprof_websitelink,
    s_prof_sweetmessage,svendor_busimail;
    String s_lnw_userid,s_lnw_useremail,s_lnw_usertype,s_res_userprofimg,s_lnw_usertoken,s_lnw_getcompany,s_basic_image;
    Boolean s_ln_tab1,s_ln_tab2,s_ln_tab3;
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
    List<String> L_user_plantype ;
    List<String> L_user_planamount ;
    List<String> L_user_desc ;
    RecyclerView Rec_usersubs;

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    String s_uplan_plantype,s_uplan_amount,s_uplan_desc,selec;

    final ArrayList<MultiSelectModel> AScategory_vendor= new ArrayList<>();
    ArrayList sel_vendorcateg=new ArrayList();
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    Context context;
    List<String> subjectnamelist;
    List<String> subjectnameid;
    String Ssubjectkind;
    private JSONArray result;
    ArrayList<String> vendor_plan = new ArrayList<String>();
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
        ET_emailid=findViewById(R.id.et_emailid);

        ET_mobile=findViewById(R.id.et_mobile);
        ET_address=findViewById(R.id.et_address);

        SP_mobilepin=findViewById(R.id.basic_spiner_countrycode);
        ET_country=findViewById(R.id.et_selectcountry);

        ET_Prof_cidno=findViewById(R.id.et_prof_cidno);
        ET_Prof_memno=findViewById(R.id.et_prof_membernum);
        ET_Prof_valid=findViewById(R.id.et_prof_valid);
        ET_vprof_category=findViewById(R.id.vendor_professional_category);
        ET_vprof_businescontect=findViewById(R.id.vendor_professional_buscontact);
        ET_vprof_businesemail=findViewById(R.id.vendor_professional_busemail);
        ET_vprof_websitel=findViewById(R.id.vendor_professional_websitelink);

        scrollView_personal=findViewById(R.id.scrollview_personal);
        scrollView_professional=findViewById(R.id.scrollview_professional);
        scrollview_vendor_professional=findViewById(R.id.scrollview_vendorprofessional);
        scrollView_complete=findViewById(R.id.scrollview_complete);
        scrollView_vendor_complete=findViewById(R.id.scrollview_vendor_complete);
        B_saveandcontinue_personal=findViewById(R.id.saveandcontinue_personal);
        B_professional_next=findViewById(R.id.professional_but_next);
        B_vendorprofessional_next=findViewById(R.id.vendorprofessional_but_next);

        IV_basic_image=findViewById(R.id.profile_edit);
        IV_personal=findViewById(R.id.IV_personaL);
        IV_professional=findViewById(R.id.IV_professional);
        IV_complete=findViewById(R.id.IV_complete);
        IV_prof_uploadfile=findViewById(R.id.IV_prof_uploadfile);
        IV_vendor_professional_companylogo=findViewById(R.id.vendor_professional_companylogo);
        SP_vendor_com_planchoose=findViewById(R.id.vendor_complete_plan);
        view1=findViewById(R.id.profile_view1);
        view2=findViewById(R.id.profile_view2);
        view3=findViewById(R.id.profile_view3);
        view4=findViewById(R.id.profile_view4);
        but_complete=findViewById(R.id.complete_but);
        Rec_usersubs = findViewById(R.id.recyclerview1);
        L_user_plantype= new ArrayList<String>();
        L_user_planamount= new ArrayList<String>();
        L_user_desc= new ArrayList<String>();

        ET_country.setText("Kuwait");
        ET_country.setFocusable(false);
        ET_country.setFocusableInTouchMode(false);
        ET_country.setClickable(false);
        SP_mobilepin.setPrompt("+965");
        context = ProfileInfo.this;
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        Rec_usersubs.setLayoutManager(RecyclerViewLayoutManager);

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(L_user_plantype,L_user_planamount,L_user_desc,getApplicationContext());

        HorizontalLayout = new LinearLayoutManager(ProfileInfo.this, LinearLayoutManager.HORIZONTAL, false);
        Rec_usersubs.setLayoutManager(HorizontalLayout);
        Rec_usersubs.setHorizontalScrollBarEnabled(false);
        vendor_plan = new ArrayList<String>();
        Intent getuserdet=getIntent();
        s_lnw_userid=getuserdet.getStringExtra("w_userid");
        s_lnw_useremail=getuserdet.getStringExtra("w_useremail");
        s_lnw_usertype=getuserdet.getStringExtra("w_usertype");
        s_lnw_usertoken=getuserdet.getStringExtra("APIKey");
        s_ln_tab1= getuserdet.getBooleanExtra("login_tab1",false);
        s_ln_tab2= getuserdet.getBooleanExtra("login_tab2",false);
        s_ln_tab3= getuserdet.getBooleanExtra("login_tab3",false);

        if(s_ln_tab1)
        {

            if(s_ln_tab2)
            {

                if(s_ln_tab3)
                {
                    startActivity(new Intent(ProfileInfo.this,HomeScreen.class));
                }
                else
                {


                    if(s_lnw_usertype.equals("user")||s_lnw_usertype.matches("user")) {
                        toolbar.setTitle("Subscription");
                        getUserCompletesubscription();
                        scrollView_personal.setVisibility(View.INVISIBLE);
                        scrollView_professional.setVisibility(View.INVISIBLE);
                        but_complete.setVisibility(View.VISIBLE);
                        scrollView_complete.setVisibility(View.VISIBLE);

                        IV_personal.setImageResource(R.drawable.profile_basic_three);
                        IV_professional.setImageResource(R.drawable.profile_professional_three);
                        IV_complete.setImageResource(R.drawable.profile_complete_two);
                        view1.setBackgroundResource(R.color.colorAccent);
                        view2.setBackgroundResource(R.color.colorAccent);
                        view3.setBackgroundResource(R.color.colorAccent);

                    }
                    else if(s_lnw_usertype.equals("vendor")||s_lnw_usertype.matches("vendor")) {
                        toolbar.setTitle("Add Discount");    getUserCompletesubscription();
                        scrollView_personal.setVisibility(View.INVISIBLE);
                        scrollView_professional.setVisibility(View.INVISIBLE);
                        but_complete.setVisibility(View.INVISIBLE);
                        scrollView_complete.setVisibility(View.INVISIBLE);

                      scrollView_vendor_complete.setVisibility(View.VISIBLE);
                        IV_personal.setImageResource(R.drawable.profile_basic_three);
                        IV_professional.setImageResource(R.drawable.profile_professional_three);
                        IV_complete.setImageResource(R.drawable.profile_complete_two);
                        view1.setBackgroundResource(R.color.colorAccent);
                        view2.setBackgroundResource(R.color.colorAccent);
                        view3.setBackgroundResource(R.color.colorAccent);
                    }
                }

            }
            else
            {
                if(s_lnw_usertype.equals("user")||s_lnw_usertype.matches("user"))
                {
                    toolbar.setTitle("Professional");

                    scrollView_personal.setVisibility(View.INVISIBLE);
                    scrollView_professional.setVisibility(View.VISIBLE);
                    scrollView_complete.setVisibility(View.INVISIBLE);
                    IV_personal.setImageResource(R.drawable.profile_basic_three);
                    IV_professional.setImageResource(R.drawable.profile_professional_two);
                    IV_complete.setImageResource(R.drawable.profile_complete_one);
                    view1.setBackgroundResource(R.color.colorAccent);
                    view2.setBackgroundResource(R.color.colorAccent);
                }
                else if(s_lnw_usertype.equals("vendor")||s_lnw_usertype.matches("vendor"))
                {
                    toolbar.setTitle("Verification");

                    scrollview_vendor_professional.setVisibility(View.VISIBLE);
                    scrollView_personal.setVisibility(View.INVISIBLE);
                    scrollView_professional.setVisibility(View.INVISIBLE);
                    scrollView_complete.setVisibility(View.INVISIBLE);
                    IV_personal.setImageResource(R.drawable.profile_basic_three);
                    IV_professional.setImageResource(R.drawable.profile_professional_two);
                    IV_complete.setImageResource(R.drawable.profile_complete_one);
                    view1.setBackgroundResource(R.color.colorAccent);
                    view2.setBackgroundResource(R.color.colorAccent);

                }

            }
        }
        else
        {
            toolbar.setTitle("Basic Info");
            scrollView_personal.setVisibility(View.VISIBLE);
            scrollView_professional.setVisibility(View.INVISIBLE);
            scrollView_complete.setVisibility(View.INVISIBLE);
            view1.setBackgroundResource(R.color.colorAccent);
        }
        Calendar now = Calendar.getInstance();
        dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                ProfileInfo.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        focuschange();


        ET_Prof_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genrateach= UUID.randomUUID().toString();
                dpd.show(getFragmentManager(),genrateach);
                dpd.setTitle("Valid Date");
            }
        });
        ET_vprof_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfo.this, MultiSpinner_Vendor_Category.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        IV_basic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1002);
            }
        });

        IV_prof_uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        IV_vendor_professional_companylogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1001);
            }
        });


        B_saveandcontinue_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                callmetouploadbasic();


            }
        });
        B_professional_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callmetouploadprofessional();



            }
        });
        B_vendorprofessional_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callmetouploadprofessional_vendor();
            }
        });
        but_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileInfo.this,HomeScreen.class));
            }
        });
        SP_vendor_com_planchoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selec = parent.getItemAtPosition(position).toString();

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                IV_basic_image.setImageBitmap(bitmap);
                basic_image(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                IV_prof_uploadfile.setImageBitmap(bitmap);
                upload_user_profimage(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                IV_vendor_professional_companylogo.setImageBitmap(bitmap);
                upload_vendor_companylogo(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(context);
                Ssubjectkind=tinydb.getString("subjecttype");
                if (Ssubjectkind.equals("Subject")) {
                    subjectnamelist = tinydb.getListString("subjectnamelist");
                    subjectnameid = tinydb.getListString("subjectnameid");
                    StringBuilder builder = new StringBuilder();
                    // JSONArray startendarray=new JSONArray();
                    for (int i = 0; i < subjectnamelist.size(); i++) {
                        //   JSONObject obj=new JSONObject();
                        // try {
//                obj.put("start",starttimesched.get(i));
//                obj.put("end",endtimesched.get(i));
                        builder.append("").append(subjectnamelist.get(i)).append(",");

                        //   } catch (JSONException e) {
                        //      e.printStackTrace();
                    }

                    ET_vprof_category.setText(builder);
                }


                //   startendarray.put(obj);
                //   }
                //Toast.makeText(context, "i got you", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void basic_image(final Bitmap bitmap) {


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
                        s_basic_image=jObj.getString("Response");
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
    private void upload_user_profimage(final Bitmap bitmap) {


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
                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_res_userprofimg=jObj.getString("Response");
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
    private void upload_vendor_companylogo(final Bitmap bitmap)
    {
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

                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_lnw_getcompany=jObj.getString("Response");
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
            new PromptDialog(ProfileInfo.this)
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
            if(ET_mname.getText().toString().equals(""))
            {
                new PromptDialog(ProfileInfo.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Enter Middle Name")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();

            }
            else
            {
                if(ET_lname.getText().toString().equals(""))
                {
                    new PromptDialog(ProfileInfo.this)
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
                    if(ET_emailid.getText().toString().equals(""))
                    {
                        new PromptDialog(ProfileInfo.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Your Email Id")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();

                    }
                    else
                    {
                        if(ET_mobile.getText().toString().equals(""))
                        {
                            new PromptDialog(ProfileInfo.this)
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
                            if(ET_address.getText().toString().equals(""))
                            {
                                new PromptDialog(ProfileInfo.this)
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
                            else
                            {
                                s_fname=ET_fname.getText().toString();
                                s_mname=ET_mname.getText().toString();
                                s_lname=ET_lname.getText().toString();
                                s_emailid=ET_emailid.getText().toString();
                                s_mobilepin="+965";
                                s_mobile=ET_mobile.getText().toString();
                                s_address=ET_address.getText().toString();
                                // s_city=ET_city.getText().toString();
                                // s_zipcode=ET_zipcode.getText().toString();
                                s_country="Kuwait";
                                scrollView_personal.setVisibility(View.INVISIBLE);

                                if(s_lnw_usertype.equals("user")||s_lnw_usertype.matches("user"))
                                {
                                    scrollView_professional.setVisibility(View.VISIBLE);
                                    toolbar.setTitle("Professional");

                                }
                                else if(s_lnw_usertype.equals("vendor")||s_lnw_usertype.matches("vendor"))
                                {
                                    scrollview_vendor_professional.setVisibility(View.VISIBLE);
                                    toolbar.setTitle("Verification");

                                }
                                scrollView_complete.setVisibility(View.INVISIBLE);
                                view2.setBackgroundResource(R.color.colorAccent);

                                IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_two);
                                IV_complete.setImageResource(R.drawable.profile_complete_one);
                                callmetouploadbasicurl(s_lnw_userid,s_emailid,s_fname,s_mname,s_lname,s_mobile,s_address,s_country);


                            }
                        }
                    }

                }
            }
        }

    }
    public void callmetouploadbasicurl(String user_uid,String user_email,String sfname,
                                       String smname,String slname,String smobile,String saddress,String scountry) {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
           jsonBody.put("Address1", saddress);
        //    jsonBody.put("Address2", scity);
            jsonBody.put("Id", user_uid);
            jsonBody.put("Image", s_basic_image);
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
            new PromptDialog(ProfileInfo.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Enter C.I.D.Number")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
            if(ET_Prof_memno.getText().toString().equals(""))
            {
                new PromptDialog(ProfileInfo.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Enter Member Number")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();

            }
            else
            {
                if(ET_Prof_valid.getText().toString().equals(""))
                {
                    new PromptDialog(ProfileInfo.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Enter Valid Date")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();

                }
                else
                {
                    s_prof_cidno=ET_Prof_cidno.getText().toString();
                    s_prof_memno=ET_Prof_memno.getText().toString();
                    s_prof_valid=ET_Prof_valid.getText().toString(); scrollView_personal.setVisibility(View.INVISIBLE);
                    scrollView_professional.setVisibility(View.INVISIBLE);
                    scrollview_vendor_professional.setVisibility(View.INVISIBLE);
                    scrollView_complete.setVisibility(View.VISIBLE);
                    view3.setBackgroundResource(R.color.colorAccent);
                    toolbar.setTitle("Subscription");
                    IV_personal.setImageResource(R.drawable.profile_basic_three);
                    IV_professional.setImageResource(R.drawable.profile_professional_three);
                    IV_complete.setImageResource(R.drawable.profile_complete_two);
                    but_complete.setVisibility(View.VISIBLE);

                    callmetouploadprofessionalurl(s_prof_cidno,s_prof_memno,s_lnw_userid,s_prof_valid,s_res_userprofimg);
                    getUserCompletesubscription();
                    IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_three);
                                IV_complete.setImageResource(R.drawable.profile_complete_three);
                                view4.setBackgroundResource(R.color.colorAccent);
                }
            }
        }

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
//    public void get_vendor_categ() {
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://weqar.co/webapi/api/vendor/category", new Response.Listener<String>() {
//
//            public void onResponse(String response) {
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    JSONArray jsonArray = jObj.getJSONArray("Response");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String s_uplan_plantypes = object.getString("Name");
//
//                        AScategory_vendor.add(new MultiSelectModel(i,s_uplan_plantypes));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            public String getBodyContentType() {
//
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //   headers.put("content-Type", "application/json");
//
//
//
//                return headers;
//
//            }
//
//
//
//        };
//
//        requestQueue.add(stringRequest);
//    }
    public void callmetouploadprofessional_vendor()
    {
        if(ET_vprof_category.getText().toString().equals(""))
        {
            new PromptDialog(ProfileInfo.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Select Category")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
            if(ET_vprof_businescontect.getText().toString().equals(""))
            {
                new PromptDialog(ProfileInfo.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Enter Business Contact")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();

            }
            else
            {
                if(ET_vprof_businesemail.getText().toString().equals(""))
                {
                    new PromptDialog(ProfileInfo.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Enter Business Email")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();

                }
                else
                {
                    String  SemailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                    svendor_busimail = ET_vprof_businesemail.getText().toString().trim();
                    if (svendor_busimail.matches(SemailPattern)) {
                        if (ET_vprof_websitel.getText().toString().equals(""))
                        {
                            new PromptDialog(ProfileInfo.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Website Link")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                        }
                        else {
                            s_vprof_category = ET_vprof_category.getText().toString();
                            s_vprof_buscontact = ET_vprof_businescontect.getText().toString();
                            s_vprof_busemail = ET_vprof_businesemail.getText().toString();
                            s_vprof_websitelink = ET_vprof_websitel.getText().toString();
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

                            callmetouploadprofessionalurl_vendor(s_vprof_buscontact, s_vprof_busemail, s_lnw_getcompany, s_lnw_userid, s_vprof_category);
                            getUserCompletesubscription();
                            IV_personal.setImageResource(R.drawable.profile_basic_three);
                            IV_professional.setImageResource(R.drawable.profile_professional_three);
                            IV_complete.setImageResource(R.drawable.profile_complete_three);
                            view4.setBackgroundResource(R.color.colorAccent);

                        }
                    }
                    else
                    {
                        new PromptDialog(ProfileInfo.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Check Your Entered EMail")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }

                }
            }
        }

    }

    public void callmetouploadprofessionalurl_vendor(String buscontact,String busemail,String vpro_logo,String vpro_userid,String v_procateg)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("BusinessContact", buscontact);
            jsonBody.put("BusinessEmail", busemail);
            jsonBody.put("Logo", vpro_logo);
            jsonBody.put("UserId", vpro_userid);

            JSONArray array2=new JSONArray(subjectnameid);
            jsonBody.put("Categories",array2);



            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_insertprofessionalinfo, new Response.Listener<String>() {

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
    public void getUserCompletesubscription()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.User_subscriptiondet_get, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String s_uplan_plantypes = object.getString("PlanType");
                        s_uplan_plantype=s_uplan_plantypes.trim();
                        s_uplan_amount = object.getString("Amount");
                        s_uplan_desc= object.getString("Description");
                        L_user_plantype.add(String.valueOf(s_uplan_plantype));
                        L_user_planamount.add(String.valueOf(s_uplan_amount));
                        L_user_desc.add(String.valueOf(s_uplan_desc));

                    }
                    Rec_usersubs.setAdapter(RecyclerViewHorizontalAdapter);




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
                return headers;

            }



        };

        requestQueue.add(stringRequest);
    }
    private void getVendorplan() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest("http://weqar.co/webapi/api/vendor/discountplan",
                new Response.Listener<String>() {
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
   //     RequestQueue requestQueue = Volley.newRequestQueue(this);
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
        SP_vendor_com_planchoose.setAdapter(new ArrayAdapter<String>(ProfileInfo.this, android.R.layout.simple_dropdown_item_1line, vendor_plan));
    }

    public void focuschange()
    {
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
        ET_emailid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_emailid.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_emailid.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });

        ET_Prof_cidno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_Prof_cidno.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_Prof_cidno.setTextColor((getResources().getColor(R.color.colorBlack)));
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
        ET_vprof_category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_category.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_category.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vprof_businescontect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_businescontect.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_businescontect.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vprof_businesemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_businesemail.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_businesemail.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vprof_websitel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_websitel.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_websitel.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
    }
}
