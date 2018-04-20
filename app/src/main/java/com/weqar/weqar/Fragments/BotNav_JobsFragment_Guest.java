package com.weqar.weqar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.jobscard_list;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter_JobField;
import com.weqar.weqar.JobDetails_Guest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.thefinestartist.utils.service.ServiceUtil.getSystemService;


public class BotNav_JobsFragment_Guest extends Fragment {
    ListView GV_jobs_user;
    String s_user_jobfield_name, s_user_jobfield_id;
    RecyclerView RV_home_hoizontal_scroll;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter_JobField RecyclerViewHorizontalAdapter;
    List<String> L_user_jobfield_name;
    List<String> L_user_jobfield_id;
    LinearLayoutManager HorizontalLayout;
    ImageView IV_nojobs;

    public static BotNav_JobsFragment_Guest newInstance() {
        BotNav_JobsFragment_Guest fragment = new BotNav_JobsFragment_Guest();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bot_nav__jobs_fragment__guest, container, false);

        GV_jobs_user = view.findViewById(R.id.jobs_vendor_gv);
        RV_home_hoizontal_scroll = view.findViewById(R.id.RV_jobs_user);
        IV_nojobs = view.findViewById(R.id.IV_noitem_jobs);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        L_user_jobfield_name = new ArrayList<String>();
        L_user_jobfield_id = new ArrayList<String>();
        RV_home_hoizontal_scroll.setLayoutManager(RecyclerViewLayoutManager);
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter_JobField(L_user_jobfield_id, L_user_jobfield_name, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_home_hoizontal_scroll.setLayoutManager(HorizontalLayout);
        RV_home_hoizontal_scroll.setHorizontalScrollBarEnabled(false);
        String URLLL = Global_URL.user_show_jobs;
        new kilomilo().execute(URLLL);
        getUserJobfields();

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
                holder.IV_logo = convertView.findViewById(R.id.IV_logo_jobuser);
                convertView.setTag(holder);
            } else {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            jobscard_list ccitacc = movieModelList.get(position);
            holder.text_jobtype.setText(ccitacc.getName());
            holder.textjobfield.setText(ccitacc.getJobType());
            holder.textdesc.setText(ccitacc.getDescription());
            String first = ccitacc.getClosingDate();
            holder.textdeadline.setText("Deadline " + first);
            try {
                Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.IV_logo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        class ViewHolder {
            public TextView text_jobtype, textjobfield, textdesc, textdeadline;
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
            if ((movieMode != null) && (movieMode.size() > 0) && getActivity() != null) {
                MovieAdap adapter = new MovieAdap(getActivity(), R.layout.content_jobs_user, movieMode);
                GV_jobs_user.setAdapter(adapter);
                GV_jobs_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        jobscard_list item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(), JobDetails_Guest.class);
                        intent.putExtra("put_jobs_user_logo", item.getLogo());
                        intent.putExtra("put_jobs_user_jobtype", item.getJobField());
                        intent.putExtra("put_jobs_user_jobname", item.getName());
                        intent.putExtra("put_jobs_user_jobfield", item.getJobType());
                        intent.putExtra("put_jobs_user_deadline", item.getClosingDate());
                        intent.putExtra("put_jobs_user_desc", item.getDescription());
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            } else {
                GV_jobs_user.setVisibility(View.INVISIBLE);
                IV_nojobs.setVisibility(View.VISIBLE);
            }
        }
    }

    public void getUserJobfields() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_getjobfield, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        s_user_jobfield_name = object.getString("Description");
                        s_user_jobfield_id = object.getString("Id");
                        L_user_jobfield_id.add(String.valueOf(s_user_jobfield_id));
                        L_user_jobfield_name.add(String.valueOf(s_user_jobfield_name));
                    }
                    RV_home_hoizontal_scroll.setAdapter(RecyclerViewHorizontalAdapter);
                } catch (JSONException e) {
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

    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}