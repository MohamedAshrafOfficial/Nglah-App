package com.debugspace.nglah.View.Driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.debugspace.nglah.Model.hassan_now.Car_Model;
import com.debugspace.nglah.Model.hassan_now.Driver_Model;
import com.debugspace.nglah.Model.hassan_now.user_service;
import com.debugspace.nglah.R;
import com.debugspace.nglah.Services.JsonPlaceHolderApi;
import com.debugspace.nglah.View.SignIn;
import com.debugspace.nglah.View.User.UserInfo;
import com.debugspace.nglah.View.User.User_Profile;
import com.debugspace.nglah.View.User_Main;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Driver_Profile extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView national;
    private TextView dLicence;
    private TextView dFName;
    private TextView dSName;
    private TextView dLName;
    private TextView dNationalId;
    private TextView dPhone;
    private TextView dEmail;
    private TextView dCity;
    private TextView dRegion;
    private TextView dUsername;
    private TextView dPassword;
    private TextView carType;
    private TextView panalId;
    private TextView weight;
    private TextView dCarName;
    private TextView dCarCity;
    private TextView dCarRegion;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__profile);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ematj.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        initView();
        getData();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.yello));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        national = (TextView) findViewById(R.id.national);
        dLicence = (TextView) findViewById(R.id.d_licence);
        dFName = (TextView) findViewById(R.id.d_f_name);
        dSName = (TextView) findViewById(R.id.d_s_name);
        dLName = (TextView) findViewById(R.id.d_l_name);
        dNationalId = (TextView) findViewById(R.id.d_national_id);
        dPhone = (TextView) findViewById(R.id.d_phone);
        dEmail = (TextView) findViewById(R.id.d_email);
        dCity = (TextView) findViewById(R.id.d_city);
        dRegion = (TextView) findViewById(R.id.d_region);
        dUsername = (TextView) findViewById(R.id.d_username);
        dPassword = (TextView) findViewById(R.id.d_password);
        carType = (TextView) findViewById(R.id.car_type);
        panalId = (TextView) findViewById(R.id.panal_id);
        weight = (TextView) findViewById(R.id.weight);
        dCarName = (TextView) findViewById(R.id.d_car_name);
        dCarCity = (TextView) findViewById(R.id.d_car_city);
        dCarRegion = (TextView) findViewById(R.id.d_car_region);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
    }

    private void getData () {

        progressDialog.show();

        Call<user_service> call = jsonPlaceHolderApi.GetCar_Owner_Data(sharedPreferences.getString("email", "null"));
        call.enqueue(new Callback<user_service>() {
            @Override
            public void onResponse(Call<user_service> call, Response<user_service> response) {

                if (response.isSuccessful()) {

                    user_service user_service = response.body();
                    List<Driver_Model> driver_models = user_service.getDriver_model();

                    for (Driver_Model driver_model : driver_models) {

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
                        //dPassword.setText(driver_model.getPassword());

                        getCarData(driver_model.getOwner_name());

                        progressDialog.dismiss();
                    }

                }

            }

            @Override
            public void onFailure(Call<user_service> call, Throwable t) {
                Toast.makeText(Driver_Profile.this, "Check Internet", Toast.LENGTH_SHORT).show();
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

                    carType.setText(car_model.getCar_type());
                        panalId.setText(car_model.getPanal_id());
                        weight.setText(car_model.getAllowed_weight());
                        dCarCity.setText(car_model.getDriver_car_city());
                        dCarRegion.setText(car_model.getDriver_car_region());
                        dCarName.setText(name);


                    }

                }

            }

            @Override
            public void onFailure(Call<user_service> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Setting) {

            editor.putString("setting","update");
            editor.commit();
            startActivity(new Intent(Driver_Profile.this, DriverInfo.class));


        } else if (id == R.id.Logout) {

            startActivity(new Intent(Driver_Profile.this, SignIn.class));
            finish();


        }

        return super.onOptionsItemSelected(item);
    }


    public void backmain(View view) {
        startActivity(new Intent(Driver_Profile.this, User_Main.class));
        finish();

    }
}
