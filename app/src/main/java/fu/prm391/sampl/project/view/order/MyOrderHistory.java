package fu.prm391.sampl.project.view.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_history);
        recyclerView = findViewById(R.id.recyclerViewMyOrdersHistory);

        String token = PreferencesHelpers.loadStringData(MyOrderHistory.this, "token");
        Call<OrderResponse> orderResponseCall = ApiClient.getOrderService().getOrdersHistory("bearer " + token);
        orderResponseCall.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

                if (response.isSuccessful()) {
                    ArrayList<Order> orders = (ArrayList<Order>) response.body().getData();
                    System.out.println(response.body().getData());
                    recyclerView.setAdapter(new MyOrdersHistoryAdapter(MyOrderHistory.this, orders));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrderHistory.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
            }
        });
        //back
        imageViewBack = findViewById(R.id.imageViewBackMyOrderHistory);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}