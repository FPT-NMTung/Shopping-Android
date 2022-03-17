package fu.prm391.sampl.project.view.checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.checkout.CheckoutAddressAdapter;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.address.Address;
import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import fu.prm391.sampl.project.model.order.check_out.CheckOutOrderRequest;
import fu.prm391.sampl.project.model.order.check_out.CheckOutOrderResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.address.CreateNewAddress;
import fu.prm391.sampl.project.view.address.ProfileShippingAddress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutAddress extends AppCompatActivity {

    private Button btnCoBack;
    private Button btnPlaceOrder;

    private StepView stepView;

    private ConstraintLayout layoutAddressCheckout;
    private ConstraintLayout layoutConfirmCheckout;

    private ProgressBar progressBarCheckoutAddress;

    private RecyclerView recyclerViewAddressCheckout;

    private Button btnCheckoutAddressSubmit;
    private Button caBtnNewAddress;

    private String token;

    private Address selectAddress;

    private List<Address> list;

    private double subTotal;
    private double fee;
    private double tax;
    private double total;

    private TextView txtConfirmationSubTotalValue;
    private TextView txtConfirmationShippingFeeValue;
    private TextView txtConfirmationEstimatingTaxValue;
    private TextView txtConfirmationTotalValue;

    private TextView txtCheckoutConfirmationAddressName;
    private TextView txtCheckoutConfirmationAddressDetail;
    private TextView txtCheckoutConfirmationAddressPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_address);

        this.btnCoBack = findViewById(R.id.btnCoBack);
        this.btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        this.stepView = findViewById(R.id.stepView);

        this.layoutAddressCheckout = findViewById(R.id.layoutAddressCheckout);
        this.layoutConfirmCheckout = findViewById(R.id.layoutConfirmCheckout);

        this.progressBarCheckoutAddress = findViewById(R.id.progressBarCheckoutAddress);

        this.recyclerViewAddressCheckout = findViewById(R.id.recyclerViewAddressCheckout);

        this.btnCheckoutAddressSubmit = findViewById(R.id.btnCheckoutAddressSubmit);
        this.caBtnNewAddress = findViewById(R.id.caBtnNewAddress);

        this.txtConfirmationSubTotalValue = findViewById(R.id.txtConfirmationSubTotalValue);
        this.txtConfirmationShippingFeeValue = findViewById(R.id.txtConfirmationShippingFeeValue);
        this.txtConfirmationEstimatingTaxValue = findViewById(R.id.txtConfirmationEstimatingTaxValue);
        this.txtConfirmationTotalValue = findViewById(R.id.txtConfirmationTotalValue);

        this.txtCheckoutConfirmationAddressName = findViewById(R.id.txtCheckoutConfirmationAddressName);
        this.txtCheckoutConfirmationAddressDetail = findViewById(R.id.txtCheckoutConfirmationAddressDetail);
        this.txtCheckoutConfirmationAddressPhone = findViewById(R.id.txtCheckoutConfirmationAddressPhone);

        this.token = PreferencesHelpers.loadStringData(CheckOutAddress.this, "token");

        this.selectAddress = null;

        this.list = null;

        loadDataAddress();
        loadDataCredit();

        setStepView();
        setEventStepView();

        setEventBtnNextStep();
        setEventBtnBack();
        setEventBtnPlaceOrder();
        setEventBtnNewAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataAddress();
    }

    private void setEventBtnNewAddress() {
        caBtnNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutAddress.this, CreateNewAddress.class);
                startActivity(intent);
            }
        });
    }

    private void setEventBtnPlaceOrder() {
        this.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlaceOrder.setEnabled(false);
                Call<CheckOutOrderResponse> call = ApiClient.getOrderService().checkout("Bearer " + token, new CheckOutOrderRequest(selectAddress.getId()));
                call.enqueue(new Callback<CheckOutOrderResponse>() {
                    @Override
                    public void onResponse(Call<CheckOutOrderResponse> call, Response<CheckOutOrderResponse> response) {
                        if (response.isSuccessful()) {
                            btnPlaceOrder.setEnabled(true);
                            Intent intent = new Intent(CheckOutAddress.this, CheckOutSuccess.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CheckOutAddress.this, "Product quantity is not enough", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckOutOrderResponse> call, Throwable t) {
                        Toast.makeText(CheckOutAddress.this, "Product quantity is not enough", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    public void setSelectAddress(Address address) {
        this.selectAddress = address;

        this.txtCheckoutConfirmationAddressName.setText(address.getFullName());
        this.txtCheckoutConfirmationAddressDetail.setText(address.getDetail()
                + ", " + address.getWardPrefix() + " " + address.getWardName()
                + ", " + address.getDistrictPrefix() + " " + address.getDistrictName()
                + ", " + address.getProvinceName());
        this.txtCheckoutConfirmationAddressPhone.setText(address.getPhone());
    }

    private void loadDataCredit() {
        Intent intent = getIntent();

        this.subTotal = intent.getDoubleExtra("subTotal", 0);
        this.fee = intent.getDoubleExtra("fee", 0);
        this.tax = intent.getDoubleExtra("tax", 0);
        this.total = intent.getDoubleExtra("total", 0);

        Log.d("TAG", "loadDataCredit: " + total);

        this.txtConfirmationSubTotalValue.setText(StringHelpers.currencyFormatter(this.subTotal));
        this.txtConfirmationShippingFeeValue.setText(StringHelpers.currencyFormatter(this.fee));
        this.txtConfirmationEstimatingTaxValue.setText(StringHelpers.currencyFormatter(this.tax));
        this.txtConfirmationTotalValue.setText(StringHelpers.currencyFormatter(this.total));
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
                            setSelectAddress(temp);
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