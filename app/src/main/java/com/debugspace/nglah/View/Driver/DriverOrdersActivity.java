package com.debugspace.nglah.View.Driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.debugspace.nglah.Adapters.DriverNotificationAdapter;
import com.debugspace.nglah.Model.NglahModel.Nglah;
import com.debugspace.nglah.Model.NglahModel.NglahOrdersService;
import com.debugspace.nglah.R;
import com.debugspace.nglah.Services.ApiClient;
import com.debugspace.nglah.Services.JsonPlaceHolderApi;
import com.debugspace.nglah.View.User_Main;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverOrdersActivity extends AppCompatActivity implements DriverNotificationAdapter.ListItemClickListener {

    private DriverNotificationAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView emptyTextView;
    private List<Nglah> nglahOrdersList;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_orders);
        sharedPreferences=getSharedPreferences("nglah_file",MODE_PRIVATE);

        // init all widgets in this activity
        initWidgets();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        // load nglahs orders for driver home page
        loadNglahInRecyclerView();
    }

    private void initWidgets(){
        mRecyclerView = findViewById(R.id.recyclerView);
        emptyTextView = findViewById(R.id.tv_empty_image);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Drivers...");
    }


    private void loadNglahInRecyclerView(){

        progressDialog.show();

        Call<NglahOrdersService> call = jsonPlaceHolderApi.getNglahOrders(sharedPreferences.getString("Driver_ID","null"));

        call.enqueue(new Callback<NglahOrdersService>() {
            @Override
            public void onResponse(Call<NglahOrdersService> call, Response<NglahOrdersService> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DriverOrdersActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                NglahOrdersService nglahOrdersService = response.body();

                nglahOrdersList = nglahOrdersService.getNglahOrdersInside();
                nglahOrdersList.addAll(nglahOrdersService.getNglahOrdersOutside());

                refreshRecyclerView();

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NglahOrdersService> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DriverOrdersActivity.this, "Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void refreshRecyclerView(){
        if (nglahOrdersList.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
        }else {
            emptyTextView.setVisibility(View.GONE);
            mAdapter = new DriverNotificationAdapter(nglahOrdersList, this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        String nglahName = nglahOrdersList.get(clickedItemIndex).getFirstName() +
                " " + nglahOrdersList.get(clickedItemIndex).getLastName();

        Intent intent = new Intent(DriverOrdersActivity.this, AcceptedUserInfo.class);
        intent.putExtra("nglah_id", nglahOrdersList.get(clickedItemIndex).getNglahId());
        intent.putExtra("nglah_name", nglahName);
        intent.putExtra("thing_type", nglahOrdersList.get(clickedItemIndex).getThingType());
        intent.putExtra("nglah_city", nglahOrdersList.get(clickedItemIndex).getCity());
        intent.putExtra("nglah_sector", nglahOrdersList.get(clickedItemIndex).getSector());
        intent.putExtra("nglah_date", nglahOrdersList.get(clickedItemIndex).getDate());
        intent.putExtra("nglah_time", nglahOrdersList.get(clickedItemIndex).getTime());
        intent.putExtra("nglah_details", nglahOrdersList.get(clickedItemIndex).getDetails());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(DriverOrdersActivity.this, User_Main.class));
        finish();
    }
}
