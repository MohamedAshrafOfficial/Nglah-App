package com.example.nglah.View.User;

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

import com.example.nglah.Model.hassan_now.User_Model;
import com.example.nglah.Model.hassan_now.user_service;
import com.example.nglah.R;
import com.example.nglah.Services.JsonPlaceHolderApi;
import com.example.nglah.View.Driver.Driver_Profile;
import com.example.nglah.View.SignIn;
import com.example.nglah.View.User_Main;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_Profile extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView uFName;
    private TextView uLName;
    private TextView uPhone;
    private TextView uEmail;
    private TextView uUsername;
    private TextView uPassword;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ematj.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        initView();
        getData();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.yello));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        uFName = (TextView) findViewById(R.id.u_f_name);
        uLName = (TextView) findViewById(R.id.u_l_name);
        uPhone = (TextView) findViewById(R.id.u_phone);
        uEmail = (TextView) findViewById(R.id.u_email);
        uUsername = (TextView) findViewById(R.id.u_username);
        uPassword = (TextView) findViewById(R.id.u_password);
    }


    private void getData (){




        progressDialog.show();
        Call<user_service> call=jsonPlaceHolderApi.GetData(sharedPreferences.getString("email","null"));



        call.enqueue(new Callback<user_service>() {
            @Override
            public void onResponse(Call<user_service> call, Response<user_service> response) {

                if (response.isSuccessful()){
//
                    user_service user_service=response.body();
                    List<User_Model> user_model=user_service.getUser_model();

                    for (User_Model user_model1 : user_model){

                        uFName.setText(user_model1.getF_name());
                        uLName.setText(user_model1.getL_name());
                        uPhone.setText(user_model1.getPhone());
                        uEmail.setText(user_model1.getEmail());
                        uUsername.setText(user_model1.getUsername());
                        //uPassword.setText(user_model1.getPassword());
                        progressDialog.dismiss();


                    }

                }else {
                    Toast.makeText(User_Profile.this, "Check Internet", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<user_service> call, Throwable t) {
                Toast.makeText(User_Profile.this, "Check Internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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
            startActivity(new Intent(User_Profile.this, UserInfo.class));
            finish();


        } else if (id == R.id.Logout) {

            startActivity(new Intent(User_Profile.this, SignIn.class));
            finish();


        }

        return super.onOptionsItemSelected(item);
    }


    public void back(View view) {
        startActivity(new Intent(User_Profile.this, User_Main.class));
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(User_Profile.this, User_Main.class));
        finish();
    }
}
