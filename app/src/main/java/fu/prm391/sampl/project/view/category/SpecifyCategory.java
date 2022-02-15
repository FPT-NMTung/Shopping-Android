package fu.prm391.sampl.project.view.category;

import androidx.appcompat.app.AppCompatActivity;
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
import fu.prm391.sampl.project.model.product.ProductResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.product.NewArrivalProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecifyCategory extends AppCompatActivity {

    private TextView txtTitle;
    private ImageView imageViewBack;
    private Category category;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);

        txtTitle = findViewById(R.id.txtSpecifyCategoryTitle);

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("specifyCategory");
        txtTitle.setText(category.getName());

        recyclerView = findViewById(R.id.recyclerViewProductByCategory);
        Call<ProductResponse> productResponseCall = ApiClient.getProductService().getProductByCategoryId(category.getId());
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getResult();
                    recyclerView.setAdapter(new ProductGridLayoutItemAdapter(SpecifyCategory.this, products));
                    GridLayoutManager layoutManager = new GridLayoutManager(SpecifyCategory.this, 2);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
            }
        });

        //back
        imageViewBack =

                findViewById(R.id.imageViewBackSpecifyCategory);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}