package fu.prm391.sampl.project.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.order.OrderCartAdapter;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.order.Order;
import fu.prm391.sampl.project.model.order.delete_order.DeleteOrderRequest;
import fu.prm391.sampl.project.model.order.delete_order.DeleteOrderResponse;
import fu.prm391.sampl.project.model.order.get_all_order.GetAllOrderResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.account.Login;
import fu.prm391.sampl.project.view.checkout.CheckOutAddress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String token;

    private RecyclerView recyclerViewMainCart;

    private ProgressBar progressBarCart;

    private TextView txtCartTotalValue;
    private TextView txtCartEstimatingTaxValue;
    private TextView txtCartShippingFeeValue;
    private TextView txtCardSubTotalValue;

    private Button btnCartCheckout;

    private Call<GetAllOrderResponse> call;

    private List<Order> list;

    private OrderCartAdapter orderCartAdapter;

    private double total;
    private double subTotal;
    private double shippingFee;
    private double tax;

    public Cart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cart.
     */
    // TODO: Rename and change types and number of parameters
    public static Cart newInstance(String param1, String param2) {
        Cart fragment = new Cart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.token = PreferencesHelpers.loadStringData(getContext(), "token");

        this.recyclerViewMainCart = view.findViewById(R.id.recyclerViewMainCart);

        this.progressBarCart = view.findViewById(R.id.progressBarCart);

        this.txtCartTotalValue = view.findViewById(R.id.txtCartTotalValue);
        this.txtCartEstimatingTaxValue = view.findViewById(R.id.txtCartEstimatingTaxValue);
        this.txtCartShippingFeeValue = view.findViewById(R.id.txtCartShippingFeeValue);
        this.txtCardSubTotalValue = view.findViewById(R.id.txtCardSubTotalValue);

        this.btnCartCheckout = view.findViewById(R.id.btnCartCheckout);

        if (token.equals("")) {
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);
        } else {
            loadAllListOrder();
            setEventBtnCheckout();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (call != null) {
            call.cancel();
        }
    }

    private void setEventBtnCheckout() {
        btnCartCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CheckOutAddress.class);

                intent.putExtra("subTotal", subTotal);
                intent.putExtra("fee", shippingFee);
                intent.putExtra("tax", tax);
                intent.putExtra("total", total);

                startActivity(intent);
            }
        });
    }

    private void loadAllListOrder() {
        progressBarCart.setVisibility(View.VISIBLE);
        call = ApiClient.getOrderService().getAllOrder("Bearer " + this.token);
        call.enqueue(new Callback<GetAllOrderResponse>() {
            @Override
            public void onResponse(Call<GetAllOrderResponse> call, Response<GetAllOrderResponse> response) {
                if (response.isSuccessful()) {
                    list = response.body().getData();
                    orderCartAdapter = new OrderCartAdapter(list, getContext(), Cart.this);
                    recyclerViewMainCart.setAdapter(orderCartAdapter);
                    recyclerViewMainCart.setLayoutManager(new LinearLayoutManager(getContext()));
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(cartSimpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerViewMainCart);
                    progressBarCart.setVisibility(View.INVISIBLE);

                    renderCheckout(list);

                    if (list.size() != 0) {
                        btnCartCheckout.setEnabled(true);
                    }
                } else {
                    progressBarCart.setVisibility(View.INVISIBLE);
                    Log.e("onResponse", "asdasdasdadnasudgbasbsdugfyhbsuhfgbsdufhg");
                }
            }

            @Override
            public void onFailure(Call<GetAllOrderResponse> call, Throwable t) {
                Log.e("onResponse", t.toString());
            }
        });
    }

    public void renderCheckout(List<Order> list) {
        total = 0;
        subTotal = 0;
        shippingFee = (list.size() == 0) ? 0 : 2;

        for (int index = 0; index < list.size(); index++) {
            Order order = list.get(index);
            subTotal += ((order.getProduct().getPrice() * (100 - order.getProduct().getDiscount())) / 100) * order.getQuantity();
        }

        tax = subTotal / 10;
        total = subTotal + shippingFee + tax;

        txtCardSubTotalValue.setText(StringHelpers.currencyFormatter((double) subTotal));
        txtCartEstimatingTaxValue.setText(StringHelpers.currencyFormatter((double) tax));
        txtCartShippingFeeValue.setText(StringHelpers.currencyFormatter((double) shippingFee));
        txtCartTotalValue.setText(StringHelpers.currencyFormatter((double) total));

        btnCartCheckout.setEnabled(list.size() != 0);
    }

    ItemTouchHelper.SimpleCallback cartSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            Call<DeleteOrderResponse> call = ApiClient.getOrderService().deleteOrder("Bearer " + token, new DeleteOrderRequest(list.get(position).getProduct().getId()));
            call.enqueue(new Callback<DeleteOrderResponse>() {
                @Override
                public void onResponse(Call<DeleteOrderResponse> call, Response<DeleteOrderResponse> response) {
                    if (response.isSuccessful()) {
                        Log.i("Delete order", "Delete order successfully");
                    } else {
                        Log.e("Delete order", "Delete order failure" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<DeleteOrderResponse> call, Throwable t) {
                    Log.e("Delete order", "Delete order failure: " + t.toString());
                }
            });

            list.remove(position);
            orderCartAdapter.notifyItemRemoved(position);
            recyclerViewMainCart.setAdapter(orderCartAdapter);
            renderCheckout(list);
        }
    };

}