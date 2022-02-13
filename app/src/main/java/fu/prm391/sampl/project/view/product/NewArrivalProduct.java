package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
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

public class NewArrivalProduct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_arrival_product);

        recyclerView = findViewById(R.id.recyclerViewNewArrivalsProduct);

        Call<ProductResponse> productResponseCall = ApiClient.getProductService().getNewArrivalsProduct();
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getResult();
                    recyclerView.setAdapter(new ProductGridRecyclerViewAdapter(NewArrivalProduct.this, products));
                    GridLayoutManager layoutManager = new GridLayoutManager(NewArrivalProduct.this, 2);
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
            }
        });

        imageViewBack = findViewById(R.id.imageViewBackNewArrivalProduct);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewArrivalProduct.this, MainActivity.class));
                finish();
            }
        });
    }
}