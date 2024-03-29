package com.nglah.masrytech.View.User;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nglah.masrytech.Model.NglahModel.LoginNglahService;
import com.nglah.masrytech.R;
import com.nglah.masrytech.Services.JsonPlaceHolderApi;
import com.nglah.masrytech.View.User_Main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PickTime extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgBackRecent;
    private Button now;
    private Button later;
    private TextView date;
    private TextView time;
    private EditText details;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_time);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ematj.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Waiting...");
        initView();

        imgBackRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickTime.this,PickElement.class));
                finish();
            }
        });
    }



    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgBackRecent = (ImageView) findViewById(R.id.imgBackRecent);
        now = (Button) findViewById(R.id.now);
        later = (Button) findViewById(R.id.later);
        date =  findViewById(R.id.date);
        time =  findViewById(R.id.time);
        details = (EditText) findViewById(R.id.details);
    }

    public void Order(View view) {
        if (sharedPreferences.getString("nglah_type","null").equals("Inside")){
            Create_Inside_Order();


        }else if (sharedPreferences.getString("nglah_type","null").equals("OutSide")){
            Create_OutSide_Order();

        }else {

            Toast.makeText(this, "repeat order", Toast.LENGTH_SHORT).show();
        }


    }

    public void Cancle(View view) {
        startActivity(new Intent(PickTime.this, User_Main.class));
        finish();

    }

    public void Now(View view) {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        String formattedTime = tf.format(c.getTime());
        time.setText(formattedTime);
        date.setText(formattedDate);

    }

    public void Later(View view) {


        final View dialogView = View.inflate(PickTime.this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(PickTime.this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
                String formattedDate = df.format(calendar.getTime());
                String formattedTime = tf.format(calendar.getTime());
                time.setText(formattedTime);
                date.setText(formattedDate);
                alertDialog.dismiss();
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();


    }

    private void Create_Inside_Order(){

        progressDialog.show();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("nglah_id", String.valueOf(sharedPreferences.getInt("Nglah_ID",0)));
        parameters.put("country", sharedPreferences.getString("country","null"));
        parameters.put("region", sharedPreferences.getString("region","null"));
        parameters.put("city", sharedPreferences.getString("city","null"));
        parameters.put("thing_type", sharedPreferences.getString("thing_type_elment","null"));
        parameters.put("time", time.getText().toString());
        parameters.put("date", date.getText().toString());
        parameters.put("details", details.getText().toString());

        Call<LoginNglahService> call=jsonPlaceHolderApi.CreateUser__Inside_Order(parameters);

        call.enqueue(new Callback<LoginNglahService>() {
            @Override
            public void onResponse(Call<LoginNglahService> call, Response<LoginNglahService> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(PickTime.this, "Check Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginNglahService service = response.body();

                if (service.isSuccess()){
                    progressDialog.dismiss();
                    editor.putBoolean("flag_t",true);
                    editor.commit();
                    startActivity(new Intent(PickTime.this,DriversList.class));
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(PickTime.this, "خطأ فى التسجيل الطلب !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginNglahService> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PickTime.this, "Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Create_OutSide_Order(){

        progressDialog.show();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("nglah_id", String.valueOf(sharedPreferences.getInt("Nglah_ID",0)));
        parameters.put("country", sharedPreferences.getString("country","null"));
        parameters.put("arrive_details", sharedPreferences.getString("city_recive","null"));
        parameters.put("departure_details", sharedPreferences.getString("location","null"));
        parameters.put("arrive_city", sharedPreferences.getString("city_recive","null"));
        parameters.put("departure_city", sharedPreferences.getString("city_send","null"));
        parameters.put("thing_type", sharedPreferences.getString("thing_type_elment","null"));
        parameters.put("time", time.getText().toString());
        parameters.put("date", date.getText().toString());
        parameters.put("details", details.getText().toString());

        Call<LoginNglahService> call=jsonPlaceHolderApi.CreateUser__Outside_Order(parameters);

        call.enqueue(new Callback<LoginNglahService>() {
            @Override
            public void onResponse(Call<LoginNglahService> call, Response<LoginNglahService> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(PickTime.this, "Check Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginNglahService service = response.body();

                if (service.isSuccess()){
                    progressDialog.dismiss();
                    editor.putBoolean("flag_t",true);
                    editor.commit();
                    startActivity(new Intent(PickTime.this,DriversList.class));
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(PickTime.this, "خطأ فى التسجيل الطلب !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginNglahService> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PickTime.this, "Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PickTime.this,PickElement.class));
        finish();
    }
}
