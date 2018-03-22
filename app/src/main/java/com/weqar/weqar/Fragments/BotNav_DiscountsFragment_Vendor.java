package com.weqar.weqar.Fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.weqar.weqar.AddDiscount_Vendor;
import com.weqar.weqar.DBJavaClasses.discountcard_list_vendor;
import com.weqar.weqar.DiscountDetails_Vendor;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BotNav_DiscountsFragment_Vendor  extends Fragment
{
    String s_vendor_disc,s_vendor_token;
    SwipeMenuListView GV_vendor_view;
    ImageView IV_adddiscount_vendor;
    public static BotNav_DiscountsFragment_Vendor newInstance()
    {
        BotNav_DiscountsFragment_Vendor fragment= new BotNav_DiscountsFragment_Vendor();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_bot_nav__discounts_fragment__vendor, container, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        s_vendor_disc = preferences.getString("weqar_uid", "");
        s_vendor_token = preferences.getString("weqar_token", "");
        GV_vendor_view=view.findViewById(R.id.disc_vendor_gv);
        IV_adddiscount_vendor=view.findViewById(R.id.homescreen_adddiscount);
        IV_adddiscount_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(),AddDiscount_Vendor.class));
            }
        });
        new kilomilo().execute(Global_URL.Vendor_showown_discounts);
        return view;
    }
    public class MovieAdap extends ArrayAdapter
    {
        private List<discountcard_list_vendor> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<discountcard_list_vendor> objects)
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
            final ViewHolder holder;
            if (convertView == null)
            {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();
                holder.textone = (TextView) convertView.findViewById(R.id.TV_disc_percentage_vendor);
                holder.menuimage = convertView.findViewById(R.id.roundimg_one);
                holder.RB_vendor_rating = (RatingBar)convertView.findViewById(R.id.RB_vendr_rating_vendor);
                holder.RIV_logo=convertView.findViewById(R.id.RIV_vendor_logo_vendor);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            discountcard_list_vendor ccitacc = movieModelList.get(position);
            holder.textone.setText(ccitacc.getPercentage()+"% "+ccitacc.getTitle());
            String vendor_image=ccitacc.getImage();
            String gg=ccitacc.getPercentage();
            Integer k=Integer.parseInt(gg);
            Integer kk=k/10;
            Float g=(float) kk;
            holder.RB_vendor_rating.setRating(g);
            //  holder.menuimage.setImageResource(R.drawable.vgv);
            try
            {
                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.menuimage);


                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.RIV_logo);
            }catch (Exception e){

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
            public TextView textone;
            private ImageView menuimage;
           CircleImageView RIV_logo;
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
              GV_vendor_view.setVisibility(View.VISIBLE);
                MovieAdap adapter = new MovieAdap(getActivity(), R.layout.fragment_discountcard_vendor, movieMode);
                GV_vendor_view.setAdapter(adapter);
                GV_vendor_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        discountcard_list_vendor item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(),DiscountDetails_Vendor.class);
                        intent.putExtra("put_title",item.getTitle());
                        intent.putExtra("put_per",item.getPercentage());
                        intent.putExtra("put_desc",item.getDescription());
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        }
    }
}