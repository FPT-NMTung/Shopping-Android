package fu.prm391.sampl.project.model.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.StringHelpers;

public class ProductSuperSaleAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> products;

    public ProductSuperSaleAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.grid_product_item, null);
            holder = new ViewHolder();
            holder.productImage = view.findViewById(R.id.imageProductItemGrid);
            holder.productName = view.findViewById(R.id.txtProductItemGridName);
            holder.productPrice = view.findViewById(R.id.txtProductItemGridPrice);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Product product = this.products.get(i);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(StringHelpers.currencyFormatter(product.getPrice()));
        Picasso.get().load(product.getImage()).fit().into(holder.productImage);
        return view;
    }

    public class ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
    }
}
