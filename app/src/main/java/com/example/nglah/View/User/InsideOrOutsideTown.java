package com.example.nglah.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nglah.R;

public class InsideOrOutsideTown extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_or_outside_town);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void Iside(View view) {

        editor.putString("nglah_type","Inside");
        editor.commit();

        startActivity(new Intent(this,PickPlace_Inside.class));


    }

    public void OutIside(View view) {

        editor.putString("nglah_type","OutSide");
        editor.commit();

        startActivity(new Intent(this,PickPlace_OutSide.class));


    }
}
