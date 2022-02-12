package fu.prm391.sampl.project.view.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.ShippingAddressAdapter;
import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileShippingAddress extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Button btnCreateNewAddress;
    private Button btnPsaBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shipping_address);

        recyclerView = findViewById(R.id.recyclerViewAddress);

        btnCreateNewAddress = findViewById(R.id.btnPsaNewAddress);
        btnPsaBack = findViewById(R.id.btnPsaBack);

        setEventBtnCreateNewAddress();
        setEventBtnBack();
    }

    @Override
    protected void onStart() {
        super.onStart();

        clearListInsideRecycleView();
        loadListToRecycleView();
    }

    private void loadListToRecycleView() {
        Call<GetAllAddressResponse> call = ApiClient.getAddressService().getAllAddress("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywiZW1haWwiOiJubXR1bmdvZmZpY2lhbEBnbWFpbC5jb20iLCJpYXQiOjE2NDQyMzM1OTl9.X7sI6-AIyKQHNj6-vlBHuuplFmTEkLnL5zkZfn5Dnzs");
        call.enqueue(new Callback<GetAllAddressResponse>() {
            @Override
            public void onResponse(Call<GetAllAddressResponse> call, Response<GetAllAddressResponse> response) {
                ShippingAddressAdapter shippingAddressAdapter = new ShippingAddressAdapter(response.body().getListAddress(), ProfileShippingAddress.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProfileShippingAddress.this));
                recyclerView.setAdapter(shippingAddressAdapter);
            }

            @Override
            public void onFailure(Call<GetAllAddressResponse> call, Throwable t) {
                Log.e("GetAllAddressResponse: ", "Failed");
            }
        });
    }

    private void setEventBtnCreateNewAddress() {
        btnCreateNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileShippingAddress.this, CreateNewAddress.class);
                startActivity(intent);
            }
        });
    }

    private void setEventBtnBack() {
        btnPsaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void clearListInsideRecycleView() {
        ShippingAddressAdapter temp = new ShippingAddressAdapter(new ArrayList<>(), ProfileShippingAddress.this);
        recyclerView.setAdapter(temp);
    }
}