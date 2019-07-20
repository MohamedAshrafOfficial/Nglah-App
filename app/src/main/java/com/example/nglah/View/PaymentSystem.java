package com.example.nglah.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nglah.R;
import com.example.nglah.View.Driver.DriverInfo;
import com.example.nglah.View.User.UserInfo;

public class PaymentSystem extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_system);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);

    }

    public void SKIP(View view) {
        int user_type=sharedPreferences.getInt("user_type",0);
        if (user_type==1){
            startActivity(new Intent(this, UserInfo.class));
            finish();

        }else if (user_type==2){

            startActivity(new Intent(this, DriverInfo.class));
            finish();

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
