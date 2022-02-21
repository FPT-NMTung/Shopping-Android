package fu.prm391.sampl.project.adapter.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.view.product.SpecifyProduct;

public class ProductSimilarItemAdapter extends RecyclerView.Adapter<ProductSimilarItemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> products;

    public ProductSimilarItemAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.item_similar_products, parent, false);
        ProductSimilarItemAdapter.ViewHolder viewHolder = new ProductSimilarItemAdapter.ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(StringHelpers.currencyFormatterWithPercent(product.getPrice(), product.getDiscount()));
        Picasso.get().load(product.getImage()).fit().into(holder.productImage);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
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
        if (products.size() <= 5) {
            return products.size();
        } else {
            return 5;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productPrice;
//        private TextView productOldPrice;
        private ImageView productImage;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txtSimilarProductNameItem);
            productPrice = itemView.findViewById(R.id.txtSimilarProductPriceItem);
            productImage = itemView.findViewById(R.id.txtSimilarProductImageItem);
            constraintLayout = itemView.findViewById(R.id.constraintLayoutItemSimilarProduct);
        }
    }
}
