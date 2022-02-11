package fu.prm391.sampl.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import fu.prm391.sampl.project.adapter.ShippingAddressAdapter;
import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileShippingAddress extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shipping_address);

        recyclerView = findViewById(R.id.recyclerViewAddress);
        Call<GetAllAddressResponse> call = ApiClient.getAddressService().getAllAddress("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywiZW1haWwiOiJubXR1bmdvZmZpY2lhbEBnbWFpbC5jb20iLCJpYXQiOjE2NDQyMzM1OTl9.X7sI6-AIyKQHNj6-vlBHuuplFmTEkLnL5zkZfn5Dnzs");
        call.enqueue(new Callback<GetAllAddressResponse>() {
            @Override
            public void onResponse(Call<GetAllAddressResponse> call, Response<GetAllAddressResponse> response) {
                Log.i("Size: ", response.body().getListAddress().size() + "");

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
}