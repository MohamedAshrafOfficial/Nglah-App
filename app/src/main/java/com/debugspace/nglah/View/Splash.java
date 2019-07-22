package com.debugspace.nglah.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.debugspace.nglah.R;


/**
 * Created by Butcher on 28/08/2017.
 */

public class Splash extends Activity {

    int timeSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //  new Handler().postDelayed(new Runnable() {
        Thread splash_screen = new Thread() {
            //for not moving to any activity after 3000
            @Override
            public void run() {
                // This method will be executed once the timer is over
                timeSec = 4000;
                // Start your app Main activity
                try {
                    sleep(timeSec);

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {

                    // it must return to Main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Intent intent = new Intent(Splash.this, SignIn.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                }
            }
        };

        splash_screen.start();


    }

}

