package com.example.marty.myglucoserundown;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class setupPage extends AppCompatActivity {

    EditText setupName;
    EditText ageInput;
    EditText genderInput;
    public int age;
    Spinner spinner1;
    ArrayAdapter<CharSequence> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_page);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        setupName = (EditText) findViewById(R.id.setupName);
        ageInput = (EditText) findViewById(R.id.ageInput);


        adapter = ArrayAdapter.createFromResource(this, R.array.GENDERS,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        //get program to see what item in the spinner that was selected
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //don't need to show the user a toast of their selection as they can see it on the screen already
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //just some starter learning code that I used to change between activities through buttons
    public void returnButtonMethod(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    //method takes all the user inputs checks for errors in their entries and if none stores their info into shared preferences
    public void saveSetupInfo(View view){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("settingUpName",setupName.getText().toString());
        editor.putInt("ageInput",age);
        editor.putString("gender",spinner1.getSelectedItem().toString());
        if(setupName.getText().toString().trim().equals("")) {

            Toast.makeText(this, "Fill in Required Fields", Toast.LENGTH_SHORT).show();
            setupName.setError("Name is Required");
        }
        else if(ageInput.getText().toString().trim().equals("")) {

            Toast.makeText(this, "Fill in Required Fields", Toast.LENGTH_SHORT).show();
            ageInput.setError("Age is Required");
        }

        else if(spinner1.getSelectedItem().toString().equals("Select")) {

            Toast.makeText(this, "Fill in Required Fields", Toast.LENGTH_SHORT).show();
            genderInput.setError("Gender is Required");
        }
        else {
            editor.apply();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, homeScreen.class);
            startActivity(intent);

        }
        }
    }


/*
    //Printout saved info
    public void displayData (View view){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String setupName = sharedPref.getString("settingUpName", "");
        String name = sharedPref.getString("username", ""); //Send username into as a perameter. Second "" is the return value
        String pass = sharedPref.getString("password", "");
        textOut.setText(name + " " + pass);
    }
*/