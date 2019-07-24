package com.debugspace.nglah.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debugspace.nglah.Model.hassan_now.Car_Model;
import com.debugspace.nglah.Model.hassan_now.Driver_Model;
import com.debugspace.nglah.Model.hassan_now.User_Model;
import com.debugspace.nglah.Model.hassan_now.Verification_model;
import com.debugspace.nglah.R;
import com.debugspace.nglah.Services.JsonPlaceHolderApi;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgBackRecent;
    private LinearLayout framLayout;
    private EditText edtname;
    private EditText edtphone;
    private EditText edtemail;
    private EditText edtpass;
    private Spinner spinner;
    private TextView signUp;
    private Button Next;
    private String[] list;
    private ArrayAdapter<String> adapter;
    private FragmentTransaction fragmentTransaction;
    private LinearLayout Sin_layout, Main_Fram;
    AlertDialog.Builder alertDialog;
    View user_Layout;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Retrofit retrofit, retrofit2;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ArrayList<User_Model> user_list = new ArrayList<>();
    private ArrayList<Driver_Model> driver_list = new ArrayList<>();
    private ArrayList<Car_Model> car_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ematj.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        retrofit2 = new Retrofit.Builder()
                .baseUrl("https://hassan-elkhadrawy.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("waiting...");
        initView();
        Action();
        editor.putString("setting", "");
        editor.putBoolean("mahfazty", false);
        editor.commit();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint({"ResourceType", "WrongViewCast"})
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgBackRecent = (ImageView) findViewById(R.id.imgBackRecent);
        edtname = (EditText) findViewById(R.id.edtname);
        edtphone = (EditText) findViewById(R.id.edtphone);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edtpass = (EditText) findViewById(R.id.edtpass);
        spinner = (Spinner) findViewById(R.id.spinner);
        signUp = (TextView) findViewById(R.id.signUp);
        list = getResources().getStringArray(R.array.detect_user);
        alertDialog = new AlertDialog.Builder(this);


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void Action() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        spinner.setAdapter(adapter);
             //////////////////////////////////////////////////
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtname.getText().toString().isEmpty()) {
                    edtname.setError("name not entered");
                    edtname.requestFocus();

                } else if (edtemail.getText().toString().isEmpty()) {

                    edtemail.setError("email not entered");
                    edtemail.requestFocus();
                } else if (!isEmailValid(edtemail.getText().toString())) {

                    edtemail.setError("email not correct");
                    edtemail.requestFocus();
                } else if (edtphone.getText().toString().isEmpty()) {
                    edtphone.setError("phone not entered");
                    edtphone.requestFocus();

                } else if (edtpass.getText().toString().isEmpty()) {

                    edtpass.setError("password not entered");
                    edtpass.requestFocus();
                } else if (spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(SignUp.this, "اختر الهويه", Toast.LENGTH_SHORT).show();

                    spinner.requestFocus();
                } else {

                    progressDialog.show();
                    checkEmailFound(edtemail.getText().toString());
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void Verification(String Email) {

        jsonPlaceHolderApi = retrofit2.create(JsonPlaceHolderApi.class);
        Call<Verification_model> call = jsonPlaceHolderApi.verifi(Email);

        call.enqueue(new Callback<Verification_model>() {
            @Override
            public void onResponse(Call<Verification_model> call, Response<Verification_model> response) {
                if (response.isSuccessful()) {
                    Verification_model verification_model = response.body();

                    checkCodeAliart(verification_model.getCode());

                    progressDialog.dismiss();

                    return;
                } else {
                    progressDialog.dismiss();

                    Toast.makeText(SignUp.this, "faild", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Verification_model> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void checkCodeAliart(final int code) {
        final EditText txt = new EditText(SignUp.this);
        txt.setHint("Virification Code");
        txt.setTextColor(Color.BLACK);
        alertDialog.setView(txt);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNegativeButton("Cancel", null);
        final AlertDialog mAlertDialog = alertDialog.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button Positive = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button Cancel = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                Positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int numberCode = Integer.parseInt(txt.getText().toString());
                        if (numberCode == code) {
                            if (spinner.getSelectedItemPosition() == 1) {

                                // CreateUser();


                                editor.putInt("user_type",1);


                            } else if (spinner.getSelectedItemPosition() == 2) {
                                editor.putInt("user_type",2);

                                //  CreateDriver();

                            }

                            editor.putString("username",edtname.getText().toString());
                            editor.putString("phone",edtphone.getText().toString());
                            editor.putString("email",edtemail.getText().toString());
                            editor.putString("password",edtpass.getText().toString());
                            editor.commit();

                            startActivity(new Intent(SignUp.this, PaymentSystem.class));
                            finish();


                        } else {
                            txt.setError("الرقم غير صحيح !");
                            txt.requestFocus();
                        }
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SignUp.this, SignIn.class));
                        dialogInterface.dismiss();
                        finish();
                    }
                });
            }
        });

        mAlertDialog.show();
    }


    public Integer checkEmailFound(String Email) {

        final int[] code = new int[1];
        Call<Verification_model> call = jsonPlaceHolderApi.GetEmail(Email);
        call.enqueue(new Callback<Verification_model>() {
            @Override
            public void onResponse(Call<Verification_model> call, Response<Verification_model> response) {


                if (response.isSuccessful()) {
                    Verification_model verification_model = response.body();

                    if (verification_model.getCode()==0){
                        if (spinner.getSelectedItemPosition() == 1) {

                          //  Naglah_Owner();
                            editor.putInt("user_type",1);

                            editor.commit();

                        } else if (spinner.getSelectedItemPosition() == 2) {

                            //Driver();
                            editor.putInt("user_type",2);
                            editor.commit();


                        }
                        Verification(edtemail.getText().toString());

                    }else {

                        edtemail.setError("email already exist");
                        edtemail.requestFocus();
                        progressDialog.dismiss();


                    }

//                    return;
                } else {
                    progressDialog.dismiss();

                    Toast.makeText(SignUp.this, "faild", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Verification_model> call, Throwable t) {

            }
        });
        return code[0];

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void Back(View view) {
        startActivity(new Intent(this, SignIn.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, SignIn.class));
        finish();
    }
}
