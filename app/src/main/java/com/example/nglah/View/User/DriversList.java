package com.example.nglah.View.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.nglah.Adapters.NglahNotificationAdapter;
import com.example.nglah.Model.DriverModel.AcceptDriverService;
import com.example.nglah.Model.DriverModel.Driver;
import com.example.nglah.R;
import com.example.nglah.Services.ApiClient;
import com.example.nglah.Services.JsonPlaceHolderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriversList extends AppCompatActivity {

    private NglahNotificationAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Driver> driverAcceptedList;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_list);

        // init all widgets in this activity
        initWidgets();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        // load drivers that accepted the request
        loadDriversInRecyclerView();

    }


    private void initWidgets(){
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }


    private void loadDriversInRecyclerView(){

        Call<AcceptDriverService> call = jsonPlaceHolderApi.getDriversAccepted(2);

        call.enqueue(new Callback<AcceptDriverService>() {
            @Override
            public void onResponse(Call<AcceptDriverService> call, Response<AcceptDriverService> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DriversList.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                AcceptDriverService acceptDriverService = response.body();

                driverAcceptedList = acceptDriverService.getDriversAccepted();

                mAdapter = new NglahNotificationAdapter(driverAcceptedList, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<AcceptDriverService> call, Throwable t) {

            }
        });

    }
}
