package com.nglah.masrytech.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nglah.masrytech.R;
import com.nglah.masrytech.View.Driver.DriverOrdersActivity;
import com.nglah.masrytech.View.Driver.Driver_Profile;
import com.nglah.masrytech.View.User.DriversList;
import com.nglah.masrytech.View.User.InsideOrOutsideTown;
import com.nglah.masrytech.View.User.User_Profile;

public class User_Main extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Toolbar toolbar;

    private Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);
        sharedPreferences =getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        button = findViewById(R.id.bt_order);

        if (sharedPreferences.getInt("user_type", 0) == 1){

            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.yello));

        }
    }


    public void Talab_Naglah(View view) {

        if (sharedPreferences.getInt("user_type", 0) == 2){
            startActivity(new Intent(User_Main.this, DriverOrdersActivity.class));
            finish();
        }else {
            startActivity(new Intent(User_Main.this, InsideOrOutsideTown.class));
            finish();
//            if (sharedPreferences.getBoolean("flag_t", false) == true) {
//                startActivity(new Intent(User_Main.this, DriversList.class));
//                finish();
//            } else {
//
//                startActivity(new Intent(this, InsideOrOutsideTown.class));
//                finish();
//            }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.driver_list_id) {
            startActivity(new Intent(User_Main.this, DriversList.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, SignIn.class));
        finish();

    }
}
