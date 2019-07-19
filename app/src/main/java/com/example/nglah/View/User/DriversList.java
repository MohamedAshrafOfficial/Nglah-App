package com.example.nglah.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.nglah.View.User_Main;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriversList extends AppCompatActivity implements NglahNotificationAdapter.ListItemClickListener{

    private NglahNotificationAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Driver> driverAcceptedList;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_list);

        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);

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

        Call<AcceptDriverService> call = jsonPlaceHolderApi.getDriversAccepted(sharedPreferences.getInt("Nglah_ID",0));

        call.enqueue(new Callback<AcceptDriverService>() {
            @Override
            public void onResponse(Call<AcceptDriverService> call, Response<AcceptDriverService> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DriversList.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                AcceptDriverService acceptDriverService = response.body();

                driverAcceptedList = acceptDriverService.getDriversAccepted();

                refreshRecyclerView();
            }

            @Override
            public void onFailure(Call<AcceptDriverService> call, Throwable t) {

            }
        });

    }

    private void refreshRecyclerView(){
        mAdapter = new NglahNotificationAdapter(driverAcceptedList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(this, ""+clickedItemIndex, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(DriversList.this, User_Main.class));
    }
}
