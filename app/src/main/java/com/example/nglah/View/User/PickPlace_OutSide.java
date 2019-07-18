package com.example.nglah.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nglah.R;

import java.util.ArrayList;

public class PickPlace_OutSide extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgBackRecent;
    private Spinner spCountryout;
    private Spinner spCitySend;
    private Spinner spSectorSend;
    private Spinner spCityRecive;
    private Spinner spSectorRecive;
    private EditText location;

    ArrayList<String>countrylist=new ArrayList<>();
    ArrayList<String>city_send=new ArrayList<>();
    ArrayList<String>sector_send=new ArrayList<>();
    ArrayList<String>cityrecive=new ArrayList<>();
    ArrayList<String>sector_recive=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adaptercitysend;
    private ArrayAdapter<String> adaptersectorsend;
    private ArrayAdapter<String> adaptercityrecive;
    private ArrayAdapter<String> adaptersectorrecive;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_place__out_side);
        countrylist.add("- اختار -");
        countrylist.add("السعوديه");
        city_send.add("جده");
        city_send.add("مكه");
        sector_send.add("الجنوب");
        sector_send.add("الشمال");
        cityrecive.add("الرياض");
        sector_recive.add("الشمال");
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();



        initView();
        action();

    }


    private void action(){

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, countrylist) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        spCountryout.setAdapter(adapter);
        adaptercitysend = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, city_send) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        spCitySend.setAdapter(adaptercitysend);
        adaptersectorsend = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sector_send) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        spSectorSend.setAdapter(adaptersectorsend);
        adaptercityrecive = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cityrecive) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        spCityRecive.setAdapter(adaptercityrecive);
        adaptersectorrecive = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sector_recive) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        spSectorRecive.setAdapter(adaptersectorrecive);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgBackRecent = (ImageView) findViewById(R.id.imgBackRecent);
        spCountryout = (Spinner) findViewById(R.id.sp_country_out);
        spCitySend = (Spinner) findViewById(R.id.sp_city_send);
        spSectorSend = (Spinner) findViewById(R.id.sp_sector_send);
        spCityRecive = (Spinner) findViewById(R.id.sp_city_recive);
        spSectorRecive = (Spinner) findViewById(R.id.sp_sector_recive);
        location = (EditText) findViewById(R.id.location);
    }


    public void next(View view) {
        if (spCountryout.getSelectedItemPosition()==0){

            Toast.makeText(this, "select country", Toast.LENGTH_SHORT).show();
        }else {
            editor.putString("country",spCountryout.getSelectedItem().toString());
            editor.putString("city_send",spCitySend.getSelectedItem().toString());
            editor.putString("sector_send",spSectorSend.getSelectedItem().toString());
            editor.putString("city_recive",spCityRecive.getSelectedItem().toString());
            editor.putString("secttor_recive",spSectorRecive.getSelectedItem().toString());
            editor.putString("location",location.getText().toString());

            editor.commit();
            startActivity(new Intent(this,PickTime.class));

        }


    }

}
