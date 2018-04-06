package com.weqar.weqar.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.dashboard_list;

import com.weqar.weqar.Events_Display;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JobDetails_User;
import com.weqar.weqar.News_Display;
import com.weqar.weqar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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


public class BotNav_EventsFragment extends Fragment {
    private FloatingActionMenu dash_menu;
   ListView LV_listview;
   ImageView IV_event;
    SharedPreferences Shared_user_details;
    String s_vendor_token,s_vendor_disc;
    FloatingActionButton But_dash_u_events,But_dash_u_news;
    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;

    public static BotNav_EventsFragment newInstance() {
        BotNav_EventsFragment fragment= new BotNav_EventsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__events, container, false);

        dash_menu = view.findViewById(R.id.menu_red);
        IV_event = view.findViewById(R.id.IV_noitem_events);

        Shared_user_details = getActivity().getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);
        LV_listview=view.findViewById(R.id.events_user_listview);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        fab3 = view.findViewById(R.id.fab3);
        But_dash_u_events=view.findViewById(R.id.dashboard_button_u_events);
        But_dash_u_news=view.findViewById(R.id.dashboard_button_u_news);

        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);
        But_dash_u_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Events_Display.class));
            }
        });
        But_dash_u_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),News_Display.class));
            }
        });
//        View headerview = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fragment_bot_nav__events, null, false);
//        LV_listview.addHeaderView(headerview);
      //  LV_listview.addHeaderView(But_dash_u_events);
        String URLLL = Global_URL.user_show_dashboard;
        new kilomilo().execute(URLLL);


            return view;
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LV_listview.setClickable(false);
            switch (v.getId()) {
                case R.id.fab1:
                    Toast.makeText(getActivity(), "News", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fab2:
                    Toast.makeText(getActivity(), "Events", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fab3:
                    Toast.makeText(getActivity(), "Discount", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    public class MovieAdap extends ArrayAdapter {
        private List<dashboard_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<dashboard_list> objects) {
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
                holder.image_event_user = convertView.findViewById(R.id.image_event_user);
                holder.image_two_event_user = convertView.findViewById(R.id.image_two_event_user);
                holder.logo_event_user = convertView.findViewById(R.id.logo_event_user);
                holder.event_discount_layout = convertView.findViewById(R.id.event_discount_layout);
                holder.event_event_layout = convertView.findViewById(R.id.event_event_layout);
                holder.TV_event_texttitle = convertView.findViewById(R.id.TV_event_texttitle);
                holder.TV_event_textenddate = convertView.findViewById(R.id.TV_event_textenddate);
                holder.TV_event_etitle = convertView.findViewById(R.id.TV_event_etitle);
                holder.TV_event_edesc = convertView.findViewById(R.id.TV_event_edesc);
                holder.RB_event_rating = convertView.findViewById(R.id.RB_event_rating);

                convertView.setTag(holder);
            }
            else
            {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            dashboard_list ccitacc = movieModelList.get(position);
            String getdashboardtype=ccitacc.getType();
            switch (getdashboardtype) {
                case "Discount":
                    holder.event_discount_layout.setVisibility(View.VISIBLE);
                    holder.event_event_layout.setVisibility(View.INVISIBLE);
                    holder.image_two_event_user.setVisibility(View.VISIBLE);
                    holder.image_event_user.setVisibility(View.VISIBLE);
                    holder.logo_event_user.setVisibility(View.VISIBLE);
                    holder.TV_event_texttitle.setVisibility(View.VISIBLE);
                    holder.RB_event_rating.setVisibility(View.VISIBLE);
                    holder.TV_event_textenddate.setVisibility(View.VISIBLE);
                    holder.TV_event_etitle.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_edesc.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_texttitle.setText(ccitacc.getTitle());
                    try {
                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.logo_event_user);
                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.image_event_user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
//                case "Job":
//                          holder.TV_event_texttitle.setVisibility(View.INVISIBLE);
//                          holder.TV_event_textenddate.setVisibility(View.INVISIBLE);
//                          holder.image_two_event_user.setVisibility(View.INVISIBLE);
//                          holder.RB_event_rating.setVisibility(View.INVISIBLE);
//                        holder.event_event_layout.setVisibility(View.VISIBLE);
//                        holder.logo_event_user.setVisibility(View.INVISIBLE);
//                        holder.image_event_user.setVisibility(View.VISIBLE);
//                        holder.image_two_event_user.setVisibility(View.INVISIBLE);
//                    holder.TV_event_etitle.setTextColor(getResources().getColor(R.color.colorBlack));
//                    holder.TV_event_edesc.setTextColor(getResources().getColor(R.color.colorBlack));
//
//                    holder.event_event_layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
//                        holder.TV_event_etitle.setText(ccitacc.getTitle());
//                    try {
//                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.image_event_user);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
                case "Event":
                    holder.TV_event_texttitle.setVisibility(View.INVISIBLE);
                    holder.TV_event_textenddate.setVisibility(View.INVISIBLE);
                    holder.image_two_event_user.setVisibility(View.INVISIBLE);
                    holder.RB_event_rating.setVisibility(View.INVISIBLE);
                    holder.event_event_layout.setVisibility(View.VISIBLE);
                    holder.logo_event_user.setVisibility(View.INVISIBLE);
                    holder.image_event_user.setVisibility(View.VISIBLE);
                    holder.image_two_event_user.setVisibility(View.INVISIBLE);
                    holder.event_event_layout.setBackgroundColor(getResources().getColor(R.color.colorBlacks));
                    holder.TV_event_etitle.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_edesc.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_etitle.setText(ccitacc.getTitle());
                    try {
                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.image_event_user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:

                    break;
            }
            return convertView;
        }
        class ViewHolder {
            public ImageView image_event_user,image_two_event_user;
            CircleImageView logo_event_user;
                LinearLayout event_discount_layout,event_event_layout;
                TextView TV_event_texttitle,TV_event_textenddate,TV_event_etitle,TV_event_edesc;
                RatingBar RB_event_rating;

        }
    }
    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<dashboard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<dashboard_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestProperty("x-api-key",s_vendor_token);
                connection.setRequestMethod("GET");
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
                List<dashboard_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    dashboard_list catego = gson.fromJson(finalObject.toString(), dashboard_list.class);
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
        protected void onPostExecute(final List<dashboard_list> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0) &&getActivity()!=null ){
               MovieAdap adapter = new MovieAdap(getActivity(), R.layout.conent_dashboard_user, movieMode);
                LV_listview.setAdapter(adapter);
                LV_listview.setVisibility(View.VISIBLE);
                IV_event.setVisibility(View.INVISIBLE);
                LV_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dashboard_list item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(),JobDetails_User.class);
//                        intent.putExtra("put_jobs_user_logo",item.getLogo());
//                        intent.putExtra("put_jobs_user_jobtype",item.getJobField());
//                        intent.putExtra("put_jobs_user_jobname",item.getName());
//                        intent.putExtra("put_jobs_user_jobfield",item.getJobType());
//                        intent.putExtra("put_jobs_user_deadline",item.getClosingDate());
//                        intent.putExtra("put_jobs_user_desc",item.getDescription());
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }
            else
            {
                LV_listview.setVisibility(View.INVISIBLE);
                IV_event.setVisibility(View.VISIBLE);
            }
        }
    }

}
