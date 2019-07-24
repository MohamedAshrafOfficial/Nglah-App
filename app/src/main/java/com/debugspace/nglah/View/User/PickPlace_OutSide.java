package com.debugspace.nglah.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.debugspace.nglah.Database.DB;
import com.debugspace.nglah.R;
import com.debugspace.nglah.View.User_Main;

import java.util.ArrayList;


public class PickPlace_OutSide extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spCountryout;
    private Spinner spCitySend;
    private Spinner spCityRecive;
    private EditText location;

    ArrayList<String>countrylist=new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    DB dbobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_place__out_side);
        countrylist.add("- اختار -");
        countrylist.add("السعوديه");

        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        initView();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countrylist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCountryout.setAdapter(adapter);

        spCountryout.setOnItemSelectedListener(this);
        spCitySend.setOnItemSelectedListener(this);
        spCityRecive.setOnItemSelectedListener(this);

        spCitySend.setEnabled(false);
        spCityRecive.setEnabled(false);
    }


    private void initView() {

        dbobject = new DB(this);
        dbobject.openDataBase();

        spCountryout = (Spinner) findViewById(R.id.sp_country_out);
        spCitySend = (Spinner) findViewById(R.id.sp_city_send);
        spCityRecive = (Spinner) findViewById(R.id.sp_city_recive);
        location = (EditText) findViewById(R.id.location);
    }


    public void next(View view) {
        if (spCountryout.getSelectedItemPosition()==0){

            Toast.makeText(this, "select country", Toast.LENGTH_SHORT).show();
        }else {
            if (!"- اختار -".equals(spCountryout.getSelectedItem().toString()) &&
                    !"- اختار -".equals(spCitySend.getSelectedItem().toString()) &&
                    !"- اختار -".equals(spCityRecive.getSelectedItem().toString())) {
                editor.putString("country", spCountryout.getSelectedItem().toString());
                editor.putString("city_send", spCitySend.getSelectedItem().toString());
                editor.putString("city_recive", spCityRecive.getSelectedItem().toString());
                editor.putString("location", location.getText().toString());

                editor.commit();
                startActivity(new Intent(this, PickElement.class));
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PickPlace_OutSide.this, User_Main.class));
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        switch (adapterView.getId()){

            case R.id.sp_country_out:
                String text = adapterView.getItemAtPosition(position).toString();
                // Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();

                if (!"- اختار -".equals(text)){

                    ArrayList citiesOutside = dbobject.getCitiesOutside();

                    ArrayAdapter<String> citySendAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, citiesOutside);

                    ArrayAdapter<String> cityReceiveAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, citiesOutside);

                    citySendAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cityReceiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spCitySend.setAdapter(citySendAdapter);
                    spCityRecive.setAdapter(cityReceiveAdapter);

                    spCitySend.setEnabled(true);
                    spCityRecive.setEnabled(true);
                }

                break;

            case R.id.sp_city_send:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_city_recive:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
