package com.debugspace.nglah.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.debugspace.nglah.R;
import com.debugspace.nglah.View.Driver.DriverOrdersActivity;
import com.debugspace.nglah.View.Driver.Driver_Profile;
import com.debugspace.nglah.View.User.DriversList;
import com.debugspace.nglah.View.User.InsideOrOutsideTown;
import com.debugspace.nglah.View.User.User_Profile;

public class User_Main extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);
        sharedPreferences =getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        button = findViewById(R.id.bt_order);

        if (sharedPreferences.getBoolean("flag_t",false)==true){
            button.setText("قائمة الطلبات");
        }
    }


    public void Talab_Naglah(View view) {

        if (sharedPreferences.getBoolean("flag_t", false) == true) {
            startActivity(new Intent(User_Main.this, DriversList.class));
            finish();
        } else {


            if (sharedPreferences.getInt("user_type", 0) == 1) {

                startActivity(new Intent(this, InsideOrOutsideTown.class));


            } else if (sharedPreferences.getInt("user_type", 0) == 2) {

                startActivity(new Intent(this, DriverOrdersActivity.class));


            }
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

        editor.putBoolean("mahfazty",true);
        editor.commit();

        startActivity(new Intent(this, PaymentSystem.class));

    }

    public void EXIT(View view) {

        startActivity(new Intent(this, SignIn.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, SignIn.class));
        finish();

    }
}
