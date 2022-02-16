package fu.prm391.sampl.project.adapter.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.order.Order;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartViewHolder> {

    private List<Order> list;
    private LayoutInflater inflater;
    private Context context;

    public OrderCartAdapter(List<Order> list, Context context) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public OrderCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart_product, parent, false);
        return new OrderCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCartViewHolder holder, int position) {
        Order order = list.get(position);

        
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class OrderCartViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private ImageButton btnSub;
    private ImageButton btnPlus;

    private TextView name;
    private TextView price;
    private TextView quantity;

    public OrderCartViewHolder(@NonNull View itemView) {
        super(itemView);

        this.image = itemView.findViewById(R.id.imageItemCartProduct);
        this.btnSub = itemView.findViewById(R.id.imageItemCartSub);
        this.btnPlus = itemView.findViewById(R.id.imageItemCartPlus);

        this.name = itemView.findViewById(R.id.txtItemCartNameProduct);
        this.price = itemView.findViewById(R.id.txtItemCartPriceProduct);
        this.quantity = itemView.findViewById(R.id.txtItemCartQuantityProduct);
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
}
