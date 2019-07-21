package com.debugspace.nglah.View.Driver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debugspace.nglah.Model.DriverModel.LoginDriverService;
import com.debugspace.nglah.Model.hassan_now.Car_Model;
import com.debugspace.nglah.Model.hassan_now.Driver_Model;
import com.debugspace.nglah.Model.hassan_now.user_service;
import com.debugspace.nglah.R;
import com.debugspace.nglah.Services.JsonPlaceHolderApi;
import com.debugspace.nglah.View.PaymentSystem;
import com.debugspace.nglah.View.User_Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverInfo extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText national;
    private EditText dLicence;
    private EditText dFName;
    private EditText dSName;
    private EditText dLName;
    private EditText dNationalId;
    private EditText dPhone;
    private EditText dEmail;
    private EditText dCity;
    private EditText dRegion;
    private EditText dUsername;
    private EditText dPassword;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlertDialog.Builder alertDialog;
    ArrayList<Car_Model>car_list=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    ProgressDialog progressDialog;
    String txtCar_type,txtCar_Panal, txtCar_Weight, txtD_Car_Name, txtD_Car_City, txtD_Car_Region,old_n_id,car_image;
    ArrayList<String>car_type_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ematj.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        alertDialog=new AlertDialog.Builder(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("waiting...");

        car_type_list.add("اختر  سياره");
        car_type_list.add("ToyoTa");
        car_type_list.add("Carry");


        initView();
        getData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        national = (EditText) findViewById(R.id.national);
        dLicence = (EditText) findViewById(R.id.d_licence);
        dFName = (EditText) findViewById(R.id.d_f_name);
        dSName = (EditText) findViewById(R.id.d_s_name);
        dLName = (EditText) findViewById(R.id.d_l_name);
        dNationalId = (EditText) findViewById(R.id.d_national_id);
        dPhone = (EditText) findViewById(R.id.d_phone);
        dEmail = (EditText) findViewById(R.id.d_email);
        dCity = (EditText) findViewById(R.id.d_city);
        dRegion = (EditText) findViewById(R.id.d_region);
        dUsername = (EditText) findViewById(R.id.d_username);
        dPassword = (EditText) findViewById(R.id.d_password);
    }

    private void getData () {


        if (sharedPreferences.getString("setting","null").equals("update")){

        Call<user_service> call = jsonPlaceHolderApi.GetCar_Owner_Data(sharedPreferences.getString("email", "null"));
        call.enqueue(new Callback<user_service>() {
            @Override
            public void onResponse(Call<user_service> call, Response<user_service> response) {

                if (response.isSuccessful()) {
//
                    user_service user_service = response.body();
                    List<Driver_Model> driver_models = user_service.getDriver_model();

                    for (Driver_Model driver_model : driver_models) {
                        Toast.makeText(DriverInfo.this, ""+driver_model.getEmail(), Toast.LENGTH_SHORT).show();

                        national.setText(driver_model.getNationality());
                        dLicence.setText(driver_model.getDriver_l_id());
                        dFName.setText(driver_model.getF_name());
                        dSName.setText(driver_model.getSe_name());
                        dLName.setText(driver_model.getL_name());
                        dNationalId.setText(driver_model.getDriver_n_id());
                        dPhone.setText(driver_model.getPhone());
                        dEmail.setText(driver_model.getEmail());
                        dCity.setText(driver_model.getD_city());
                        dRegion.setText(driver_model.getD_region());
                        dUsername.setText(driver_model.getUsernmae());
                        dPassword.setText(driver_model.getPassword());

                        getCarData(driver_model.getOwner_name());
                        old_n_id=dNationalId.getText().toString();

                    }

                }

            }

            @Override
            public void onFailure(Call<user_service> call, Throwable t) {

            }
        });


        }else {

            dPhone.setText(sharedPreferences.getString("phone","null"));
            dEmail.setText(sharedPreferences.getString("email","null"));
            dUsername.setText(sharedPreferences.getString("username","null"));
            dPassword.setText(sharedPreferences.getString("password","null"));


        }



    }



    private void CreateDriver() {

        progressDialog.show();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("driver_national_id", dNationalId.getText().toString());
        parameters.put("driver_name", txtD_Car_Name);
        parameters.put("nationality", national.getText().toString());
        parameters.put("first_name", dFName.getText().toString());
        parameters.put("second_name", dSName.getText().toString());
        parameters.put("last_name", dLName.getText().toString());
        parameters.put("driver_license_id", dLicence.getText().toString());
        parameters.put("phone", dPhone.getText().toString());
        parameters.put("email", dEmail.getText().toString());
        parameters.put("user_name", dUsername.getText().toString());
        parameters.put("password", dPassword.getText().toString());
        parameters.put("car_type", txtCar_type);
        parameters.put("car_image", "https");
        parameters.put("panel_id", txtCar_Panal);
        parameters.put("allowed_weight", txtCar_Weight);
        parameters.put("owner_city", txtD_Car_City);
        parameters.put("owner_region", txtD_Car_Region);
        parameters.put("driver_city", dCity.getText().toString());
        parameters.put("driver_region", dRegion.getText().toString());


        Call<LoginDriverService> call = jsonPlaceHolderApi.CreateDriver(parameters);
        call.enqueue(new Callback<LoginDriverService>() {
            @Override
            public void onResponse(Call<LoginDriverService> call, Response<LoginDriverService> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(DriverInfo.this, "Check Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginDriverService service = response.body();

                if (service.isSuccess()){
                    progressDialog.dismiss();
                    startActivity(new Intent(DriverInfo.this, User_Main.class));
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DriverInfo.this, "خطأ فى التسجيل !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginDriverService> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DriverInfo.this, "Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Update_Driver() {

        progressDialog.show();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("driver_national_id", dNationalId.getText().toString());
        parameters.put("driver_name", txtD_Car_Name);
        parameters.put("nationality", national.getText().toString());
        parameters.put("first_name", dFName.getText().toString());
        parameters.put("second_name", dSName.getText().toString());
        parameters.put("last_name", dLName.getText().toString());
        parameters.put("driver_license_id", dLicence.getText().toString());
        parameters.put("phone", dPhone.getText().toString());
        parameters.put("email", dEmail.getText().toString());
        parameters.put("user_name", dUsername.getText().toString());
        parameters.put("password", dPassword.getText().toString());
        parameters.put("car_type", txtCar_type);
        parameters.put("car_image", car_image);
        parameters.put("panel_id", txtCar_Panal);
        parameters.put("allowed_weight", txtCar_Weight);
        parameters.put("owner_city", txtD_Car_City);
        parameters.put("owner_region", txtD_Car_Region);
        parameters.put("driver_city", dCity.getText().toString());
        parameters.put("driver_region", dRegion.getText().toString());
        parameters.put("old_driver_national_id", old_n_id);


        Call<Driver_Model> call = jsonPlaceHolderApi.Update_Driver(parameters);
        call.enqueue(new Callback<Driver_Model>() {
            @Override
            public void onResponse(Call<Driver_Model> call, Response<Driver_Model> response) {

                if (!response.isSuccessful()) {
                    progressDialog.dismiss();

                    Toast.makeText(DriverInfo.this, "faild", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog.dismiss();

                    Toast.makeText(DriverInfo.this, "success", Toast.LENGTH_SHORT).show();

                    return;
                }
            }

            @Override
            public void onFailure(Call<Driver_Model> call, Throwable t) {

                editor.putString("setting","");
                editor.commit();
                startActivity(new Intent(DriverInfo.this, User_Main.class));
                progressDialog.dismiss();

            }
        });

    }







    private void getCarData (final String name) {

        Call<user_service> call = jsonPlaceHolderApi.GetCar__Data(dNationalId.getText().toString());
        call.enqueue(new Callback<user_service>() {
            @Override
            public void onResponse(Call<user_service> call, Response<user_service> response) {

                if (response.isSuccessful()) {
//
                    user_service user_service = response.body();
                    List<Car_Model> car_models = user_service.getCar_model();

                    for (Car_Model car_model : car_models) {

                        txtCar_type=car_model.getCar_type();
                        txtCar_Panal=car_model.getPanal_id();
                        txtCar_Weight=car_model.getAllowed_weight();
                        txtD_Car_City=car_model.getDriver_car_city();
                        txtD_Car_Region=car_model.getDriver_car_region();
                        txtD_Car_Name=name;


                    }

                }

            }

            @Override
            public void onFailure(Call<user_service> call, Throwable t) {

            }
        });
    }


    public void NEXT(View view) {

     car_Info();
//        CreateDriver();

    }


    private void car_Info() {

         View user_Layout = LayoutInflater.from(DriverInfo.this).inflate(R.layout.activity_car_info, null);
         alertDialog.setView(user_Layout);
        alertDialog.setCancelable(false);
        car_list.clear();
        final Spinner Car_type = user_Layout.findViewById(R.id.car_type);
        final EditText Car_Panal = user_Layout.findViewById(R.id.panal_id);
        final EditText Car_Weight = user_Layout.findViewById(R.id.weight);
        final EditText D_Car_Name = user_Layout.findViewById(R.id.d_car_name);
        final EditText D_Car_City = user_Layout.findViewById(R.id.d_car_city);
        final EditText D_Car_Region = user_Layout.findViewById(R.id.d_car_region);
        final ImageView Car_image = user_Layout.findViewById(R.id.car_image);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, car_type_list) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        Car_type.setAdapter(adapter);

        Car_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    Car_image.setImageResource(R.drawable.toyota);
                    car_image="toyota";
                    Car_image.setVisibility(View.VISIBLE);

                }else if (position==2){
                    Car_image.setImageResource(R.drawable.carry);
                    car_image="carry";
                    Car_image.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Car_type.setText(txtCar_type);
        Car_Panal.setText(txtCar_Panal);
        Car_Weight.setText(txtCar_Weight);
        D_Car_Name.setText(txtD_Car_Name);
        D_Car_City.setText(txtD_Car_City);
        D_Car_Region.setText(txtD_Car_Region);


        alertDialog.setPositiveButton("Submit", null);
        alertDialog.setNegativeButton("Cancel", null);


        final AlertDialog mAlertDialog = alertDialog.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button Positive = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button Cancel = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);


                Positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Car_type.getSelectedItemPosition()==0) {
                            Toast.makeText(DriverInfo.this, "Car Type not entered", Toast.LENGTH_SHORT).show();
                            Car_type.requestFocus();
                        } else if (Car_Panal.getText().toString().isEmpty()) {
                            Car_Panal.setError("Car Panal not entered");
                            Car_Panal.requestFocus();
                        } else if (Car_Weight.getText().toString().isEmpty()) {
                            Car_Weight.setError("Car_Weight not entered");
                            Car_Weight.requestFocus();
                        } else if (D_Car_Name.getText().toString().isEmpty()) {
                            D_Car_Name.setError("Name not entered");
                            D_Car_Name.requestFocus();
                        } else if (D_Car_City.getText().toString().isEmpty()) {
                            D_Car_City.setError("Citr not entered");
                            D_Car_City.requestFocus();
                        } else if (D_Car_Region.getText().toString().isEmpty()) {
                            D_Car_Region.setError("Region not entered");
                            D_Car_Region.requestFocus();
                        } else {
//
//                            Car_Model car_model = new Car_Model();
//                            car_model.setCar_type(Car_type.getText().toString());
//                            car_model.setCar_image("https:");
//                            car_model.setPanal_id(Car_Panal.getText().toString());
//                            car_model.setAllowed_weight(Car_Weight.getText().toString());
//                            car_model.setDriver_car_name(D_Car_Name.getText().toString());
//                            car_model.setDriver_car_city(D_Car_City.getText().toString());
//                            car_model.setDriver_car_region(D_Car_Region.getText().toString());
//
//                            car_list.add(car_model);
                            txtCar_type=Car_type.getSelectedItem().toString();
                            txtCar_Panal=Car_Panal.getText().toString();
                            txtCar_Weight=Car_Weight.getText().toString();
                            txtD_Car_Name=D_Car_Name.getText().toString();
                            txtD_Car_City=D_Car_City.getText().toString();
                            txtD_Car_Region=D_Car_Region.getText().toString();


                            if (sharedPreferences.getString("setting","null").equals("update")){

                                Update_Driver();

                            }else {

                                CreateDriver();

                            }

                            dialog.dismiss();
                        }

                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


        mAlertDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (sharedPreferences.getString("setting","null").equals("update")){
            startActivity(new Intent(DriverInfo.this, User_Main.class));
            finish();

        }else {
            startActivity(new Intent(DriverInfo.this, PaymentSystem.class));
            finish();
        }

    }
}
