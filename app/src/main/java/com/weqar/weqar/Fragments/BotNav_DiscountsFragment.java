package com.weqar.weqar.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.discountcard_list;
import com.weqar.weqar.DiscountDetails_User;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.ImageConverter;
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter_Category;
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

public class BotNav_DiscountsFragment extends Fragment {
    String s_vendor_getho_name,s_vendor_getho_id;
    ListView GV_disc_user;
Context c;
    RecyclerView RV_home_hoizontal_scroll;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter_Category RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    List<String> L_vendor_hor_id;
    List<String> L_vendor_hor_name;
    public static BotNav_DiscountsFragment newInstance() {
        BotNav_DiscountsFragment fragment= new BotNav_DiscountsFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         c = getActivity().getApplicationContext();
        View view= inflater.inflate(R.layout.fragment_bot_nav__discounts, container, false);
        GV_disc_user=view.findViewById(R.id.disc_vendor_gv);
        String URLLL = Global_URL.user_show_discount;
         new kilomilo().execute(URLLL);
        RV_home_hoizontal_scroll=view.findViewById(R.id.home_hoizontal_scroll);
        L_vendor_hor_id= new ArrayList<String>();
        L_vendor_hor_name= new ArrayList<String>();

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        RV_home_hoizontal_scroll.setLayoutManager(RecyclerViewLayoutManager);

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter_Category(L_vendor_hor_id,L_vendor_hor_name,getActivity());

        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_home_hoizontal_scroll.setLayoutManager(HorizontalLayout);
        RV_home_hoizontal_scroll.setHorizontalScrollBarEnabled(false);
        getUserCompletesubscription();
        return view;
    }

    public class MovieAdap extends ArrayAdapter {
        private List<discountcard_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        MovieAdap(Context context, int resource, List<discountcard_list> objects) {
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
                holder.menuimage = convertView.findViewById(R.id.roundimg_one);
                holder.RIV_logo = convertView.findViewById(R.id.roundedImageView);

                holder.ratingbar=convertView.findViewById(R.id.RB_vendr_rating);
                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            discountcard_list ccitacc = movieModelList.get(position);

            holder.textone.setText(ccitacc.getPercentage()+"% "+ccitacc.getTitle());
            String gg=ccitacc.getPercentage();
            Integer k=Integer.parseInt(gg);
            Integer kk=k/20;
            Float g=(float) kk;
            holder.ratingbar.setRating(g);
            String ing=ccitacc.getImage().trim();
            String ings=ccitacc.getLogo().trim();


            try
            {
                Picasso.with(context).load(Global_URL.Image_url_load+ings).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.RIV_logo);
                Picasso.with(context).load(Global_URL.Image_url_load+ings).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.menuimage);

            }catch (Exception e){}



            return convertView;
        }

        class ViewHolder {
            public TextView textone;
            private ImageView menuimage;
            private CircleImageView RIV_logo;
            RatingBar ratingbar;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<discountcard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<discountcard_list> doInBackground(String... params) {
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
                List<discountcard_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    discountcard_list catego = gson.fromJson(finalObject.toString(), discountcard_list.class);
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
        protected void onPostExecute(final List<discountcard_list> movieMode) {
            super.onPostExecute(movieMode);

            if((movieMode != null) && (movieMode.size()>0) ){

                MovieAdap adapter = new MovieAdap(getActivity(), R.layout.fragment_discount_card, movieMode);
                GV_disc_user.setAdapter(adapter);
           GV_disc_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(),DiscountDetails_User.class));
               }
           });


                adapter.notifyDataSetChanged();
            }



        }
    }
    public void getUserCompletesubscription()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_select_categ, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        s_vendor_getho_name= object.getString("Name");
                        s_vendor_getho_id= object.getString("Id");

                        L_vendor_hor_id.add(String.valueOf(s_vendor_getho_id));
                        L_vendor_hor_name.add(String.valueOf(s_vendor_getho_name));


                    }
                    RV_home_hoizontal_scroll.setAdapter(RecyclerViewHorizontalAdapter);
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
