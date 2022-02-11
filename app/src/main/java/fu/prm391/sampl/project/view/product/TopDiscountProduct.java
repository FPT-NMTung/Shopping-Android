package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.ProductResponse;
import fu.prm391.sampl.project.model.product.ProductSuperSaleAdapter;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.account.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopDiscountProduct extends AppCompatActivity {

    private GridView gridView;
    private ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_discount_product);
        gridView = findViewById(R.id.gridViewSuperSaleProduct);

        Call<ProductResponse> productResponseCall = ApiClient.getProductService().getTopDiscountProduct();
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getResult();
                    gridView.setAdapter(new ProductSuperSaleAdapter(TopDiscountProduct.this, products));
                    // When the user clicks on the GridItem
//                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            Object o = gridView.getItemAtPosition(i);
//                            Product product = (Product) o;
//                            Toast.makeText(TopDiscountProduct.this, "Selected :"
//                                    + " " + product, Toast.LENGTH_SHORT).show();
//                        }
//
//                    });
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

        imageButtonBack = findViewById(R.id.imageButtonBackDiscount);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopDiscountProduct.this, MainActivity.class));
                finish();
            }
        });
    }
}