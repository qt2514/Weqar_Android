package com.weqar.weqar.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class BotNav_DiscountsFragment_Vendor  extends Fragment {
    String s_vendor_disc;
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

        String URLLL = Global_URL.Vendor_showown_discounts
                + "?Id="+s_vendor_disc;


        // Toast.makeText(getApplicationContext(), URLLL, Toast.LENGTH_SHORT).show();
      //  new kilomilo().execute(URLLL);
        return view;
    }
//
//    public class MovieAdap extends ArrayAdapter {
//        private List<subcatelist> movieModelList;
//        private int resource;
//        Context context;
//        private LayoutInflater inflater;
//
//        MovieAdap(Context context, int resource, List<subcatelist> objects) {
//            super(context, resource, objects);
//            movieModelList = objects;
//            this.context = context;
//            this.resource = resource;
//            inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getViewTypeCount() {
//            return 1;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final ViewHolder holder;
//            if (convertView == null) {
//                convertView = inflater.inflate(resource, null);
//                holder = new ViewHolder();
//
//                holder.textone = (TextView) convertView.findViewById(R.id.second_texttitle);
//                holder.menuimage = (ImageView)convertView.findViewById(R.id.second_imageview);
//                convertView.setTag(holder);
//            }//ino
//            else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            subcatelist ccitacc = movieModelList.get(position);
//            holder.textone.setText(ccitacc.getService_subcateg_name());
//
//            Picasso.with(context).load(ccitacc.getService_subcateg_fullimage()).fit().error(R.drawable.load).fit().into(holder.menuimage);
//
//            return convertView;
//        }
//
//        class ViewHolder {
//            public TextView textone;
//            private ImageView menuimage;
//        }
//    }
//
//    public class kilomilo extends AsyncTask<String, String, List<subcatelist>> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected List<subcatelist> doInBackground(String... params) {
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream stream = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(stream));
//                StringBuilder buffer = new StringBuilder();
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                }
//                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("result");
//                List<subcatelist> milokilo = new ArrayList<>();
//                Gson gson = new Gson();
//                for (int i = 0; i < parentArray.length(); i++) {
//                    JSONObject finalObject = parentArray.getJSONObject(i);
//                    subcatelist catego = gson.fromJson(finalObject.toString(), subcatelist.class);
//                    milokilo.add(catego);
//                }
//                return milokilo;
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(final List<subcatelist> movieMode) {
//            super.onPostExecute(movieMode);
//            dialog.dismiss();
//            if((movieMode != null) && (movieMode.size()>0) ){
//                second_listview.setVisibility(View.VISIBLE);
//                underser_gif.setVisibility(View.INVISIBLE);
//                normal_text.setVisibility(View.INVISIBLE);
//                normal_texts.setVisibility(View.INVISIBLE);
//
//                MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.categorytwo, movieMode);
//                second_listview.setAdapter(adapter);
//                second_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        subcatelist item = movieMode.get(position);
//                        Intent intent = new Intent(Second.this,MapsActivity.class);
//                        intent.putExtra("categid",categid);
//                        intent.putExtra("subcategid",item.getService_subcateg_uid());
//                        intent.putExtra("subcategname",item.getService_subcateg_name());
//                        intent.putExtra("categoryname_user",categoryname_user);
//                        startActivity(intent);
//                    }
//                });
//
//                adapter.notifyDataSetChanged();
//            }
//
//
//            else {
//                second_listview.setVisibility(View.INVISIBLE);
//                underser_gif.setVisibility(View.VISIBLE);
//                normal_text.setVisibility(View.VISIBLE);
//                normal_texts.setVisibility(View.VISIBLE);
//
//
//            }
//        }
//    }

}
