package com.weqar.weqar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.weqar.weqar.JobDetails;
import com.weqar.weqar.R;

import java.util.List;


public class BotNav_JobsFragment_Vendor extends Fragment {
    SwipeMenuListView swipeMenuListView_jobs;
    public static BotNav_JobsFragment_Vendor newInstance() {
        BotNav_JobsFragment_Vendor fragment= new BotNav_JobsFragment_Vendor();
        return fragment;
    }
    private List<ApplicationInfo> mAppList;
    private BotNav_JobsFragment_Vendor.AppAdapter mAdapter;
    CardView card_jobs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__jobs_fragment__vendor, container, false);

        mAppList = getActivity().getPackageManager().getInstalledApplications(0);

        SwipeMenuListView listView = (SwipeMenuListView) view.findViewById(R.id.weqar_jobs);
        mAdapter = new BotNav_JobsFragment_Vendor.AppAdapter();
        listView.setAdapter(mAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                    case 2:
                        createMenu3(menu);
                        break;

                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getActivity());
                item1.setBackground(R.color.colorHints);
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.jobs_more);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getActivity());
                item2.setBackground(R.color.colorPrimary);

                item2.setWidth(dp2px(90));

                item2.setIcon(R.drawable.jobs_archive);



                menu.addMenuItem(item2);
            }

            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getActivity());
                item1.setBackground(R.color.colorHints);
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.jobs_more);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getActivity());
                item2.setBackground(R.color.colorPrimary);

                item2.setWidth(dp2px(90));

                item2.setIcon(R.drawable.jobs_archive);

                menu.addMenuItem(item2);
            }

            private void createMenu3(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getActivity());
                item1.setBackground(R.color.colorHints);
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.jobs_more);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getActivity());
                item2.setBackground(R.color.colorPrimary);

                item2.setWidth(dp2px(90));

                item2.setIcon(R.drawable.jobs_archive);

                menu.addMenuItem(item2);
            }
        };
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
//					delete(item);
                        mAppList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        return view;
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            // menu type count
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            // current menu type
            return position % 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(),
                        R.layout.item_list_app, null);
                new  BotNav_JobsFragment_Vendor.AppAdapter.ViewHolder(convertView);
            }
            BotNav_JobsFragment.AppAdapter.ViewHolder holder = (BotNav_JobsFragment.AppAdapter.ViewHolder) convertView.getTag();
            ApplicationInfo item = getItem(position);
            holder.tv_name.setText(item.loadLabel(getActivity().getPackageManager()));

            card_jobs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(),JobDetails.class));
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                card_jobs=view.findViewById(R.id.cardview_jobs);
                view.setTag(this);
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
