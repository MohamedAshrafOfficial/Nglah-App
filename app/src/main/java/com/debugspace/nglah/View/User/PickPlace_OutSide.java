package com.debugspace.nglah.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.debugspace.nglah.Database.DB;
import com.debugspace.nglah.R;
import com.debugspace.nglah.View.User_Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PickPlace_OutSide extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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

    DB dbobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_place__out_side);
        countrylist.add("- اختار -");
        countrylist.add("السعوديه");
//        city_send.add("جده");
//        city_send.add("مكه");
//        sector_send.add("الجنوب");
//        sector_send.add("الشمال");
//        cityrecive.add("الرياض");
//        sector_recive.add("الشمال");
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        initView();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countrylist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCountryout.setAdapter(adapter);

        spCountryout.setOnItemSelectedListener(this);
        spCitySend.setOnItemSelectedListener(this);
        spSectorSend.setOnItemSelectedListener(this);
        spCityRecive.setOnItemSelectedListener(this);
        spSectorRecive.setOnItemSelectedListener(this);

        spCitySend.setEnabled(false);
        spSectorSend.setEnabled(false);
        spCityRecive.setEnabled(false);
        spSectorRecive.setEnabled(false);
//        action();

    }


////    private void action(){
////
////        adapter = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_1, countrylist) {
////
////            @NonNull
////            @Override
////            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////                View view = super.getView(position, convertView, parent);
////                TextView tv = view.findViewById(android.R.id.text1);
////                tv.setTextColor(Color.WHITE);
////                return view;
////            }
////        };
////        spCountryout.setAdapter(adapter);
////        adaptercitysend = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_1, city_send) {
////
////            @NonNull
////            @Override
////            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////                View view = super.getView(position, convertView, parent);
////                TextView tv = view.findViewById(android.R.id.text1);
////                tv.setTextColor(Color.WHITE);
////                return view;
////            }
////        };
////        spCitySend.setAdapter(adaptercitysend);
////        adaptersectorsend = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_1, sector_send) {
////
////            @NonNull
////            @Override
////            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////                View view = super.getView(position, convertView, parent);
////                TextView tv = view.findViewById(android.R.id.text1);
////                tv.setTextColor(Color.WHITE);
////                return view;
////            }
////        };
////        spSectorSend.setAdapter(adaptersectorsend);
////        adaptercityrecive = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_1, cityrecive) {
////
////            @NonNull
////            @Override
////            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////                View view = super.getView(position, convertView, parent);
////                TextView tv = view.findViewById(android.R.id.text1);
////                tv.setTextColor(Color.WHITE);
////                return view;
////            }
////        };
////        spCityRecive.setAdapter(adaptercityrecive);
////        adaptersectorrecive = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_1, sector_recive) {
////
////            @NonNull
////            @Override
////            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////                View view = super.getView(position, convertView, parent);
////                TextView tv = view.findViewById(android.R.id.text1);
////                tv.setTextColor(Color.WHITE);
////                return view;
////            }
////        };
////        spSectorRecive.setAdapter(adaptersectorrecive);
////
////        imgBackRecent.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                startActivity(new Intent(PickPlace_OutSide.this, User_Main.class));
////                finish();
////            }
////        });
//
//    }

    private void initView() {

        dbobject = new DB(this);
        dbobject.openDataBase();

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
            startActivity(new Intent(this,PickElement.class));

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
                    ArrayList sectorsOutside = dbobject.getSectorsOutside();

                    ArrayAdapter<String> citySendAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, citiesOutside);

                    ArrayAdapter<String> sectorSendAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, removeRepeatedValues(sectorsOutside));

                    ArrayAdapter<String> cityReceiveAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, citiesOutside);

                    ArrayAdapter<String> sectorReceiveAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, removeRepeatedValues(sectorsOutside));

                    citySendAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectorSendAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cityReceiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectorReceiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spCitySend.setAdapter(citySendAdapter);
                    spSectorSend.setAdapter(sectorSendAdapter);
                    spCityRecive.setAdapter(cityReceiveAdapter);
                    spSectorRecive.setAdapter(sectorReceiveAdapter);

                    spCitySend.setEnabled(true);
                    spSectorSend.setEnabled(true);
                    spCityRecive.setEnabled(true);
                    spSectorRecive.setEnabled(true);
                }

                break;

            case R.id.sp_city_send:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_sector_send:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_city_recive:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_sector_recive:
                text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList removeRepeatedValues(ArrayList list){
        ArrayList arrayList = list;
        Set set = new HashSet(arrayList);
        arrayList.clear();
        arrayList.addAll(set);
        return arrayList;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
