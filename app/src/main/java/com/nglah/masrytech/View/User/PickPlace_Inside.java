package com.nglah.masrytech.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nglah.masrytech.Database.DB;
import com.nglah.masrytech.R;
import com.nglah.masrytech.View.User_Main;

import java.util.ArrayList;
import java.util.Map;

public class PickPlace_Inside extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerCountry, spinnerRegion, spinnerCity;
    DB dbobject;
    private ImageView imageView;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_place__inside);

        // init all widgets in this activity
        initWidgets();
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("- اختار -");
        arrayList.add("السعوديه");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        spinnerCountry.setOnItemSelectedListener(this);
        spinnerRegion.setOnItemSelectedListener(this);
        spinnerCity.setOnItemSelectedListener(this);

        spinnerRegion.setEnabled(false);
        spinnerCity.setEnabled(false);

        imageView=findViewById(R.id.imgBackRecent);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickPlace_Inside.this, User_Main.class));
                finish();
            }
        });

    }


    public void initWidgets(){

        dbobject = new DB(this);
        dbobject.openDataBase();

        spinnerCountry = findViewById(R.id.sp_country);
        spinnerRegion = findViewById(R.id.sp_region);
        spinnerCity = findViewById(R.id.sp_city);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        switch (adapterView.getId()){

            case R.id.sp_country:
                String text = adapterView.getItemAtPosition(position).toString();
               // Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();

                if (!"- اختار -".equals(text)){

                    editor.putString("country",text);
                    editor.commit();

                    Map<String, ArrayList> data = dbobject.getRegionsWithSectors(String.valueOf(position));

                    ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, data.get("regionList"));

                    regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinnerRegion.setAdapter(regionAdapter);

                    spinnerRegion.setEnabled(true);
                }

                break;

            case R.id.sp_region:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                if (!"- اختار -".equals(text)) {

                    editor.putString("region", text);
                    editor.commit();

                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, dbobject.getCities(String.valueOf(position)));

                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinnerCity.setAdapter(cityAdapter);

                    spinnerCity.setEnabled(true);

                }

                break;

            case R.id.sp_city:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();

                if (!"- اختار -".equals(text)) {
                    editor.putString("city", text);
                    editor.commit();
                }
                break;

            default:
                Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void GO(View view) {
        startActivity(new Intent(PickPlace_Inside.this,PickElement.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PickPlace_Inside.this, User_Main.class));
        finish();
    }
}
