package com.example.marty.myglucoserundown;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class createNotification extends AppCompatActivity {
    EditText hourEditText;
    EditText minuteEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        //declaring the text fields that are editable
        hourEditText = (EditText) findViewById(R.id.hourEditText);
        minuteEditText = (EditText) findViewById(R.id.minuteEditText);
    }


    // this determines where is the most availbe blank spot to put the notification that will be created
    public int determineNotificationSlot() {

        SharedPreferences sharedPref = getSharedPreferences("userNotifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i = 1; i <= 5; i++) {
            if (sharedPref.getString("notification" + i, "").equalsIgnoreCase("")) {
                return i;
            }
        }
        return 6;
    }

    //this method actually creates the notification but also detects if the user enters any invalid inputs into the text field
    public void notify(View v) {
        //NOTIFICATION CODE TO SEND A NOTIFICATION AT A SPECIFIC TIME AND REPEAT IT EVERY DAY
        Calendar calendar = Calendar.getInstance();

        //if the fields are blank display appropriate errors
        if (hourEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in Required Fields", Toast.LENGTH_SHORT).show();
            hourEditText.setError("Hour is Required");
        }
        else if (minuteEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in Required Fields", Toast.LENGTH_SHORT).show();
            minuteEditText.setError("Minute is Required");
        }
        //if the numbers entered are outside of the 24 hour and 60 minute range dsiplay appropriate error
        else if(parseInt(hourEditText.getText().toString())<= 0 || parseInt(hourEditText.getText().toString()) > 24 )
        {

            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            hourEditText.setError("Hour must be between 1 to 24");
        }
        else if(parseInt(minuteEditText.getText().toString()) <0 || parseInt(minuteEditText.getText().toString())>=60)
        {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            minuteEditText.setError("Minute must be between 0 to 59");
        }
        else
        {
            //once all the errors have been checked set the time of the notification using the user inputs
            calendar.set(Calendar.HOUR_OF_DAY, parseInt(hourEditText.getText().toString()));
            calendar.set(Calendar.MINUTE, parseInt(minuteEditText.getText().toString()));
            calendar.set(Calendar.SECOND, 1);

            //variable/number representing which ID/position this new notification will have
            int notificationFilled = determineNotificationSlot();
            //changes the listview SharedPreference that is displayed in the notification settings so that it is now filled
            SharedPreferences sharedPref = getSharedPreferences("userNotifications", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if(parseInt(minuteEditText.getText().toString()) < 10) {
                editor.putString("notification" + notificationFilled, parseInt(hourEditText.getText().toString()) + ":0" + parseInt(minuteEditText.getText().toString()));
            }
            else
            {
                editor.putString("notification" + notificationFilled, parseInt(hourEditText.getText().toString()) + ":" + parseInt(minuteEditText.getText().toString()));
            }
            editor.apply();


            //daily notification is created with the appropriate ID (notificationFilled is the ID/position in the listview)
            Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationFilled, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            this.startActivity(new Intent(getApplicationContext(), notificationSettings.class));
        }
    }
}
