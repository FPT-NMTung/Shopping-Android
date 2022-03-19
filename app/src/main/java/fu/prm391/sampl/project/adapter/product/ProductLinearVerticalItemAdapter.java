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

public class ProductLinearVerticalItemAdapter extends RecyclerView.Adapter<ProductLinearVerticalItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> products;

    public ProductLinearVerticalItemAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.item_product, parent, false);
        ProductLinearVerticalItemAdapter.ViewHolder viewHolder = new ProductLinearVerticalItemAdapter.ViewHolder(productView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        if(product.getDiscount() != 0) {
            holder.productOldPrice.setText(StringHelpers.currencyFormatter(product.getPrice()));
            holder.productPrice.setText(StringHelpers.currencyFormatterWithPercent(product.getPrice(), product.getDiscount()));
            holder.productOldPrice.setPaintFlags(holder.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.productOldPrice.setVisibility(View.INVISIBLE);
            holder.productPrice.setText(StringHelpers.currencyFormatter(product.getPrice()));
        }

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
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private TextView productOldPrice;
        private ImageView productImage;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txtProductNameItem);
            productPrice = itemView.findViewById(R.id.txtProductPriceItem);
            productImage = itemView.findViewById(R.id.imgProductItem);
            productOldPrice = itemView.findViewById(R.id.txtProductOldPriceItem);
            constraintLayout = itemView.findViewById(R.id.consTraintLayoutProductItem);
        }
    }
}
