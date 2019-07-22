package com.debugspace.nglah.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.debugspace.nglah.Model.LoginUserService;
import com.debugspace.nglah.Model.User;
import com.debugspace.nglah.Services.ApiClient;
import com.debugspace.nglah.Services.JsonPlaceHolderApi;
import com.debugspace.nglah.Model.DriverModel.LoginDriverService;
import com.debugspace.nglah.Model.DriverModel.Driver;
import com.debugspace.nglah.Model.NglahModel.LoginNglahService;
import com.debugspace.nglah.Model.NglahModel.Nglah;
import com.debugspace.nglah.R;
import com.debugspace.nglah.View.User.DriversList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // init all widgets in this activity
        initWidgets();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        // action all widgets in this activity
        actionWidgets();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
    private void initWidgets(){
        emailEditText = findViewById(R.id.et_user_name);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.bt_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Waiting...");
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
    private void actionWidgets(){

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                Login(email, password);
            }
        });
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//    private void driverLogin(String email, String password){
//
//        progressDialog.show();
//
//        if (email.isEmpty() && password.isEmpty()){
//            return;
//        }
//
//        Call<LoginDriverService> call = jsonPlaceHolderApi.getDriverInfo(email, password);
//
//        call.enqueue(new Callback<LoginDriverService>() {
//            @Override
//            public void onResponse(Call<LoginDriverService> call, Response<LoginDriverService> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(SignIn.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                LoginDriverService driverResponse = response.body();
//
//                if (driverResponse.isSuccess()) {
//
//                    List<Driver> driver = driverResponse.getDriverInfo();
//
//                    editor.putInt("user_type",2);
//                    editor.putString("Driver_ID",driver.get(0).getDriverId());
//                    editor.putString("email",driver.get(0).getEmail());
//                    editor.commit();
//
//                    progressDialog.dismiss();
//                    startActivity(new Intent(SignIn.this, User_Main.class));
//                    finish();
//
//                }else{
//                    progressDialog.dismiss();
//                    Toast.makeText(SignIn.this, "Invalid Password", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginDriverService> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(SignIn.this, "User Name or Password is Invalid", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

//    private void nglahLogin(String email, String password){
//
//        progressDialog.show();
//
//        if (email.isEmpty() && password.isEmpty()){
//            return;
//        }
//
//        Call<LoginNglahService> call = jsonPlaceHolderApi.getNglahInfo(email, password);
//
//        call.enqueue(new Callback<LoginNglahService>() {
//            @Override
//            public void onResponse(Call<LoginNglahService> call, Response<LoginNglahService> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(SignIn.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                LoginNglahService nglahResponse = response.body();
//
//                if (nglahResponse.isSuccess()) {
//
//                    List<Nglah> nglah = nglahResponse.getNglahOwnerInfo();
//                    editor.putInt("user_type",1);
//                    editor.putInt("Nglah_ID",nglah.get(0).getNglahId());
//                    editor.putString("email",nglah.get(0).getEmail());
//                    editor.commit();
//                    finish();
//
//                    if (sharedPreferences.getBoolean("flag_t",false)==true){
//                        progressDialog.dismiss();
//                        startActivity(new Intent(SignIn.this, DriversList.class));
//                        finish();
//                    }else {
//                        progressDialog.dismiss();
//                        startActivity(new Intent(SignIn.this, User_Main.class));
//                        finish();
//                    }
//
//                }else{
//                    progressDialog.dismiss();
//                    Toast.makeText(SignIn.this, "Invalid Password", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginNglahService> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(SignIn.this, "User Name or Password is Invalid", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void Login(String email, String password){

        progressDialog.show();

        if (email.isEmpty() && password.isEmpty()){
            return;
        }

        Call<LoginUserService> call = jsonPlaceHolderApi.getUserInfo(email, password);
        call.enqueue(new Callback<LoginUserService>() {
            @Override
            public void onResponse(Call<LoginUserService> call, Response<LoginUserService> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(SignIn.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginUserService userResponse = response.body();

                if (userResponse.isSuccess()){

                    List<User> user = userResponse.getUserInfo();
                    int userType = user.get(0).getUserType();
                    switch (userType){
                        case 1:                       // Nglah user

                            editor.putInt("user_type",userType);
                            editor.putInt("Nglah_ID",user.get(0).getNglahId());
                            editor.putString("email",user.get(0).getEmail());
                            editor.commit();
//                            finish();

                            if (sharedPreferences.getBoolean("flag_t",false)==true){
                                progressDialog.dismiss();
                                startActivity(new Intent(SignIn.this, DriversList.class));
                                finish();
                            }else {
                                progressDialog.dismiss();
                                startActivity(new Intent(SignIn.this, User_Main.class));
                                finish();
                            }

                            break;
                        case 2:                       // Driver user

                            editor.putInt("user_type",userType);
                            editor.putString("Driver_ID",user.get(0).getDriverId());
                            editor.putString("email",user.get(0).getEmail());
                            editor.commit();

                            progressDialog.dismiss();
                            startActivity(new Intent(SignIn.this, User_Main.class));
                            finish();
                            break;
                        default:
                            Toast.makeText(SignIn.this, "خطأ فى الايميل او كلمه السر", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignIn.this, "كلمه السر غير صحيحه !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginUserService> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignIn.this, "خطأ فى الايميل او كلمه السر", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void goSignUp(View view) {
        startActivity(new Intent(SignIn.this,SignUp.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
