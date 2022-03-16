package fu.prm391.sampl.project.adapter.orders_history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.order.Order;
import fu.prm391.sampl.project.view.product.SpecifyProduct;

public class MyOrdersHistoryAdapter extends RecyclerView.Adapter<MyOrdersHistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Order> orders;

    public MyOrdersHistoryAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.item_my_orders_history_product, parent, false);
        MyOrdersHistoryAdapter.ViewHolder viewHolder = new MyOrdersHistoryAdapter.ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.productName.setText(order.getProduct().getName());
        holder.productPrice.setText(StringHelpers.currencyFormatter(order.getProduct().getPrice()));
        holder.quantity.setText(String.valueOf(order.getQuantity()));
        Picasso.get().load(order.getProduct().getImage()).fit().into(holder.productImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpecifyProduct.class);
                intent.putExtra("productId", order.getProduct().getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private TextView quantity;
        private ImageView productImage;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txtProductNameMyOrderHistoryItem);
            productPrice = itemView.findViewById(R.id.txtProductPriceMyOrderHistoryItem);
            productImage = itemView.findViewById(R.id.imgProductMyOrdersHistoryItem);
            quantity = itemView.findViewById(R.id.txtQuantityMyOrderHistory);
            cardView = itemView.findViewById(R.id.cardOrdersHistoryItem);
        }
    }
}
