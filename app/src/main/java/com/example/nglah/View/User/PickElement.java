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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nglah.R;

import java.util.ArrayList;

public class PickElement extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgBackRecent;
    private Spinner car;
    private Spinner asas;
    private Spinner building;
    private Spinner other;
    ArrayList<String>carlist=new ArrayList<>();
    ArrayList<String>asaslist=new ArrayList<>();
    ArrayList<String>buldinglist=new ArrayList<>();
    ArrayList<String>otherlist=new ArrayList<>();

    private ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_element);
        initView();
        action();
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        carlist.add("dyna");
        asaslist.add("كنبه");
        buldinglist.add("حجاره");
        otherlist.add("شئ اخر");
    }

    public void Submit(View view) {

        editor.putString("car","car");
//        editor.putString("asas",asas.getSelectedItem().toString());
//        editor.putString("bulding",building.getSelectedItem().toString());
//        editor.putString("other",other.getSelectedItem().toString());
        editor.commit();
        startActivity(new Intent(this,PickTime.class));

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgBackRecent = (ImageView) findViewById(R.id.imgBackRecent);
        car = (Spinner) findViewById(R.id.car);
        asas = (Spinner) findViewById(R.id.asas);
        building = (Spinner) findViewById(R.id.building);
        other = (Spinner) findViewById(R.id.other);
    }

    private void action(){


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, carlist) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        car.setAdapter(adapter);


//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, asaslist) {
//
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView tv = view.findViewById(android.R.id.text1);
//                tv.setTextColor(Color.WHITE);
//                return view;
//            }
//        };
      //  asas.setAdapter(adapter);

//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, buldinglist) {
//
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView tv = view.findViewById(android.R.id.text1);
//                tv.setTextColor(Color.WHITE);
//                return view;
//            }
//        };
      //  building.setAdapter(adapter);

//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, otherlist) {
//
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView tv = view.findViewById(android.R.id.text1);
//                tv.setTextColor(Color.WHITE);
//                return view;
//            }
//        };
       // other.setAdapter(adapter);



    }
}
