package com.weqar.weqar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.dashboard_list;
import com.weqar.weqar.DBJavaClasses.discountcard_list_vendor;
import com.weqar.weqar.DBJavaClasses.events_list_vendor;
import com.weqar.weqar.Fragments.BotNav_DiscountsFragment_Vendor;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class Events_Display_Vendor extends AppCompatActivity {
    String s_vendor_disc,s_vendor_token;
    SwipeMenuListView GV_vendor_view;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    ImageView IV_addevent_vendor,events_v_back;
    List<events_list_vendor> milokilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__display__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);
        GV_vendor_view = findViewById(R.id.events_vendor_list);
        events_v_back = findViewById(R.id.events_v_back);
        events_v_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(Events_Display_Vendor.this,HomeScreen_vendor.class));
            }
        });
//        IV_addevent_vendor =findViewById(R.id.event_addevent_vendor);
//        IV_addevent_vendor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), AddEvents_Vendor.class));
//            }
//        });
        new kilomilo().execute(Global_URL.Vendor_show_allevents);

    }
    public class MovieAdap extends ArrayAdapter
    {
        private List<events_list_vendor> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<events_list_vendor> objects)
        {
            super(context, resource, objects);
            movieModelList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
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
                holder.textone = (TextView) convertView.findViewById(R.id.events_vendor_title);
                holder.texttwo_desc = (TextView) convertView.findViewById(R.id.events_vendor_desc);
                holder.menuimage = convertView.findViewById(R.id.events_vendor_image);

                convertView.setTag(holder);
            }
            else
            {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            final events_list_vendor ccitacc = movieModelList.get(position);


            try
            {
                holder.textone.setText(ccitacc.getTitle());
                holder.texttwo_desc.setText(ccitacc.getDescription());
                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.menuimage);
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
                    more_sched.setTitle("Edit");
                    more_sched.setIcon(R.drawable.ic_edit_black_24dp);
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
                     events_list_vendor schedule_history_list = milokilo.get(position);

                    switch (index) {
                        case 0:
                            Intent intent=new Intent(getApplicationContext(),Discount_Edit_Vendor.class);
                            intent.putExtra("put_discountid_fordisc_edit",schedule_history_list.getId());

                            startActivity(intent);
                            break;
                        case 1:
                            String ed=schedule_history_list.getId();
                          // Toast.makeText(getApplicationContext(),ed, Toast.LENGTH_SHORT).show();
                           callmetodeleteiscount(ed);
                            break;
                    }
                    return false;
                }
            });
            return convertView;
        }
        class ViewHolder {
            public TextView textone,texttwo_desc;
            private ImageView menuimage;

        }
    }
    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<events_list_vendor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<events_list_vendor> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);


                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestProperty("x-api-key",s_vendor_token);
                connection.setRequestMethod("POST");
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
             milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    events_list_vendor catego = gson.fromJson(finalObject.toString(), events_list_vendor.class);
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
        protected void onPostExecute(final List<events_list_vendor> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0)){
                GV_vendor_view.setVisibility(View.VISIBLE);
            MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.content_event_list_vendor, movieMode);
                GV_vendor_view.setAdapter(adapter);
                GV_vendor_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        events_list_vendor item = movieMode.get(position);
                        Intent intent = new Intent(getApplicationContext(),EventDetails_Vendor.class);
                        intent.putExtra("event_v_id",item.getId());

                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }

        }
    }
    public void callmetodeleteiscount(String id)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", id);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_delete_events, new Response.Listener<String>() {
                public void onResponse(String response) {
                    new PromptDialog(Events_Display_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Your Event Deleted Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                   startActivity(new Intent(Events_Display_Vendor.this,Events_Display_Vendor.class));
                                }
                            }).show();
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
                    //// headers.put("Content-Type", "application/json");
                    // headers.put("x-tutor-app-id", "tutor-app-android");
                    headers.put("x-api-type","Android");
                    headers.put("x-api-key",s_vendor_token);
                    return headers;
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
