package fu.prm391.sampl.project.model.product;

import android.content.Context;
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

public class ProductGridRecyclerViewAdapter extends RecyclerView.Adapter<ProductGridRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> products;

    public ProductGridRecyclerViewAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.grid_product_item, parent, false);
        ProductGridRecyclerViewAdapter.ViewHolder viewHolder = new ProductGridRecyclerViewAdapter.ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(StringHelpers.currencyFormatter(product.getPrice()));
        Picasso.get().load(product.getImage()).fit().into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView productPrice;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageProductItemGrid);
            productName = itemView.findViewById(R.id.txtProductItemGridName);
            productPrice = itemView.findViewById(R.id.txtProductItemGridPrice);
            cardView = itemView.findViewById(R.id.cardProductItemGridView);
        }
    }
}
