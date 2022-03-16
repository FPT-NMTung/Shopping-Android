package fu.prm391.sampl.project.view.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.product.ProductGridLayoutItemAdapter;
import fu.prm391.sampl.project.model.category.Category;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.get_list_product.ProductListResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecifyCategory extends AppCompatActivity {

    private TextView txtTitle;
    private ImageView imageViewBack;
    private Category category;
    private RecyclerView recyclerView;
    private ConstraintLayout loadingConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);
        txtTitle = findViewById(R.id.txtSpecifyCategoryTitle);
        loadingConstraintLayout = findViewById(R.id.loadingConstraintLayoutSpecifyCategory);
        loadingConstraintLayout.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("specifyCategory");
        txtTitle.setText(category.getName());

        recyclerView = findViewById(R.id.recyclerViewProductByCategory);
        loadListProductByCategoryId();
        backAction();
    }

    private void loadListProductByCategoryId() {
        Call<ProductListResponse> productResponseCall = ApiClient.getProductService().getProductByCategoryId(category.getId());
        productResponseCall.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getData();
                    recyclerView.setAdapter(new ProductGridLayoutItemAdapter(SpecifyCategory.this, products));
                    GridLayoutManager layoutManager = new GridLayoutManager(SpecifyCategory.this, 2);
                    recyclerView.setLayoutManager(layoutManager);
                    loadingConstraintLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
            }
        });
    }

    private void backAction() {
        imageViewBack = findViewById(R.id.imageViewBackSpecifyCategory);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}