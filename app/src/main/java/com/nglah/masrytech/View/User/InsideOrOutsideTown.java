package com.nglah.masrytech.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nglah.masrytech.R;
import com.nglah.masrytech.View.User_Main;

public class InsideOrOutsideTown extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_or_outside_town);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        image_back=findViewById(R.id.imgBackRecent);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsideOrOutsideTown.this, User_Main.class));
                finish();
            }
        });
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
