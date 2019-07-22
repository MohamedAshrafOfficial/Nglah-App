package com.debugspace.nglah.View.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.debugspace.nglah.Model.DriverModel.LoginDriverService;
import com.debugspace.nglah.Model.NglahModel.LoginNglahService;
import com.debugspace.nglah.Model.NglahModel.Nglah;
import com.debugspace.nglah.Model.hassan_now.User_Model;
import com.debugspace.nglah.Model.hassan_now.user_service;
import com.debugspace.nglah.R;
import com.debugspace.nglah.Services.JsonPlaceHolderApi;
import com.debugspace.nglah.View.Driver.DriverInfo;
import com.debugspace.nglah.View.PaymentSystem;
import com.debugspace.nglah.View.User_Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfo extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText uFName;
    private EditText uLName;
    private EditText uPhone;
    private EditText uEmail;
    private EditText uUsername;
    private EditText uPassword;

    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ematj.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("waiting...");
        initView();
        getData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        uFName = (EditText) findViewById(R.id.u_f_name);
        uLName = (EditText) findViewById(R.id.u_l_name);
        uPhone = (EditText) findViewById(R.id.u_phone);
        uEmail = (EditText) findViewById(R.id.u_email);
        uUsername = (EditText) findViewById(R.id.u_username);
        uPassword = (EditText) findViewById(R.id.u_password);
    }

    private void getData (){

        if (sharedPreferences.getString("setting","null").equals("update")){


        Call<user_service>call=jsonPlaceHolderApi.GetData(sharedPreferences.getString("email","null"));
        call.enqueue(new Callback<user_service>() {
            @Override
            public void onResponse(Call<user_service> call, Response<user_service> response) {

                if (response.isSuccessful()){

                    user_service user_service=response.body();
                    List<User_Model> user_model=user_service.getUser_model();

                    for (User_Model user_model1 : user_model){

                        uFName.setText(user_model1.getF_name());
                        uLName.setText(user_model1.getL_name());
                    uPhone.setText(user_model1.getPhone());
                    uEmail.setText(user_model1.getEmail());
                    uUsername.setText(user_model1.getUsername());
                    uPassword.setText(user_model1.getPassword());


                    }

                }

            }

            @Override
            public void onFailure(Call<user_service> call, Throwable t) {

            }
        });

        }else {

            uFName.setText(sharedPreferences.getString("f_name",""));
            uLName.setText(sharedPreferences.getString("l_name",""));
            uPhone.setText(sharedPreferences.getString("phone","null"));
            uEmail.setText(sharedPreferences.getString("email","null"));
            uUsername.setText(sharedPreferences.getString("username","null"));
            uPassword.setText(sharedPreferences.getString("password","null"));



        }





    }

    private void CreateUser() {

        progressDialog.show();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("first_name", uFName.getText().toString());
        parameters.put("last_name", uLName.getText().toString());
        parameters.put("phone", uPhone.getText().toString());
        parameters.put("email", uEmail.getText().toString());
        parameters.put("user_name", uUsername.getText().toString());
        parameters.put("password", uPassword.getText().toString());

        Call<LoginNglahService> call = jsonPlaceHolderApi.CreateUser(parameters);

        call.enqueue(new Callback<LoginNglahService>() {
            @Override
            public void onResponse(Call<LoginNglahService> call, Response<LoginNglahService> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(UserInfo.this, "Check Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginNglahService service = response.body();

                if (service.isSuccess()){
                    List<Nglah> nglah = service.getNglahOwnerInfo();
                    editor.putBoolean("flag_t", false);
                    editor.putInt("Nglah_ID", nglah.get(0).getNglahId());
                    editor.commit();
                    progressDialog.dismiss();
                    startActivity(new Intent(UserInfo.this, User_Main.class));
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(UserInfo.this, "خطأ فى التسجيل !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginNglahService> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserInfo.this, "Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateUser() {

        progressDialog.show();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("first_name", uFName.getText().toString());
        parameters.put("last_name", uLName.getText().toString());
        parameters.put("phone", uPhone.getText().toString());
        parameters.put("email", uEmail.getText().toString());
        parameters.put("user_name", uUsername.getText().toString());
        parameters.put("password", uPassword.getText().toString());
        parameters.put("old_email", sharedPreferences.getString("email","null"));

        Call<LoginNglahService> call = jsonPlaceHolderApi.Update_User(parameters);
        call.enqueue(new Callback<LoginNglahService>() {
            @Override
            public void onResponse(Call<LoginNglahService> call, Response<LoginNglahService> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(UserInfo.this, "Check Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginNglahService service = response.body();

                if (service.isSuccess()){
                    progressDialog.dismiss();
                    startActivity(new Intent(UserInfo.this,User_Main.class));
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(UserInfo.this, "خطأ فى التعديل !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginNglahService> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserInfo.this, "Check Internet", Toast.LENGTH_SHORT).show();
            }
        });



    }


    public void Update(View view) {

        if (sharedPreferences.getString("setting","null").equals("update")) {

            UpdateUser();
            editor.putString("setting","");
            editor.commit();
        }else {

            CreateUser();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (sharedPreferences.getString("setting","null").equals("update")){
            startActivity(new Intent(UserInfo.this, User_Main.class));
            finish();

        }else {
            startActivity(new Intent(UserInfo.this, PaymentSystem.class));
            finish();
        }
    }
}
