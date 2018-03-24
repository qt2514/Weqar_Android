package com.weqar.weqar.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.AddJobs_Vendor;
import com.weqar.weqar.DBJavaClasses.jobscard_list;
import com.weqar.weqar.DBJavaClasses.jobscard_list_vendor;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JobDetails_User;
import com.weqar.weqar.JobDetails_Vendor;
import com.weqar.weqar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class BotNav_JobsFragment_Vendor extends Fragment {
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    public static BotNav_JobsFragment_Vendor newInstance() {
        BotNav_JobsFragment_Vendor fragment= new BotNav_JobsFragment_Vendor();
        return fragment;
    }

    ImageView IV_addjobs_vendor;
    String s_vendor_disc,s_vendor_token;
    SwipeMenuListView GV_vendor_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__jobs_fragment__vendor, container, false);
        Shared_user_details=getActivity().getSharedPreferences("user_detail_mode",0);
        s_vendor_disc=  Shared_user_details.getString("weqar_uid", null);
        s_vendor_token=  Shared_user_details.getString("weqar_token", null);

GV_vendor_view=view.findViewById(R.id.weqar_vendor_addjobs);
        IV_addjobs_vendor=view.findViewById(R.id.homescreen_addjobs);
        IV_addjobs_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(),AddJobs_Vendor.class));
            }
        });
        new kilomilo().execute(Global_URL.Vendor_showownjobs);


        return view;
    }

    public class MovieAdap extends ArrayAdapter
    {
        private List<jobscard_list_vendor> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<jobscard_list_vendor> objects)
        {
            super(context, resource, objects);
            movieModelList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getViewTypeCount() {
            return 1;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            final MovieAdap.ViewHolder holder;
            if (convertView == null)
            {
                convertView = inflater.inflate(resource, null);
                holder = new MovieAdap.ViewHolder();
                holder.text_jobtype = (TextView) convertView.findViewById(R.id.fag_jobs_type_vendor);
                holder.text_jobfield = (TextView) convertView.findViewById(R.id.fag_jobs_field_vendor);
                holder.text_jobdesc = (TextView) convertView.findViewById(R.id.fag_jobs_desc_vendor);
                holder.text_jobdeadline = (TextView) convertView.findViewById(R.id.fag_jobs_deadline_vendor);
                holder.RIV_logo=convertView.findViewById(R.id.IV_logo_job_vendor);
                convertView.setTag(holder);
            }
            else
            {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            jobscard_list_vendor ccitacc = movieModelList.get(position);

            holder.text_jobtype.setText(ccitacc.getJobType());
            holder.text_jobfield.setText(ccitacc.getJobField());
            holder.text_jobdesc.setText(ccitacc.getDescription());
            holder.text_jobdeadline.setText(ccitacc.getClosingDate());
            try
            {
                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.RIV_logo);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            SwipeMenuCreator creator = new SwipeMenuCreator()
            {
                @Override
                public void create(SwipeMenu menu)
                {
                    SwipeMenuItem more_sched = new SwipeMenuItem(
                            getContext());
                    more_sched.setBackground(R.color.colorHints);
                    more_sched.setWidth(180);
                    more_sched.setTitle("More");
                    more_sched.setIcon(R.drawable.ic_more_horiz_black_24dp);
                    more_sched.setTitleSize(12);
                    more_sched.setTitleColor(Color.WHITE);
                    menu.addMenuItem(more_sched);
                    SwipeMenuItem review_sched = new SwipeMenuItem(
                            getContext());
                    review_sched.setBackground(R.color.colorPrimary);
                    review_sched.setWidth(180);
                    review_sched.setTitle("Delete");
                    review_sched.setTitleSize(12);
                    review_sched.setTitleColor(Color.WHITE);
                    review_sched.setIcon(R.drawable.discount_vendor_delete);
                    menu.addMenuItem(review_sched);
                }
            };
            GV_vendor_view.setMenuCreator(creator);
            GV_vendor_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            Toast.makeText(getActivity(), "Chat"+position, Toast.LENGTH_SHORT).show();

                            break;
                        case 1:
                            Toast.makeText(getActivity(), "Message"+position, Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }
            });
            return convertView;
        }
        class ViewHolder {
            public TextView text_jobtype,text_jobfield,text_jobdesc,text_jobdeadline;
            CircleImageView RIV_logo;

        }
    }
    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<jobscard_list_vendor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<jobscard_list_vendor> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
                DataInputStream inputStream;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestProperty("x-api-key",s_vendor_token);
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","vendor");
                auth.put("UserId",s_vendor_disc);
                auth.put("PageNumber", "1");
                auth.put("RowsPerPage", "5");
                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                printout.flush ();
                printout.close ();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject ed = parentObject.getJSONObject("Response");
                JSONArray parentArray = ed.getJSONArray("Data");
                List<jobscard_list_vendor> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    jobscard_list_vendor catego = gson.fromJson(finalObject.toString(), jobscard_list_vendor.class);
                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(final List<jobscard_list_vendor> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0) ){
//                GV_vendor_view.setVisibility(View.VISIBLE);
               MovieAdap adapter = new MovieAdap(getActivity(), R.layout.content_jobs_vendor_card, movieMode);
                GV_vendor_view.setAdapter(adapter);
                GV_vendor_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        jobscard_list_vendor item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(),JobDetails_Vendor.class);
                        intent.putExtra("put_jobs_vendor_logo",item.getLogo());
                        intent.putExtra("put_jobs_vendor_jobtype",item.getJobType());
                        intent.putExtra("put_jobs_vendor_jobfield",item.getJobField());
                        intent.putExtra("put_jobs_vendor_deadline",item.getClosingDate());
                        intent.putExtra("put_jobs_vendor_desc",item.getDescription());
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        }
    }
}
