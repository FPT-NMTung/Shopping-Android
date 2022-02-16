package fu.prm391.sampl.project.view.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.address.ShippingAddressAdapter;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.address.Address;
import fu.prm391.sampl.project.model.address.delete_address.DeleteAddressRequest;
import fu.prm391.sampl.project.model.address.delete_address.DeleteAddressResponse;
import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileShippingAddress extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Button btnCreateNewAddress;
    private Button btnPsaBack;

    private ShippingAddressAdapter shippingAddressAdapter;

    private List<Address> list;

    private String token;

    private ProgressBar progressBarSpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_shipping_address);

        token = PreferencesHelpers.loadStringData(ProfileShippingAddress.this, "token");

        recyclerView = findViewById(R.id.recyclerViewAddress);

        progressBarSpa = findViewById(R.id.progressBarSpa);

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
        progressBarSpa.setVisibility(View.VISIBLE);
        Call<GetAllAddressResponse> call = ApiClient.getAddressService().getAllAddress("Bearer " + token);
        call.enqueue(new Callback<GetAllAddressResponse>() {
            @Override
            public void onResponse(Call<GetAllAddressResponse> call, Response<GetAllAddressResponse> response) {
                list = response.body().getListAddress();
                shippingAddressAdapter = new ShippingAddressAdapter(list, ProfileShippingAddress.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProfileShippingAddress.this));
                recyclerView.setAdapter(shippingAddressAdapter);

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(psaItemTouchHelper);
                itemTouchHelper.attachToRecyclerView(recyclerView);

                progressBarSpa.setVisibility(View.INVISIBLE);
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

    ItemTouchHelper.SimpleCallback psaItemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            Address address = deleteItem(position);
            if (address.getIsDefault() == 1) {
                Toast.makeText(ProfileShippingAddress.this, "Cant delete default address", Toast.LENGTH_SHORT).show();
                revertItem(position, address);
            } else {
                Call<DeleteAddressResponse> call = ApiClient.getAddressService().deleteAddress("Bearer " + token, new DeleteAddressRequest(address.getId()));
                call.enqueue(new Callback<DeleteAddressResponse>() {
                    @Override
                    public void onResponse(Call<DeleteAddressResponse> call, Response<DeleteAddressResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProfileShippingAddress.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            revertItem(position, address);
                            Toast.makeText(ProfileShippingAddress.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteAddressResponse> call, Throwable t) {
                        revertItem(position, address);
                        Toast.makeText(ProfileShippingAddress.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    private void revertItem(int position, Address address) {
        list.add(position, address);
        shippingAddressAdapter.notifyItemInserted(position);
        recyclerView.setAdapter(shippingAddressAdapter);
    }

    private Address deleteItem(int position) {
        Address address = list.get(position);
        list.remove(position);
        shippingAddressAdapter.notifyItemRemoved(position);
        recyclerView.setAdapter(shippingAddressAdapter);
        return address;
    }
}