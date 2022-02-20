package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.ProductListResponse;
import fu.prm391.sampl.project.model.product.ProductGridRecyclerViewAdapter;
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
        Call<ProductListResponse> productResponseCall = ApiClient.getProductService().getTopTrendingProduct();
        productResponseCall.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getData();
                    recyclerView.setAdapter(new ProductGridRecyclerViewAdapter(TrendingProduct.this, products));
                    GridLayoutManager layoutManager = new GridLayoutManager(TrendingProduct.this, 2);
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(TrendingProduct.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(TrendingProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Toast.makeText(TrendingProduct.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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