package com.weqar.weqar;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;

import com.weqar.weqar.Fragments.BotNav_DiscountsFragment;
import com.weqar.weqar.Fragments.BotNav_EventsFragment;
import com.weqar.weqar.Fragments.BotNav_JobsFragment;
import com.weqar.weqar.Fragments.BotNav_SettingsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeScreen extends AppCompatActivity {
    BottomBar bottomBar;
    ImageView IV_filter,IV_notification;
    EditText ET_search,ET_searchs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.botnav_event);
//        IV_filter=findViewById(R.id.homescreen_filter);
//        IV_notification=findViewById(R.id.homescreen_notification);
//        ET_search=findViewById(R.id.homescreen_search);
//        ET_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                ET_search.setWidth(400);
//
//                IV_filter.setVisibility(View.INVISIBLE);
//                IV_notification.setVisibility(View.INVISIBLE);
//            }
//        });
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                android.support.v4.app.Fragment selectedFragment = null;
                if (tabId == R.id.botnav_event) {
                    selectedFragment = BotNav_EventsFragment.newInstance();
                }
                else if (tabId == R.id.botnav_discount) {
                    selectedFragment = BotNav_DiscountsFragment.newInstance();

                }
                else if (tabId == R.id.botnav_jobs) {
                    selectedFragment = BotNav_JobsFragment.newInstance();

                }

                else if (tabId == R.id.botnav_settings) {
                    selectedFragment = BotNav_SettingsFragment.newInstance();

                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, selectedFragment);
                transaction.commit();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
