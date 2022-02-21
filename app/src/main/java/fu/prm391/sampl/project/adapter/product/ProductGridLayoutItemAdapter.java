package fu.prm391.sampl.project.adapter.product;

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
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.view.product.SpecifyProduct;

public class ProductGridLayoutItemAdapter extends RecyclerView.Adapter<ProductGridLayoutItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> products;

    public ProductGridLayoutItemAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.item_gridlayout_product, parent, false);
        ProductGridLayoutItemAdapter.ViewHolder viewHolder = new ProductGridLayoutItemAdapter.ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productDiscount.setText(StringHelpers.percentageNumber(product.getDiscount()));
        if(product.getDiscount() != 0) {
            holder.productPrice.setText(StringHelpers.currencyFormatterWithPercent(product.getPrice(), product.getDiscount()));
            holder.productDiscount.setVisibility(View.VISIBLE);
            holder.discountTag.setVisibility(View.VISIBLE);
        } else {
            holder.productPrice.setText(StringHelpers.currencyFormatter(product.getPrice()));
            holder.productDiscount.setVisibility(View.INVISIBLE);
            holder.discountTag.setVisibility(View.INVISIBLE);
        }
        Picasso.get().load(product.getImage()).fit().into(holder.productImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpecifyProduct.class);
                intent.putExtra("productId", product.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productDiscount;
        CardView cardView;
        ImageView discountTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageProductItemGrid);
            productName = itemView.findViewById(R.id.txtProductItemGridName);
            productPrice = itemView.findViewById(R.id.txtProductItemGridPrice);
            productDiscount = itemView.findViewById(R.id.txtProductItemGridDiscount);
            cardView = itemView.findViewById(R.id.cardProductItemGridView);
            discountTag = itemView.findViewById(R.id.imageViewProductItemDiscountTag);
        }
    }
}
