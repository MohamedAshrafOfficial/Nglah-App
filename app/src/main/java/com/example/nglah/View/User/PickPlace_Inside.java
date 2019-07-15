package com.example.nglah.View.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nglah.Database.DB;
import com.example.nglah.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PickPlace_Inside extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerCountry, spinnerRegion, spinnerCity, spinnerSector;
    DB dbobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_place__inside);

        // init all widgets in this activity
        initWidgets();

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
    }


    public void initWidgets(){

        dbobject = new DB(this);

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
                Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, dbobject.getCities(String.valueOf(position+1)));

                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerCity.setAdapter(cityAdapter);

                spinnerCity.setEnabled(true);

                break;

            case R.id.sp_city:
                text = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_sector:
                text = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
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

}
