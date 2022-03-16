package fu.prm391.sampl.project.adapter.order;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.order.Order;
import fu.prm391.sampl.project.model.order.decrease_quantity.DecreaseQuantityRequest;
import fu.prm391.sampl.project.model.order.decrease_quantity.DecreaseQuantityResponse;
import fu.prm391.sampl.project.model.order.increase_quantity.IncreaseQuantityRequest;
import fu.prm391.sampl.project.model.order.increase_quantity.IncreaseQuantityResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.fragment.Cart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartViewHolder> {

    private List<Order> list;
    private LayoutInflater inflater;
    private Context context;
    private View cartView;
    private Fragment fragment;

    public OrderCartAdapter(List<Order> list, Context context, Fragment fragment) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public OrderCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart_product, parent, false);
        this.cartView = inflater.inflate(R.layout.fragment_cart, parent, false);
        return new OrderCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCartViewHolder holder, int position) {
        int tempPosition = position;

        Order order = list.get(position);

        Picasso.get().load(order.getProduct().getImage()).fit().into(holder.getImage());
        holder.getName().setText(order.getProduct().getName());
        holder.getQuantity().setText(order.getQuantity() + "");
        if (order.getProduct().getDiscount() != 0) {
            holder.getPriceBase().setText("(" + StringHelpers.currencyFormatter(order.getProduct().getPrice()) + ")");
        } else {
            holder.getPriceBase().setText("");
        }
        double priceTemp = (order.getProduct().getPrice() * (100 - order.getProduct().getDiscount())) / 100;
        holder.getPriceEach().setText(StringHelpers.currencyFormatter(priceTemp));
        holder.getPrice().setText(StringHelpers.currencyFormatter((priceTemp) * order.getQuantity()));

        String token = PreferencesHelpers.loadStringData(context, "token");

        holder.getBtnPlus().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order.getQuantity() < 10 && order.getProduct().getQuantity() > order.getQuantity()) {
                    int quantity = order.getQuantity() + 1;
                    order.setQuantity(quantity);
                    ((Cart) fragment).renderCheckout(list);
                    renderItem(holder, tempPosition, priceTemp);
                    Call<IncreaseQuantityResponse> call = ApiClient.getOrderService().increaseQuantityOrder("Bearer " + token, new IncreaseQuantityRequest(order.getProduct().getId()));
                    call.enqueue(new Callback<IncreaseQuantityResponse>() {
                        @Override
                        public void onResponse(Call<IncreaseQuantityResponse> call, Response<IncreaseQuantityResponse> response) {
                            if (response.isSuccessful()) {
                                Log.i("IncreaseQuantity", "DONE");
                            }
                        }

                        @Override
                        public void onFailure(Call<IncreaseQuantityResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        holder.getBtnSub().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order.getQuantity() > 1) {
                    order.setQuantity(order.getQuantity() - 1);
                    ((Cart) fragment).renderCheckout(list);
                    renderItem(holder, tempPosition, priceTemp);
                    Call<DecreaseQuantityResponse> call = ApiClient.getOrderService().decreaseQuantityOrder("Bearer " + token, new DecreaseQuantityRequest(order.getProduct().getId()));
                    call.enqueue(new Callback<DecreaseQuantityResponse>() {
                        @Override
                        public void onResponse(Call<DecreaseQuantityResponse> call, Response<DecreaseQuantityResponse> response) {
                            Log.i("DecreaseQuantity", "DONE");
                        }

                        @Override
                        public void onFailure(Call<DecreaseQuantityResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void renderItem(OrderCartViewHolder holder, int position, double price) {
        holder.getPrice().setText(StringHelpers.currencyFormatter(list.get(position).getQuantity() * price));
        holder.getQuantity().setText(list.get(position).getQuantity() + "");
    }
}

class OrderCartViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private ImageButton btnSub;
    private ImageButton btnPlus;

    private TextView name;
    private TextView price;
    private TextView priceBase;
    private TextView priceEach;
    private TextView quantity;

    public OrderCartViewHolder(@NonNull View itemView) {
        super(itemView);

        this.image = itemView.findViewById(R.id.imageItemCartProduct);
        this.btnSub = itemView.findViewById(R.id.imageItemCartSub);
        this.btnPlus = itemView.findViewById(R.id.imageItemCartPlus);

        this.name = itemView.findViewById(R.id.txtItemCartNameProduct);
        this.price = itemView.findViewById(R.id.txtItemCartPriceProduct);
        this.priceEach = itemView.findViewById(R.id.txtItemCartPriceEachProduct);
        this.priceBase = itemView.findViewById(R.id.txtItemCartPriceBaseProduct);
        this.quantity = itemView.findViewById(R.id.txtItemCartQuantityProduct);

        priceBase.setPaintFlags(priceBase.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public ImageView getImage() {
        return image;
    }

    public ImageButton getBtnSub() {
        return btnSub;
    }

    public ImageButton getBtnPlus() {
        return btnPlus;
    }

    public TextView getName() {
        return name;
    }

    public TextView getPrice() {
        return price;
    }

    public TextView getQuantity() {
        return quantity;
    }

    public TextView getPriceBase() {
        return priceBase;
    }

    public TextView getPriceEach() {
        return priceEach;
    }
}
