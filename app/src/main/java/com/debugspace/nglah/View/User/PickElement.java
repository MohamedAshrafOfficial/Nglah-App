package com.debugspace.nglah.View.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debugspace.nglah.R;

import java.util.ArrayList;

public class PickElement extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgBackRecent;
    private Spinner car;
    private Spinner asas;
    private Spinner building;
    private Spinner other;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterasas;
    private ArrayAdapter<String> adapterbulding;
    private ArrayAdapter<String> adapterother;
    ArrayList<String>carlist=new ArrayList<>();
    ArrayList<String>asaslist=new ArrayList<>();
    ArrayList<String>buldinglist=new ArrayList<>();
    ArrayList<String>otherlist=new ArrayList<>();

    boolean flag=false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_element);

        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        carlist.add("اختر سياره");
        carlist.add("سياره صغيره");
        carlist.add("سياره كبيره");
        carlist.add("المزيد");
        asaslist.add("اختر اثاث");
        asaslist.add("كنبه");
        asaslist.add("سرير");
        asaslist.add("المزيد");
        buldinglist.add("اختر مواد بناء");
        buldinglist.add("مواد بناء");
        buldinglist.add("المزيد");
        otherlist.add("- شئ اخر -");
        otherlist.add("ادخال الشئ");
        initView();
        action();
    }

    public void Submit(View view) {

        if (flag==false){

            Toast.makeText(this, "اختر عنصر", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(this,PickTime.class));
        }

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

        imgBackRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickElement.this,InsideOrOutsideTown.class));
                finish();
            }
        });

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

        car.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    asas.setVisibility(View.VISIBLE);
                    building.setVisibility(View.VISIBLE);
                    other.setVisibility(View.VISIBLE);
                    flag=false;

                }else if(i==carlist.size()-1){
                    showAlertDialog();
                } else {
                    editor.putString("thing_type_elment",car.getSelectedItem().toString());
                    editor.commit();
                    asas.setVisibility(View.GONE);
                    building.setVisibility(View.GONE);
                    other.setVisibility(View.GONE);
                    flag=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapterasas = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, asaslist) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        asas.setAdapter(adapterasas);

        asas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    car.setVisibility(View.VISIBLE);
                    building.setVisibility(View.VISIBLE);
                    other.setVisibility(View.VISIBLE);
                    flag=false;

                }else if(i==asaslist.size()-1){
                    showAlertDialog();
                }else {
                    editor.putString("thing_type_elment",asas.getSelectedItem().toString());
                    car.setVisibility(View.GONE);
                    building.setVisibility(View.GONE);
                    other.setVisibility(View.GONE);
                    flag=true;
                    editor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        adapterbulding = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, buldinglist) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        building.setAdapter(adapterbulding);

        building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    car.setVisibility(View.VISIBLE);
                    asas.setVisibility(View.VISIBLE);
                    other.setVisibility(View.VISIBLE);
                    flag=false;

                }else if(i==buldinglist.size()-1){
                    showAlertDialog();
                }else {
                    editor.putString("thing_type_elment",building.getSelectedItem().toString());
                    car.setVisibility(View.GONE);
                    asas.setVisibility(View.GONE);
                    other.setVisibility(View.GONE);
                    flag=true;
                    editor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapterother = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, otherlist) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        other.setAdapter(adapterother);


        other.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {

                    showAlertDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void showAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PickElement.this);
        final EditText editText = new EditText(PickElement.this);
        builder.setView(editText);
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                editor.putString("thing_type_elment", editText.getText().toString());
                editor.commit();
                flag = true;
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                flag = false;
                dialogInterface.dismiss();
            }
        });

        builder.show();
        if (editText.getText().toString().isEmpty()){
            flag=false;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PickElement.this,InsideOrOutsideTown.class));
        finish();
    }
}
