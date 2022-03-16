package fu.prm391.sampl.project.view.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.orders_history.MyOrdersHistoryAdapter;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.order.Order;
import fu.prm391.sampl.project.model.order.OrderResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderHistory extends AppCompatActivity {

    private ImageView imageViewBack;
    private RecyclerView recyclerView;
    private ConstraintLayout loadingConstraintLayout, noOrderConstraintLayout;
    private String token;
    private SegmentedButtonGroup segmentedButtonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_history);
        recyclerView = findViewById(R.id.recyclerViewMyOrdersHistory);
        loadingConstraintLayout = findViewById(R.id.loadingConstraintLayoutMyOrderHistory);
        loadingConstraintLayout.setVisibility(View.VISIBLE);
        noOrderConstraintLayout = findViewById(R.id.noOrderHistoryConstraintLayout);
        noOrderConstraintLayout.setVisibility(View.GONE);

        token = PreferencesHelpers.loadStringData(MyOrderHistory.this, "token");

        setupSegmentedButton();
        backAction();
    }

    private void setupSegmentedButton() {
        segmentedButtonGroup = findViewById(R.id.segmentedButtonGroup);
        segmentedButtonGroup.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(int position) {
                loadListOrderHistories(position);
            }
        });

        segmentedButtonGroup.setPosition(0, true);
    }

    private void loadListOrderHistories(int status) {
        loadingConstraintLayout.setVisibility(View.VISIBLE);
        Call<OrderResponse> orderResponseCall = ApiClient.getOrderService().getOrdersHistoryByStatus("bearer " + token, status);
        orderResponseCall.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Order> orders = (ArrayList<Order>) response.body().getData();
                    recyclerView.setAdapter(new MyOrdersHistoryAdapter(MyOrderHistory.this, orders));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrderHistory.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    loadingConstraintLayout.setVisibility(View.GONE);
                    if (orders.size() == 0) {
                        noOrderConstraintLayout.setVisibility(View.VISIBLE);
                    } else {
                        noOrderConstraintLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
            }
        });
    }

    private void backAction() {
        imageViewBack = findViewById(R.id.imageViewBackMyOrderHistory);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}