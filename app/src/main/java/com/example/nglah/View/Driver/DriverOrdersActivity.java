package com.example.nglah.View.Driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.nglah.Adapters.DriverNotificationAdapter;
import com.example.nglah.Model.NglahModel.Nglah;
import com.example.nglah.Model.NglahModel.NglahOrdersService;
import com.example.nglah.R;
import com.example.nglah.Services.ApiClient;
import com.example.nglah.Services.JsonPlaceHolderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverOrdersActivity extends AppCompatActivity implements DriverNotificationAdapter.ListItemClickListener {

    private DriverNotificationAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Nglah> nglahOrdersList;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_orders);

        // init all widgets in this activity
        initWidgets();

        // get api client to connect server
        jsonPlaceHolderApi = ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        // load nglahs orders for driver home page
        loadNglahInRecyclerView();
    }

    private void initWidgets(){
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }


    private void loadNglahInRecyclerView(){

        Call<NglahOrdersService> call = jsonPlaceHolderApi.getNglahOrders("55");

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
            }

            @Override
            public void onFailure(Call<NglahOrdersService> call, Throwable t) {
                Toast.makeText(DriverOrdersActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void refreshRecyclerView(){
        mAdapter = new DriverNotificationAdapter(nglahOrdersList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        String nglahName = nglahOrdersList.get(clickedItemIndex).getFirstName() +
                " " + nglahOrdersList.get(clickedItemIndex).getLastName();

        Intent intent = new Intent(DriverOrdersActivity.this, AcceptedUserInfo.class);
        intent.putExtra("nglah_id", nglahOrdersList.get(clickedItemIndex).getNglahId());
        intent.putExtra("nglah_name", nglahName);
        intent.putExtra("thing_type", nglahOrdersList.get(clickedItemIndex).getThingType());
        intent.putExtra("nglah_details", nglahOrdersList.get(clickedItemIndex).getDetails());
        startActivity(intent);
    }
}
