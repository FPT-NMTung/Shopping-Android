package fu.prm391.sampl.project.adapter.category;

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
import fu.prm391.sampl.project.model.category.Category;
import fu.prm391.sampl.project.view.category.SpecifyCategory;

public class CategoryAllAdapter extends RecyclerView.Adapter<CategoryAllAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> categories;

    public CategoryAllAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View categoryView = inflater.inflate(R.layout.item_all_category, parent, false);
        CategoryAllAdapter.ViewHolder viewHolder = new CategoryAllAdapter.ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        if (category.getQuantity() == 0) {
            holder.numberOfItem.setText(category.getQuantity() + " Item");
        } else {
            holder.numberOfItem.setText(category.getQuantity() + " Items");
        }
        Picasso.get().load(category.getImage()).fit().into(holder.categoryImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpecifyCategory.class);
                intent.putExtra("specifyCategory", category);
                context.startActivity(intent);
//                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;
        private ImageView categoryImage;
        private TextView numberOfItem;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.imgAllCategory);
            categoryName = itemView.findViewById(R.id.txtAllCategoryName);
            numberOfItem = itemView.findViewById(R.id.txtNumberOfItemInCategory);
            cardView = itemView.findViewById(R.id.cardAllProduct);
        }
    }
}
