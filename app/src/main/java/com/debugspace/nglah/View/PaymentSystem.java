package com.debugspace.nglah.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.debugspace.nglah.R;
import com.debugspace.nglah.View.Driver.DriverInfo;
import com.debugspace.nglah.View.User.UserInfo;

public class PaymentSystem extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_system);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void SKIP(View view) {

        if (sharedPreferences.getBoolean("mahfazty", false) == true) {
            editor.putBoolean("mahfazty", false);
            editor.commit();
            startActivity(new Intent(PaymentSystem.this, User_Main.class));
            finish();

        } else {


            int user_type = sharedPreferences.getInt("user_type", 0);
            if (user_type == 1) {
                startActivity(new Intent(this, UserInfo.class));
                finish();

            } else if (user_type == 2) {

                startActivity(new Intent(this, DriverInfo.class));
                finish();

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (sharedPreferences.getBoolean("mahfazty",false)==true){
            startActivity(new Intent(PaymentSystem.this,User_Main.class));
            finish();

        }else {

            startActivity(new Intent(PaymentSystem.this, SignUp.class));
            finish();
        }
    }
}
