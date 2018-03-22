package com.weqar.weqar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.discountcard_list;
import com.weqar.weqar.DBJavaClasses.jobscard_list;
import com.weqar.weqar.DiscountDetails_User;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JobDetails;
import com.weqar.weqar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class BotNav_JobsFragment extends Fragment {
    ListView GV_jobs_user;
    public static BotNav_JobsFragment newInstance() {
        BotNav_JobsFragment fragment= new BotNav_JobsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__jobs, container, false);
        GV_jobs_user=view.findViewById(R.id.jobs_vendor_gv);
        String URLLL = Global_URL.user_show_jobs;
        new kilomilo().execute(URLLL);
return view;
    }

    public class MovieAdap extends ArrayAdapter {
        private List<jobscard_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        MovieAdap(Context context, int resource, List<jobscard_list> objects) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MovieAdap.ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new MovieAdap.ViewHolder();

                holder.text_jobtype = (TextView) convertView.findViewById(R.id.fag_jobs_type_user);
                holder.textjobfield = convertView.findViewById(R.id.fag_jobs_field_user);
                holder.textdesc = convertView.findViewById(R.id.fag_jobs_desc_user);
                holder.textdeadline = convertView.findViewById(R.id.fag_jobs_deadline_user);
                holder.IV_logo=convertView.findViewById(R.id.IV_logo_jobuser);
                convertView.setTag(holder);
            }//ino

            else {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            jobscard_list ccitacc = movieModelList.get(position);

            holder.text_jobtype.setText(ccitacc.getJobType());
            holder.textjobfield.setText(ccitacc.getJobField());
            holder.textdesc.setText(ccitacc.getDescription());
            holder.textdeadline.setText(ccitacc.getClosingDate());

            try {

                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.IV_logo);

            } catch (Exception e) {

                e.printStackTrace();
            }


            return convertView;
        }

        class ViewHolder {
            public TextView text_jobtype,textjobfield,textdesc,textdeadline;
            public CircleImageView IV_logo;
        }
    }

    public class kilomilo extends AsyncTask<String, String, List<jobscard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<jobscard_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("Response");
                List<jobscard_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    jobscard_list catego = gson.fromJson(finalObject.toString(), jobscard_list.class);
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
        protected void onPostExecute(final List<jobscard_list> movieMode) {
            super.onPostExecute(movieMode);

            if((movieMode != null) && (movieMode.size()>0) ){

                MovieAdap adapter = new MovieAdap(getActivity(), R.layout.item_list_app, movieMode);
                GV_jobs_user.setAdapter(adapter);
                GV_jobs_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(getActivity(),JobDetails.class));
                    }
                });


                adapter.notifyDataSetChanged();
            }



        }
    }

}
