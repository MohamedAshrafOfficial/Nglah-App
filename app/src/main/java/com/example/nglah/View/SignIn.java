package com.example.nglah.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
 import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nglah.Services.ApiClient;
import com.example.nglah.Services.JsonPlaceHolderApi;
import com.example.nglah.Model.DriverModel.LoginDriverService;
import com.example.nglah.Model.DriverModel.Driver;
import com.example.nglah.Model.NglahModel.LoginNglahService;
import com.example.nglah.Model.NglahModel.Nglah;
import com.example.nglah.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private Spinner spinnerUser;

    private boolean flagUserSelected;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // init all widgets in this activity
        initWidgets();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        // init spinner and selected user login
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("صاحب نقله");
        arrayList.add("صاحب سيارة");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);
        spinnerUser.setOnItemSelectedListener(this);

        // action all widgets in this activity
        actionWidgets();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    private void initWidgets(){
        emailEditText = findViewById(R.id.et_user_name);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.bt_login);
        spinnerUser = findViewById(R.id.sp_user);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
    private void actionWidgets(){

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (flagUserSelected){

                    nglahLogin(email, password);

                }else{

                    driverLogin(email, password);
                }

            }
        });
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
    private void driverLogin(String email, String password){

        if (email.isEmpty() && password.isEmpty()){
            return;
        }

        Call<LoginDriverService> call = jsonPlaceHolderApi.getDriverInfo(email, password);

        call.enqueue(new Callback<LoginDriverService>() {
            @Override
            public void onResponse(Call<LoginDriverService> call, Response<LoginDriverService> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(SignIn.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginDriverService driverResponse = response.body();

                if (driverResponse.isSuccess()) {

                    List<Driver> driver = driverResponse.getDriverInfo();

                    startActivity(new Intent(SignIn.this, User_Main.class));

                }else{
                    Toast.makeText(SignIn.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginDriverService> call, Throwable t) {
                Toast.makeText(SignIn.this, "User Name or Password is Invalid", Toast.LENGTH_SHORT).show();
            }
        });

    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    private void nglahLogin(String email, String password){

        if (email.isEmpty() && password.isEmpty()){
            return;
        }

        Call<LoginNglahService> call = jsonPlaceHolderApi.getNglahInfo(email, password);

        call.enqueue(new Callback<LoginNglahService>() {
            @Override
            public void onResponse(Call<LoginNglahService> call, Response<LoginNglahService> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(SignIn.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginNglahService nglahResponse = response.body();

                if (nglahResponse.isSuccess()) {

                    List<Nglah> nglah = nglahResponse.getNglahOwnerInfo();

                    startActivity(new Intent(SignIn.this, User_Main.class));

                }else{
                    Toast.makeText(SignIn.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginNglahService> call, Throwable t) {
                Toast.makeText(SignIn.this, "User Name or Password is Invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == 0)
            flagUserSelected = true;     // nglah owner
        else
            flagUserSelected = false;    // car owner
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void goSignUp(View view) {
        startActivity(new Intent(SignIn.this,SignUp.class));
    }
}
