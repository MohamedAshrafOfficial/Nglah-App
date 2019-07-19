package com.example.nglah.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nglah.R;
import com.example.nglah.View.Driver.Driver_Profile;
import com.example.nglah.View.User.InsideOrOutsideTown;
import com.example.nglah.View.User.User_Profile;

public class User_Main extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);
        sharedPreferences =getSharedPreferences("nglah_file",MODE_PRIVATE);
    }

    public void Talab_Naglah(View view) {

        if (sharedPreferences.getInt("user_type",0)==1){

            startActivity(new Intent(this, InsideOrOutsideTown.class));


        }else if (sharedPreferences.getInt("user_type",0)==2){


        }

    }

    public void Profile(View view) {

        if (sharedPreferences.getInt("user_type",0)==1){

            startActivity(new Intent(this, User_Profile.class));


        }else if (sharedPreferences.getInt("user_type",0)==2){

            startActivity(new Intent(this, Driver_Profile.class));

        }
    }

    public void Mahfazty(View view) {

        startActivity(new Intent(this, PaymentSystem.class));

    }

    public void EXIT(View view) {

        finish();
    }
}
