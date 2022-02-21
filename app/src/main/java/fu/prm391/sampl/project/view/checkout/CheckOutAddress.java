package fu.prm391.sampl.project.view.checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.checkout.CheckoutAddressAdapter;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.address.Address;
import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.address.ProfileShippingAddress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutAddress extends AppCompatActivity {

    private Button btnCoBack;
    private StepView stepView;

    private ConstraintLayout layoutAddressCheckout;
    private ConstraintLayout layoutConfirmCheckout;

    private ProgressBar progressBarCheckoutAddress;

    private RecyclerView recyclerViewAddressCheckout;

    private Button btnCheckoutAddressSubmit;

    private String token;

    private Address selectAddress;

    private List<Address> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_address);

        this.btnCoBack = findViewById(R.id.btnCoBack);
        this.stepView = findViewById(R.id.stepView);

        this.layoutAddressCheckout = findViewById(R.id.layoutAddressCheckout);
        this.layoutConfirmCheckout = findViewById(R.id.layoutConfirmCheckout);

        this.progressBarCheckoutAddress = findViewById(R.id.progressBarCheckoutAddress);

        this.recyclerViewAddressCheckout = findViewById(R.id.recyclerViewAddressCheckout);

        this.btnCheckoutAddressSubmit = findViewById(R.id.btnCheckoutAddressSubmit);

        this.token = PreferencesHelpers.loadStringData(CheckOutAddress.this, "token");

        this.selectAddress = null;

        this.list = null;

        loadDataAddress();

        setStepView();
        setEventStepView();

        setEventBtnNextStep();
        setEventBtnBack();
    }

    private void setSelectAddress(Address address) {
        this.selectAddress = address;
    }

    private void setEventBtnNextStep() {
        btnCheckoutAddressSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepView.go(1, true);
                layoutAddressCheckout.setVisibility(View.INVISIBLE);
                layoutConfirmCheckout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadDataAddress() {
        this.btnCheckoutAddressSubmit.setEnabled(false);
        Call<GetAllAddressResponse> call = ApiClient.getAddressService().getAllAddress("Bearer " + token);
        call.enqueue(new Callback<GetAllAddressResponse>() {
            @Override
            public void onResponse(Call<GetAllAddressResponse> call, Response<GetAllAddressResponse> response) {
                if (response.isSuccessful()) {
                    list = response.body().getListAddress();
                    CheckoutAddressAdapter checkoutAddressAdapter = new CheckoutAddressAdapter(list, CheckOutAddress.this);
                    recyclerViewAddressCheckout.setLayoutManager(new LinearLayoutManager(CheckOutAddress.this));
                    recyclerViewAddressCheckout.setAdapter(checkoutAddressAdapter);

                    progressBarCheckoutAddress.setVisibility(View.INVISIBLE);

                    btnCheckoutAddressSubmit.setEnabled(list.size() != 0);

                    for (int i = 0; i < list.size(); i++) {
                        Address temp = list.get(i);

                        if (temp.getIsDefault() == 1) {
                            selectAddress = temp;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllAddressResponse> call, Throwable t) {

            }
        });
    }

    private void setEventBtnBack() {
        this.btnCoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setStepView() {
        this.stepView.setSteps(new ArrayList<String>() {{
            add("Personal Info");
            add("Confirmation");
        }});
    }

    private void setEventStepView() {
        this.stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                if (list.size() != 0) {
                    stepView.go(step, true);

                    switch (step) {
                        case 0:
                            layoutAddressCheckout.setVisibility(View.VISIBLE);
                            layoutConfirmCheckout.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            layoutAddressCheckout.setVisibility(View.INVISIBLE);
                            layoutConfirmCheckout.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });
    }
}