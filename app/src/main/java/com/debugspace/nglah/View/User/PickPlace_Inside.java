package com.debugspace.nglah.View.User;

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

import com.debugspace.nglah.Database.DB;
import com.debugspace.nglah.R;
import com.debugspace.nglah.View.User_Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PickPlace_Inside extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerCountry, spinnerRegion, spinnerCity, spinnerSector;
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
        spinnerSector.setOnItemSelectedListener(this);

        spinnerRegion.setEnabled(false);
        spinnerCity.setEnabled(false);
        spinnerSector.setEnabled(false);
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
        spinnerSector = findViewById(R.id.sp_sector);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        switch (adapterView.getId()){

            case R.id.sp_country:
                String text = adapterView.getItemAtPosition(position).toString();
               // Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                editor.putString("country",text);
                editor.commit();


                if (!"- اختار -".equals(text)){

                    Map<String, ArrayList> data = dbobject.getRegionsWithSectors(String.valueOf(position));

                    ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, data.get("regionList"));

                    ArrayAdapter<String> sectorAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, removeRepeatedValues(data.get("sectorList")));

                    regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinnerRegion.setAdapter(regionAdapter);
                    spinnerSector.setAdapter(sectorAdapter);

                    spinnerRegion.setEnabled(true);
                    spinnerSector.setEnabled(true);
                }

                break;

            case R.id.sp_region:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                editor.putString("region",text);
                editor.commit();
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, dbobject.getCities(String.valueOf(position+1)));

                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerCity.setAdapter(cityAdapter);

                spinnerCity.setEnabled(true);

                break;

            case R.id.sp_city:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                editor.putString("city",text);
                editor.commit();
                break;

            case R.id.sp_sector:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                editor.putString("sector",text);
                editor.commit();
                break;

            default:
                Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private ArrayList removeRepeatedValues(ArrayList list){
        ArrayList arrayList = list;
        Set set = new HashSet(arrayList);
        arrayList.clear();
        arrayList.addAll(set);
        return arrayList;
    }

//    public void addData() {
//        boolean status = dbobject.insertDate("ejvbvjkb", "jkbfkjcb");
//        if (status == true) {
//            Toast.makeText(this, "Adding data is Done Successfully .", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Adding data is failed ! ", Toast.LENGTH_SHORT).show();
//        }
//    }

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
