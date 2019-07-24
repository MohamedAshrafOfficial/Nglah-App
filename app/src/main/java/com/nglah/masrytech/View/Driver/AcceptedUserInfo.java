package com.nglah.masrytech.View.Driver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nglah.masrytech.R;
import com.nglah.masrytech.Services.ApiClient;
import com.nglah.masrytech.Services.JsonPlaceHolderApi;
import com.nglah.masrytech.View.User_Main;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptedUserInfo extends AppCompatActivity {

    private TextView nglahNameTextView, thingTypeTextView, nglahDetailsTextView, nglahCityTextView,
                      nglahDateTextView, nglahTimeTextView;

    private EditText nglahPriceEditText;
    private Button okButton;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    SharedPreferences sharedPreferences;

    private int nglahId;
    private String nglahName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_info);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);

        Intent intent = getIntent();

        initWidgets();

        nglahId = intent.getIntExtra("nglah_id",0);
        nglahName = intent.getStringExtra("nglah_name");

        nglahNameTextView.setText(nglahName);
        thingTypeTextView.setText(intent.getStringExtra("thing_type"));
        nglahDateTextView.setText(intent.getStringExtra("nglah_date"));
        nglahTimeTextView.setText(intent.getStringExtra("nglah_time"));
        nglahCityTextView.setText(intent.getStringExtra("nglah_city"));
//        nglahSectorTextView.setText(intent.getStringExtra("nglah_sector"));
        nglahDetailsTextView.setText(intent.getStringExtra("nglah_details"));

//        Toast.makeText(this, ""+nglahId, Toast.LENGTH_SHORT).show();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        actionWidgets();
    }

    private void initWidgets(){

        nglahNameTextView = findViewById(R.id.tv_nglah_name);
        thingTypeTextView = findViewById(R.id.tv_thing_type);
        nglahCityTextView = findViewById(R.id.tv_city);
//        nglahSectorTextView = findViewById(R.id.tv_sector);
        nglahDateTextView = findViewById(R.id.tv_date);
        nglahTimeTextView = findViewById(R.id.tv_time);
        nglahDetailsTextView = findViewById(R.id.tv_details);

        nglahPriceEditText = findViewById(R.id.et_price);

        okButton = findViewById(R.id.bt_ok);

    }

    private void sendNglahRequest(String driverNationalId, int nglahId, double nglahPrice){

        Call<String> call = jsonPlaceHolderApi.sendNglahRequest(driverNationalId, nglahId, nglahPrice);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                startActivity(new Intent(AcceptedUserInfo.this, User_Main.class));
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                startActivity(new Intent(AcceptedUserInfo.this, User_Main.class));
                finish();
                Toast.makeText(AcceptedUserInfo.this, "Request Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotificationToNglah(){

        Call<String> call = jsonPlaceHolderApi.sendNotificationToNglah(
                "1",
                nglahName + " Accepted Your Request");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void actionWidgets(){

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNglahRequest(sharedPreferences.getString("Driver_ID","null"), nglahId, Double.parseDouble(nglahPriceEditText.getText().toString()));
//                sendNotificationToNglah();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AcceptedUserInfo.this, User_Main.class));
        finish();
    }
}
