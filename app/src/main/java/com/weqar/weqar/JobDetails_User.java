package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import java.lang.reflect.InvocationTargetException;

import cn.refactor.lib.colordialog.PromptDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class JobDetails_User extends AppCompatActivity {
    ImageView IV_jobdetails_back;
    CircleImageView CIV_Ujobdet_logo;
    TextView TV_ujobdet_jobtype,TV_ujobdet_jobfield,TV_ujob_jobdeadline,TV_ujob_desc,TV_job_name;
    String s_jobdet_logo,s_jobdet_jobtype,s_jobdet_jobfield,s_jobdet_jobdeadline,s_jobdet_desc,s_jobdet_name;
    Button But_apply_job_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            IV_jobdetails_back = findViewById(R.id.job_details_back);
            IV_jobdetails_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            CIV_Ujobdet_logo = findViewById(R.id.logo_user_jobdet);
            TV_ujobdet_jobtype = findViewById(R.id.text_jobtype_user_jobdet);
            TV_job_name = findViewById(R.id.title_jobdesc_user);
            TV_ujobdet_jobfield = findViewById(R.id.text_jobfield_user_jobdet);
            TV_ujob_jobdeadline = findViewById(R.id.text_deadline_user_jobdet);
            TV_ujob_desc = findViewById(R.id.text_desc_user_jobdet);
            But_apply_job_user = findViewById(R.id.apply_job_user);
            But_apply_job_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new PromptDialog(JobDetails_User.this)
//                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
//                            .setAnimationEnable(true)
//                            .setTitleText("Job Applied Successfully")
//                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                                @Override
//                                public void onClick(PromptDialog dialog) {
//                                    startActivity(new Intent(JobDetails_User.this, HomeScreen.class));
//                                }
//                            }).show();
                }
            });
            Intent intent = getIntent();
            s_jobdet_logo = intent.getStringExtra("put_jobs_user_logo");
            s_jobdet_jobtype = intent.getStringExtra("put_jobs_user_jobtype");
            s_jobdet_name = intent.getStringExtra("put_jobs_user_jobname");
            s_jobdet_jobfield = intent.getStringExtra("put_jobs_user_jobfield");
            s_jobdet_jobdeadline = intent.getStringExtra("put_jobs_user_deadline");
            s_jobdet_desc = intent.getStringExtra("put_jobs_user_desc");
            try {

                Picasso.with(this).load(Global_URL.Image_url_load + s_jobdet_logo).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CIV_Ujobdet_logo);
                TV_ujobdet_jobtype.setText(s_jobdet_jobfield + " -");
                TV_ujobdet_jobfield.setText(s_jobdet_jobtype);
                TV_ujob_jobdeadline.setText(s_jobdet_jobdeadline);
                TV_ujob_desc.setText(s_jobdet_desc);
                TV_job_name.setText("Title - "+s_jobdet_name);

            } catch (Exception e) {
            }
        }
        else
        {


            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobDetails_User.this, HomeScreen.class);
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

}
