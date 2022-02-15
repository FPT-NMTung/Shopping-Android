package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.ProductResponse;
import fu.prm391.sampl.project.adapter.product.ProductGridLayoutItemAdapter;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingProduct extends AppCompatActivity {

    private ImageView imageViewBack;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_product);
        recyclerView = findViewById(R.id.recyclerViewTrendingProduct);
        Call<ProductResponse> productResponseCall = ApiClient.getProductService().getTopTrendingProduct();
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getResult();
                    recyclerView.setAdapter(new ProductGridLayoutItemAdapter(TrendingProduct.this, products));
                    GridLayoutManager layoutManager = new GridLayoutManager(TrendingProduct.this, 2);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
            }
        });

        imageViewBack = findViewById(R.id.imageViewBackTrendingProduct);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrendingProduct.this, MainActivity.class));
                finish();
            }
        });
    }
}