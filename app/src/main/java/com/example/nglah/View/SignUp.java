package com.example.nglah.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nglah.Model.hassan_now.Car_Model;
import com.example.nglah.Model.hassan_now.Driver_Model;
import com.example.nglah.Model.hassan_now.User_Model;
import com.example.nglah.Model.hassan_now.Verification_model;
import com.example.nglah.R;
import com.example.nglah.Services.JsonPlaceHolderApi;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        retrofit2 = new Retrofit.Builder()
                .baseUrl("https://hassan-elkhadrawy.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        initView();
        Action();
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    user_Layout = LayoutInflater.from(SignUp.this).inflate(R.layout.activity_user_info, null);
                    alertDialog.setView(user_Layout);
                    user_list.clear();

                    final EditText u_f_name = user_Layout.findViewById(R.id.u_f_name);
                    final EditText u_l_name = user_Layout.findViewById(R.id.u_l_name);
                    final EditText u_phone = user_Layout.findViewById(R.id.u_phone);
                    final EditText u_email = user_Layout.findViewById(R.id.u_email);
                    final EditText u_username = user_Layout.findViewById(R.id.u_username);
                    final EditText u_password = user_Layout.findViewById(R.id.u_password);
                    u_phone.setText(edtphone.getText().toString());
                    u_email.setText(edtemail.getText().toString());
                    u_username.setText(edtname.getText().toString());
                    u_password.setText(edtpass.getText().toString());


                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            User_Model user_model = new User_Model();
                            user_model.setF_name(u_f_name.getText().toString());
                            user_model.setL_name(u_l_name.getText().toString());
                            user_model.setPhone(u_phone.getText().toString());
                            user_model.setEmail(u_email.getText().toString());
                            user_model.setUsername(u_username.getText().toString());
                            user_model.setPassword(u_password.getText().toString());
                            user_list.add(user_model);


                            dialog.dismiss();
                        }
                    });
                    alertDialog.setNegativeButton("Cancil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();

                } else if (position == 2) {


                    driver_list.clear();
                    user_Layout = LayoutInflater.from(SignUp.this).inflate(R.layout.activity_driver_info, null);
                    alertDialog.setView(user_Layout);
                    alertDialog.setCancelable(false);
                    final EditText d_nationality = user_Layout.findViewById(R.id.national);
                    final EditText d_licence = user_Layout.findViewById(R.id.d_licence);
                    final EditText d_f_name = user_Layout.findViewById(R.id.d_f_name);
                    final EditText d_s_name = user_Layout.findViewById(R.id.d_s_name);
                    final EditText d_l_name = user_Layout.findViewById(R.id.d_l_name);
                    final EditText d_national_id = user_Layout.findViewById(R.id.d_national_id);
                    final EditText d_phone = user_Layout.findViewById(R.id.d_phone);
                    final EditText d_email = user_Layout.findViewById(R.id.d_email);
                    final EditText d_city = user_Layout.findViewById(R.id.d_city);
                    final EditText d_region = user_Layout.findViewById(R.id.d_region);
                    final EditText d_username = user_Layout.findViewById(R.id.d_username);
                    final EditText d_password = user_Layout.findViewById(R.id.d_password);

                    d_phone.setText(edtphone.getText().toString());
                    d_email.setText(edtemail.getText().toString());
                    d_username.setText(edtname.getText().toString());
                    d_password.setText(edtpass.getText().toString());


                    alertDialog.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Driver_Model driver_model = new Driver_Model();
                            driver_model.setNationality(d_nationality.getText().toString());
                            driver_model.setDriver_l_id(d_licence.getText().toString());
                            driver_model.setF_name(d_f_name.getText().toString());
                            driver_model.setSe_name(d_s_name.getText().toString());
                            driver_model.setL_name(d_l_name.getText().toString());
                            driver_model.setDriver_n_id(d_national_id.getText().toString());
                            driver_model.setPhone(d_phone.getText().toString());
                            driver_model.setEmail(d_email.getText().toString());
                            driver_model.setD_city(d_city.getText().toString());
                            driver_model.setD_region(d_region.getText().toString());
                            driver_model.setUsernmae(d_username.getText().toString());
                            driver_model.setPassword(d_password.getText().toString());

                            driver_list.add(driver_model);


                            car_Info();
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setNegativeButton("Cancil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //////////////////////////////////////////////////
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemPosition() == 1) {

                    Verification(user_list.get(0).getEmail());


                } else if (spinner.getSelectedItemPosition() == 2) {

                    Verification(driver_list.get(0).getEmail());
                }

            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void car_Info() {

        user_Layout = LayoutInflater.from(SignUp.this).inflate(R.layout.activity_car_info, null);
        alertDialog.setView(user_Layout);
        alertDialog.setCancelable(false);
        car_list.clear();
        final EditText Car_type = user_Layout.findViewById(R.id.car_type);
        final EditText Car_Panal = user_Layout.findViewById(R.id.panal_id);
        final EditText Car_Weight = user_Layout.findViewById(R.id.weight);
        final EditText D_Car_Name = user_Layout.findViewById(R.id.d_car_name);
        final EditText D_Car_City = user_Layout.findViewById(R.id.d_car_city);
        final EditText D_Car_Region = user_Layout.findViewById(R.id.d_car_region);


        alertDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Car_Model car_model = new Car_Model();
                car_model.setCar_type(Car_type.getText().toString());
                car_model.setCar_image("https:");
                car_model.setPanal_id(Car_Panal.getText().toString());
                car_model.setAllowed_weight(Car_Weight.getText().toString());
                car_model.setDriver_car_name(D_Car_Name.getText().toString());
                car_model.setDriver_car_city(D_Car_City.getText().toString());
                car_model.setDriver_car_region(D_Car_Region.getText().toString());

                car_list.add(car_model);
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void CreateUser() {
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("first_name", user_list.get(0).getF_name());
        parameters.put("last_name", user_list.get(0).getL_name());
        parameters.put("phone", user_list.get(0).getPhone());
        parameters.put("email", user_list.get(0).getEmail());
        parameters.put("user_name", user_list.get(0).getUsername());
        parameters.put("password", user_list.get(0).getPassword());
        Call<User_Model> call = jsonPlaceHolderApi.CreateUser(parameters);
        Toast.makeText(this, "" + parameters, Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<User_Model>() {
            @Override
            public void onResponse(Call<User_Model> call, Response<User_Model> response) {

                if (!response.isSuccessful()) {

                    Toast.makeText(SignUp.this, "faild", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(SignUp.this, "success", Toast.LENGTH_SHORT).show();

                    return;
                }

            }

            @Override
            public void onFailure(Call<User_Model> call, Throwable t) {
                Toast.makeText(SignUp.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void CreateDriver() {
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("driver_national_id", driver_list.get(0).getDriver_n_id());
        parameters.put("driver_name", car_list.get(0).getDriver_car_name());
        parameters.put("nationality", driver_list.get(0).getNationality());
        parameters.put("first_name", driver_list.get(0).getF_name());
        parameters.put("second_name", driver_list.get(0).getSe_name());
        parameters.put("last_name", driver_list.get(0).getL_name());
        parameters.put("driver_license_id", driver_list.get(0).getDriver_l_id());
        parameters.put("phone", driver_list.get(0).getPhone());
        parameters.put("email", driver_list.get(0).getEmail());
        parameters.put("user_name", driver_list.get(0).getUsernmae());
        parameters.put("password", driver_list.get(0).getPassword());
        parameters.put("car_type", car_list.get(0).getCar_type());
        parameters.put("car_image", car_list.get(0).getCar_image());
        parameters.put("panel_id", car_list.get(0).getPanal_id());
        parameters.put("allowed_weight", car_list.get(0).getAllowed_weight());
        parameters.put("owner_city", driver_list.get(0).getD_city());
        parameters.put("owner_region", driver_list.get(0).getD_region());
        parameters.put("driver_city", car_list.get(0).getDriver_car_city());
        parameters.put("driver_region", car_list.get(0).getDriver_car_region());

        Call<Driver_Model> call = jsonPlaceHolderApi.CreateDriver(parameters);
        call.enqueue(new Callback<Driver_Model>() {
            @Override
            public void onResponse(Call<Driver_Model> call, Response<Driver_Model> response) {

                if (!response.isSuccessful()) {

                    Toast.makeText(SignUp.this, "faild", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(SignUp.this, "success", Toast.LENGTH_SHORT).show();

                    return;
                }
            }

            @Override
            public void onFailure(Call<Driver_Model> call, Throwable t) {
                Toast.makeText(SignUp.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

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

                    return;
                } else {
                    Toast.makeText(SignUp.this, "faild", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Verification_model> call, Throwable t) {

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
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int numberCode = Integer.parseInt(txt.getText().toString());
                if (numberCode == code) {
                    if (spinner.getSelectedItemPosition() == 1) {

                        CreateUser();

                    } else if (spinner.getSelectedItemPosition() == 2) {

                        CreateDriver();

                    }
                    startActivity(new Intent(SignUp.this, PaymentSystem.class));

                } else {
                    Toast.makeText(SignUp.this, "Invalid code", Toast.LENGTH_SHORT).show();

                }
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void Back(View view) {
        startActivity(new Intent(this, SignIn.class));
    }
}
