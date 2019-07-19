package com.example.nglah.View.Driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nglah.Model.NglahModel.NglahOrdersService;
import com.example.nglah.R;
import com.example.nglah.Services.ApiClient;
import com.example.nglah.Services.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptedUserInfo extends AppCompatActivity {

    private TextView nglahNameTextView, thingTypeTextView, nglahDetailsTextView;
    private EditText nglahPriceEditText;
    private Button okButton;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private int nglahId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_user_info);

        Intent intent = getIntent();

        initWidgets();

        nglahId = intent.getIntExtra("nglah_id",0);
        nglahNameTextView.setText(intent.getStringExtra("nglah_name"));
        thingTypeTextView.setText(intent.getStringExtra("thing_type"));
        nglahDetailsTextView.setText(intent.getStringExtra("nglah_details"));

        Toast.makeText(this, ""+nglahId, Toast.LENGTH_SHORT).show();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        actionWidgets();
    }

    private void initWidgets(){

        nglahNameTextView = findViewById(R.id.tv_nglah_name);
        thingTypeTextView = findViewById(R.id.tv_thing_type);
        nglahDetailsTextView = findViewById(R.id.tv_details);

        nglahPriceEditText = findViewById(R.id.et_price);

        okButton = findViewById(R.id.bt_ok);

    }

    private void sendNglahRequest(String driverNationalId, int nglahId, double nglahPrice){

        Call<String> call = jsonPlaceHolderApi.sendNglahRequest(driverNationalId, nglahId, nglahPrice);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AcceptedUserInfo.this, "Request Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actionWidgets(){

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNglahRequest("55", nglahId, Double.parseDouble(nglahPriceEditText.getText().toString()));
            }
        });
    }
}
