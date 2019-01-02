package com.example.marty.myglucoserundown;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addReading extends AppCompatActivity {

    EditText bloodG;
    private double levelInput;
    private double levelWeekInput;
    private int levelInputInt;
    private int levelWeekInputInt;
    TextView day;
;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading);




        SharedPreferences pref = getSharedPreferences("bloodGlucoseLevel", MODE_PRIVATE); //get shared preference file
        SharedPreferences.Editor editor = pref.edit(); //set up an editor



        //Getting the calendar to check the current week
        Calendar cal = Calendar.getInstance();//create a new instance of the calendar
        int currentWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);//get the current week of the year

        //get what the week of the year is that has been saved to the preferences
        int weekOfYear = pref.getInt("weekOfYear",0);

        /*
         *compare the current week of the year to the week of year saved in the shared preferences
         *and if they have a different number, it goes into the if statement and change the week
         *saved in the preferences to a new week and save the average for the previous week
         */


        if(weekOfYear != currentWeekOfYear){
            editor.putInt("weekOfYear", currentWeekOfYear);
            int lastWeekAverage = pref.getInt("weekAvg",0);//get the weekly average from last week
            editor.putInt("weekAvg",0); //reset the weekly average value in the preferences back to 0 to start the new week
            editor.putInt("lastWeekAvg",lastWeekAverage); //put the average from last week into the shared preference to be displayed later
            editor.putInt("entryNumWeek", 1);
            editor.commit();//commit the values to memory
        }




        bloodG = (EditText) findViewById((R.id.inReading));
        day = (TextView) findViewById(R.id.dateView);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveNewReading(View view) {

        //running total average
        String val = bloodG.getText().toString();//get the value that is inside the input text box
        levelInput = Double.parseDouble(val); //turn input into a double
        levelWeekInput =  Double.parseDouble(val); //turn input into a double


        SharedPreferences pref = getSharedPreferences("bloodGlucoseLevel", MODE_PRIVATE); //get shared preference file
        SharedPreferences.Editor editor = pref.edit(); //set up an editor
        int entry = pref.getInt("entryNum", 1); //get the entry number
        int entryWeek = pref.getInt("entryNumWeek", 1); //get the entry number


        double prevInput = pref.getInt("overallAvg", 0); //get previous input
        double prevWeeklyInput = pref.getInt("weekAvg",0);
        levelInput += ((prevInput / 100) * (entry - 1));//get decimal places back and add to new input
        levelInput /= entry  ;//get the average
        levelInput *= 100;//remove all decimals
        levelInputInt = (int) levelInput;//turn into an integer

        levelWeekInput += ((prevWeeklyInput / 100) * (entryWeek -1));//get decimal places back and add to new input
        levelWeekInput /= (entryWeek);//get the average
        levelWeekInput *= 100;//remove all decimals
        levelWeekInputInt = (int) levelWeekInput;//turn into an integer


        editor.putInt("overallAvg", levelInputInt);//put integer into shared preference array
        editor.putInt("weekAvg",levelWeekInputInt);//put integer into shared preference array
        entry++;//add 1 to entry
        entryWeek++;//add 1 to the entry
        editor.putInt("entryNum", entry);//save the entry number
        editor.putInt("entryNumWeek", entryWeek);//save the entry number
        editor.commit();//commit all values to memory

        //make a new toast message which is that grey oval that comes at the bottom of the screen and tells you a message
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), homeScreen.class);
        startActivity(i);
    }
}




