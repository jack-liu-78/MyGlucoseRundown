package com.example.marty.myglucoserundown;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class homeScreen extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton floatButton;
    int level;
    double avg;
    boolean ran;
    TextView weekAvg;
    int prevWeekLevel;
    double weekAvgDouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences sharedPrefINFO = getSharedPreferences("userInfo", 0);

        TextView out = (TextView) findViewById(R.id.avgView);
        TextView weekAvg = (TextView) findViewById(R.id.lastWeekAvg);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);

        String name = sharedPrefINFO.getString("settingUpName", "");
        nameTextView.setText("Welcome " + name);



        SharedPreferences shareRan = getSharedPreferences("running",0);
        SharedPreferences.Editor edit = shareRan.edit();
        ran = shareRan.getBoolean("running",false);
        SharedPreferences sharedPref = getSharedPreferences("bloodGlucoseLevel", 0);
        SharedPreferences.Editor editor =sharedPref.edit();
        if(!ran) {
            editor.putInt("levelInput", 5);
            editor.commit();
            edit.putBoolean("running",true);
            edit.commit();
        }




        level = sharedPref.getInt("overallAvg",0);
        avg = (double) level;
        avg /= 100;
        out.setText(Double.toString(avg));

        prevWeekLevel = sharedPref.getInt("lastWeekAvg",0);
        weekAvgDouble = (double) prevWeekLevel;
        weekAvgDouble /= 100;
        weekAvg.setText(Double.toString(weekAvgDouble));

        /*
        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), addReading.class);
                startActivity(i);
                */
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_homeScreen) {
            this.startActivity(new Intent(getApplicationContext(), homeScreen.class));

        }  else if (id == R.id.nav_manageSettings) {
            this.startActivity(new Intent(getApplicationContext(), manageSettings.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true; }



                public void readingButton (View view) {
                    Intent intent = new Intent(getApplicationContext(), addReading.class);
                    startActivity(intent);
                }

                }


