package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.product.ProductSimilarItemAdapter;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.category.Category;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.get_list_product.ProductListResponse;
import fu.prm391.sampl.project.model.product.get_product_by_id.ProductResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecifyProduct extends AppCompatActivity {

    private int productId;
    private TextView productName, productPrice, productDescription, quantity, quantitySold, productCategory;
    private ImageView productImage;
    private ImageView imageViewBack, imageViewFavorite;
    private RecyclerView recyclerViewSimilarProduct;
    private ConstraintLayout loadingConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_product);
        productName = findViewById(R.id.txtProductName);
        productPrice = findViewById(R.id.txtProductPrice);
        productImage = findViewById(R.id.imageViewProduct);
        productDescription = findViewById(R.id.txtProductDescription);
        quantity = findViewById(R.id.txtQuantityProduct);
        quantitySold = findViewById(R.id.txtQuantitySoldProduct);
        productCategory = findViewById(R.id.txtCategoryProduct);
        loadingConstraintLayout = findViewById(R.id.loadingConstraintLayoutSpecifyProduct);
        recyclerViewSimilarProduct = findViewById(R.id.recyclerViewSimilarProducts);

        loadingConstraintLayout.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        productId = intent.getIntExtra("productId", 0);
        Call<ProductResponse> productResponseCall = ApiClient.getProductService().getProductByID(productId);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    Product product = response.body().getData();
                    productName.setText(product.getName());
                    productPrice.setText(StringHelpers.currencyFormatterWithPercent(product.getPrice(), product.getDiscount()));
                    productDescription.setText(product.getDescription());
                    quantity.setText(String.valueOf(product.getQuantity()));
                    String productCategories = "";
                    if (product.getCategories().size() == 1) {
                        productCategories = product.getCategories().get(0).getName();
                    } else if (product.getCategories().size() > 1) {
                        int index = 1;
                        for (Category category : product.getCategories()) {
                            if (product.getCategories().size() > index) {
                                productCategories += (category.getName() + ", ");
                                index++;
                            } else {
                                productCategories += category.getName();
                            }
                        }
                    }
                    quantitySold.setText(String.valueOf(product.getQuantitySold()));
                    productCategory.setText(productCategories);
                    Picasso.get().load(product.getImage()).fit().into(productImage);


                    Call<ProductListResponse> productListResponseCall = ApiClient.getProductService().getProductByCategoryId(product.getCategories().get(0).getId());
                    productListResponseCall.enqueue(new Callback<ProductListResponse>() {
                        @Override
                        public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                            if (response.isSuccessful()) {
                                ArrayList<Product> products = (ArrayList<Product>) response.body().getData();
                                recyclerViewSimilarProduct.setAdapter(new ProductSimilarItemAdapter(SpecifyProduct.this, products));
                                LinearLayoutManager layoutManager = new LinearLayoutManager(SpecifyProduct.this, LinearLayoutManager.HORIZONTAL, false);
                                recyclerViewSimilarProduct.setLayoutManager(layoutManager);

                                loadingConstraintLayout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductListResponse> call, Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.i("Test", t.getLocalizedMessage());
            }
        });


        imageViewBack = findViewById(R.id.imageViewBackProduct);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageViewFavorite = findViewById(R.id.imageViewFavoriteIconProduct);
        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}