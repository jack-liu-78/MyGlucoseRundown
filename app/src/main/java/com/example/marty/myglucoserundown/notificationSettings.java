package com.example.marty.myglucoserundown;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;

public class notificationSettings extends AppCompatActivity {

    private ListView n_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        //creates the toolbar at the top of the screen/activity
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        n_listview = (ListView) findViewById(R.id.notificationListview);


        //used a SharedPreferences to check if a notification spot has been filled or not and that way even when the user closes
            //and restarts the app the activity will still load the same notification
        SharedPreferences sharedPref = getSharedPreferences("userNotifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        final String[] items = new String[] {sharedPref.getString("notification1", ""),
                                                sharedPref.getString("notification2", ""),
                                                        sharedPref.getString("notification3", ""),
                                                                sharedPref.getString("notification4", ""),
                                                                        sharedPref.getString("notification5", "")};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        //initializes the listview that the notifications are displayed on
        n_listview.setAdapter(adapter);

        //this method detects if a listview has been clicked
        n_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPref = getSharedPreferences("userNotifications", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                //if the notification text in the listview is blank that means that it will start the activity where the user can
                    //create a notification
                if(items[position].equalsIgnoreCase("")){
                    Intent intent = new Intent(getApplicationContext(), createNotification.class);
                    startActivity(intent);


                }
                //if the notification text is not a blank then that means a notification is already there and if the user clicke it will delete it
                else  {
                    editor.putString("notification" + (position +1), "");
                    editor.apply();
                    deleteNotification(position +1);
                    //recreate refreshes the notification settings activity once the notification is deleted
                    notificationSettings.this.recreate();
                }


            }
            });

            //this codes is more toolbar initialization
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if the back button on the toolbar is pressed it will take you back to the manage settings activity/screen
                        startActivity(new Intent(getApplicationContext(), manageSettings.class));

                    }
                });
    }

/*
    // Method can create a daily notification at the time you set

            public void notify(View v) {
                //NOTIFICATION CODE TO SEND A NOTIFICATION AT A SPECIFIC TIME AND REPEAT IT EVERY DAY
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, 18);
                calendar.set(Calendar.MINUTE, 20);
                calendar.set(Calendar.SECOND, 1);

                Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
/*
 // Method makes a one time notification at the current time
            public void getNotification(View view) {
                NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent intent = new Intent(getApplicationContext(), manageSettings.class);//set page to open on press of the notification
                PendingIntent pintent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                Notification notif = new Notification.Builder(getApplicationContext()).setSmallIcon(R.drawable.mgr_icon).setContentTitle("My Glucose Rundown").setContentText("Reminder to take your next Blood Sugar Reading!").setContentIntent(pintent).build();

                notificationmanager.notify(001, notif);


            }
            */



            //given the ID of the notification it will delete it and stop it from running daily
            public void deleteNotification(int notificationNum)
            {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent = new Intent(getApplicationContext(),
                        Notification_reciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(),notificationNum, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);
            }
        }

