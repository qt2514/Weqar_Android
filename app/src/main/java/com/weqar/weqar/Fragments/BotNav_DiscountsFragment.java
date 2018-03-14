package com.weqar.weqar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.discountcard_list;
import com.weqar.weqar.DiscountDetails;
import com.weqar.weqar.Global_url_weqar.Global_URL;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BotNav_DiscountsFragment extends Fragment {
    String S_disc;
    GridView GV_disc_user;
Context c;
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
        GV_disc_user=view.findViewById(R.id.disc_card_gridview);
        String URLLL = Global_URL.user_show_discount;
         new kilomilo().execute(URLLL);
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
                holder.menuimage = (ImageView)convertView.findViewById(R.id.IV_disc_image);
                holder.RIV_logo=convertView.findViewById(R.id.RIV_disc_logo);
                holder.ratingbar=convertView.findViewById(R.id.mappage_rating);
                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            discountcard_list ccitacc = movieModelList.get(position);

            holder.textone.setText(ccitacc.getPercentage()+"% "+ccitacc.getTitle());
String gg=ccitacc.getPercentage();
            Integer k=Integer.parseInt(gg);
            Integer kk=k/10;
            Float g=(float) kk;
           holder.ratingbar.setRating(g);
String ing=ccitacc.getImage().trim();
String ings=ccitacc.getLogo().trim();
            if (ing.isEmpty()) {
                holder.menuimage.setImageResource(R.drawable.profile_complete_two);
            } else{
                Picasso.with(context).load(ccitacc.getImage()).fit().centerCrop().into(holder.menuimage);
            }
            if (ings.isEmpty()) {
                holder.menuimage.setImageResource(R.drawable.profile_complete_two);
            } else{
                Picasso.with(context).load(ccitacc.getLogo()).fit().centerCrop().into(holder.RIV_logo);
            }

            return convertView;
        }

        class ViewHolder {
            public TextView textone;
            private ImageView menuimage;
            private RoundedImageView RIV_logo;
            RatingBar ratingbar;
        }
    }

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
                        discountcard_list item = movieMode.get(position);

                    }
                });

                adapter.notifyDataSetChanged();
            }


            else {


            }
        }
    }
}
