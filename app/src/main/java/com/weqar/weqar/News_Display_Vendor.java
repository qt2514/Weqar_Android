package com.weqar.weqar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.weqar.weqar.DBJavaClasses.news_list;
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


public class News_Display_Vendor extends AppCompatActivity {
    String s_vendor_disc,s_vendor_token;
    SwipeMenuListView SL_news_user_view;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    ImageView IV_addews_user,news_u_back;
    List<news_list> milokilo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__display__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);

        SL_news_user_view = findViewById(R.id.news_user_list);
        IV_addews_user = findViewById(R.id.news_u_addnews);
        news_u_back = findViewById(R.id.news_u_back);
        news_u_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_Display_Vendor.this,HomeScreen_vendor.class));
                finish();
            }
        });
        IV_addews_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_Display_Vendor.this, AddNews_Vendor.class));
            }
        });

        new kilomilo().execute(Global_URL.user_show_news);
        }
        else
        {
            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(News_Display_Vendor.this, News_Display_Vendor.class);
                    startActivity(intent);
                }
            });
        }
    }
    public class MovieAdap extends ArrayAdapter
    {
        private List<news_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<news_list> objects)
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
                holder.textone = (TextView) convertView.findViewById(R.id.news_user_title);
                holder.texttwo_desc = (TextView) convertView.findViewById(R.id.news_user_desc);
                holder.menuimage = convertView.findViewById(R.id.news_user_image);

                convertView.setTag(holder);
            }
            else
            {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            final news_list ccitacc = movieModelList.get(position);


            try
            {
                holder.textone.setText(ccitacc.getTitle());
                String hh=ccitacc.getContent();
                Spanned htmlAsSpanned = Html.fromHtml(hh);
                holder.texttwo_desc.setText(htmlAsSpanned);


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
            SL_news_user_view.setMenuCreator(creator);
            SL_news_user_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    news_list schedule_history_list = milokilo.get(position);

                    switch (index) {
                        case 0:
                            Intent intent=new Intent(getApplicationContext(),News_Edit_Vendor.class);
                            intent.putExtra("put_userid_fornews_edit",schedule_history_list.getUserId());
                            intent.putExtra("put_newsid_fornews_edit",schedule_history_list.getId());
                            intent.putExtra("put_url_fornews_edit",schedule_history_list.getURL());
                            intent.putExtra("put_title_fornews_edit",schedule_history_list.getTitle());
                            intent.putExtra("put_type_fornews_edit",schedule_history_list.getNewsTypeId());
                            intent.putExtra("put_image_fornews_edit",schedule_history_list.getImage());
                            intent.putExtra("put_content_fornews_edit",schedule_history_list.getContent());

                            startActivity(intent);
                            break;
                        case 1:
                            String ed=schedule_history_list.getId();
                            // Toast.makeText(getApplicationContext(),ed, Toast.LENGTH_SHORT).show();
                            callmetodeletenews(ed);
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
    public class kilomilo extends AsyncTask<String, String, List<news_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<news_list> doInBackground(String... params) {
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
                auth.put("QueryFor","User");
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

                milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    news_list catego = gson.fromJson(finalObject.toString(), news_list.class);
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
        protected void onPostExecute(final List<news_list> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0)){
                SL_news_user_view.setVisibility(View.VISIBLE);
               MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.content_news__display, movieMode);
                SL_news_user_view.setAdapter(adapter);
                SL_news_user_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        news_list item = movieMode.get(position);
                        Intent intent = new Intent(getApplicationContext(),NewsDetails_Vendor.class);
                        intent.putExtra("news_v_id",item.getId());
                        intent.putExtra("news_v_userid",item.getUserId());
                        intent.putExtra("news_v_title",item.getTitle());
                        intent.putExtra("news_v_name",item.getName());
                        intent.putExtra("news_v_image",item.getImage());
                        intent.putExtra("news_v_typeid",item.getNewsTypeId());
                        intent.putExtra("news_v_url",item.getURL());
                        intent.putExtra("news_v_content",item.getContent());



                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }

        }
    }
    public void callmetodeletenews(String id)
    {
        try {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("Id", id);
        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_delete_news, new Response.Listener<String>() {
            public void onResponse(String response) {
                new PromptDialog(News_Display_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("News Deleted Successfully")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                startActivity(new Intent(News_Display_Vendor.this,News_Display_Vendor.class));
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

private boolean isConnectedToNetwork() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnected();
}
}