package fu.prm391.sampl.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.Product;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder> {

    private List<Product> list;

    public HomeProductAdapter(List<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_item, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        productViewHolder.item_product_name.setText(list.get(position).getName());
        productViewHolder.item_product_price.setText("$ " + list.get(position).getPrice().toString());
        Picasso.get().load(list.get(position).getImage()).fit().into(productViewHolder.item_product_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView item_product_name;
        TextView item_product_price;
        ImageView item_product_image;

        ProductViewHolder(View itemView) {
            super(itemView);

            item_product_name = (TextView) itemView.findViewById(R.id.item_product_name);
            item_product_price = (TextView) itemView.findViewById(R.id.item_product_price);
            item_product_image = (ImageView) itemView.findViewById(R.id.item_product_image);
        }
    }
}
