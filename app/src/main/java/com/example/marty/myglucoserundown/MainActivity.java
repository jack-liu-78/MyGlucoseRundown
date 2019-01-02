package com.example.marty.myglucoserundown;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", false);

        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(MainActivity.this, homeScreen.class));

            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", true).commit();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        }


    public void type1ButtonMethod(View view) {
        diabetesType("type 1");
    }

    public void type2ButtonMethod(View view) { diabetesType("type 2");}

    public void diabetesType(String type) {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putString("type of Diabetes", type);
        editor.apply();

        String temp = sharedPref.getString("type of Diabetes", "");

        TextView tempResult = (TextView) findViewById(R.id.tempResult);
        tempResult.setText(temp);

        Intent intent =  new Intent(getApplicationContext(), setupPage.class);
        startActivity(intent);

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", true).commit();

    }


}

