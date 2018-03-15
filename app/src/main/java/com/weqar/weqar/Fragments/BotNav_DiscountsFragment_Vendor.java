package com.weqar.weqar.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.discountcard_list_vendor;
import com.weqar.weqar.Global_url_weqar.Global_URL;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class BotNav_DiscountsFragment_Vendor  extends Fragment {
    String s_vendor_disc,s_vendor_token;
    GridView GV_vendor_view;

    public static BotNav_DiscountsFragment_Vendor newInstance() {
        BotNav_DiscountsFragment_Vendor fragment= new BotNav_DiscountsFragment_Vendor();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_bot_nav__discounts_fragment__vendor, container, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        s_vendor_disc = preferences.getString("weqar_uid", "");
        s_vendor_token = preferences.getString("weqar_token", "");
        GV_vendor_view=view.findViewById(R.id.disc_vendor_gv);
        new kilomilo().execute(Global_URL.Vendor_showown_discounts);
        getUserCompletesubscription();
        return view;
    }

    public class MovieAdap extends ArrayAdapter {
        private List<discountcard_list_vendor> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        MovieAdap(Context context, int resource, List<discountcard_list_vendor> objects) {
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
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();

                holder.textone = (TextView) convertView.findViewById(R.id.TV_disc_percentage);
                holder.menuimage = (ImageView)convertView.findViewById(R.id.IV_vdisc_image);
                holder.RB_vendor_rating = (RatingBar)convertView.findViewById(R.id.RB_vendr_rating);
                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            discountcard_list_vendor ccitacc = movieModelList.get(position);
            holder.textone.setText(ccitacc.getTitle());
            String vendor_image=ccitacc.getImage();
            String gg=ccitacc.getPercentage();
            Integer k=Integer.parseInt(gg);
            Integer kk=k/10;
            Float g=(float) kk;
            holder.RB_vendor_rating.setRating(g);
            if(vendor_image.isEmpty())
            {
                holder.menuimage.setImageResource(R.drawable.vgv);
            }
            else{
                 Picasso.with(context).load(ccitacc.getImage()).fit().fit().into(holder.menuimage);
            }

            return convertView;
        }

        class ViewHolder {
            public TextView textone;
            private ImageView menuimage;
            RatingBar RB_vendor_rating;
        }
    }

    public class kilomilo extends AsyncTask<String, String, List<discountcard_list_vendor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<discountcard_list_vendor> doInBackground(String... params) {
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

                List<discountcard_list_vendor> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    discountcard_list_vendor catego = gson.fromJson(finalObject.toString(), discountcard_list_vendor.class);




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
        protected void onPostExecute(final List<discountcard_list_vendor> movieMode) {
            super.onPostExecute(movieMode);

            if((movieMode != null) && (movieMode.size()>0) ){


                MovieAdap adapter = new MovieAdap(getActivity(), R.layout.fragment_discountcard_vendor, movieMode);
                GV_vendor_view.setAdapter(adapter);
                GV_vendor_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        discountcard_list_vendor item = movieMode.get(position);

                    }
                });

                adapter.notifyDataSetChanged();
            }


            else {



            }
        }
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
//                        s_uplan_plantype=s_uplan_plantypes.trim();
//                        s_uplan_amount = object.getString("Amount");
//                        s_uplan_desc= object.getString("Description");
//                        s_uplan_planid=object.getString("Id");
//                        L_user_planid.add(String.valueOf(s_uplan_planid));
//                        L_user_plantype.add(String.valueOf(s_uplan_plantype));
//                        L_user_planamount.add(String.valueOf(s_uplan_amount));
//                        L_user_desc.add(String.valueOf(s_uplan_desc));

                    }
                 //   Rec_usersubs.setAdapter(RecyclerViewHorizontalAdapter);
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
}
