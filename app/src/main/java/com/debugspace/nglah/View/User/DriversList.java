package com.debugspace.nglah.View.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.debugspace.nglah.Adapters.NglahNotificationAdapter;
import com.debugspace.nglah.Model.DriverModel.AcceptDriverService;
import com.debugspace.nglah.Model.DriverModel.Driver;
import com.debugspace.nglah.R;
import com.debugspace.nglah.Services.ApiClient;
import com.debugspace.nglah.Services.JsonPlaceHolderApi;
import com.debugspace.nglah.View.User_Main;

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
    private ImageView imageView;
    private TextView emptyTextView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_list);

        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);

        imageView=findViewById(R.id.imgBackRecent);
        // init all widgets in this activity
        initWidgets();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        // load drivers that accepted the request
        loadDriversInRecyclerView();

    }


    private void initWidgets(){
        mRecyclerView = findViewById(R.id.recyclerView);
        emptyTextView = findViewById(R.id.tv_empty_image);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriversList.this, User_Main.class));
                finish();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("loading...");
    }


    private void loadDriversInRecyclerView(){

        progressDialog.show();
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
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AcceptDriverService> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

    }

    private void refreshRecyclerView(){
        if (driverAcceptedList.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
        }else {
            emptyTextView.setVisibility(View.GONE);
            mAdapter = new NglahNotificationAdapter(driverAcceptedList, this);
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(this, driverAcceptedList.get(clickedItemIndex).getFirstName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(DriversList.this, User_Main.class));
        finish();
    }
}
