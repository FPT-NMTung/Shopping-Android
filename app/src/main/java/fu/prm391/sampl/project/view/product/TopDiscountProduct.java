package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.ProductGridRecyclerViewAdapter;
import fu.prm391.sampl.project.model.product.ProductResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopDiscountProduct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_discount_product);
        recyclerView = findViewById(R.id.recyclerViewSuperSaleProduct);

        Call<ProductResponse> productResponseCall = ApiClient.getProductService().getTopDiscountProduct();
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getResult();
                    recyclerView.setAdapter(new ProductGridRecyclerViewAdapter(TopDiscountProduct.this, products));
                    GridLayoutManager layoutManager = new GridLayoutManager(TopDiscountProduct.this, 2);
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(TopDiscountProduct.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(TopDiscountProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(TopDiscountProduct.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        imageViewBack = findViewById(R.id.imageViewBackDiscountProduct);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopDiscountProduct.this, MainActivity.class));
                finish();
            }
        });
    }
}