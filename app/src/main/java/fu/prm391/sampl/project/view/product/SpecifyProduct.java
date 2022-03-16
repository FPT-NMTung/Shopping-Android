package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.product.ProductSimilarItemAdapter;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.category.Category;
import fu.prm391.sampl.project.model.order.add_to_cart.AddToCartRequest;
import fu.prm391.sampl.project.model.order.add_to_cart.AddToCartResponse;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.favorite_product.add_favorite.AddFavoriteRequest;
import fu.prm391.sampl.project.model.product.favorite_product.add_favorite.AddFavoriteResponse;
import fu.prm391.sampl.project.model.product.favorite_product.delete_favorite.DeleteFavoriteRequest;
import fu.prm391.sampl.project.model.product.favorite_product.delete_favorite.DeleteFavoriteResponse;
import fu.prm391.sampl.project.model.product.get_list_product.ProductListResponse;
import fu.prm391.sampl.project.model.product.get_product_by_id.ProductResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.account.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecifyProduct extends AppCompatActivity {

    private int productId;
    private TextView productName, productPrice, productDescription, quantity, quantitySold, productCategory, numberSelectedProduct;
    private ImageView productImage;
    private ImageView imageViewBack, imageViewFavorite;
    private RecyclerView recyclerViewSimilarProduct;
    private ConstraintLayout loadingConstraintLayout;
    private Button btnAddToCart;
    private ImageButton btnIncrease, btnDecrease;
    private CardView cardNumberSelectedProduct;
    private Product product;
    private int numberProduct = 1;
    private final int upperLimitNumberProduct = 10;
    private final int lowerLimitNumberProduct = 1;
    private String token;
    private ImageView imageViewUnFavorite;

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
        cardNumberSelectedProduct = findViewById(R.id.cardNumberSelectedProduct);

        loadingConstraintLayout.setVisibility(View.VISIBLE);
        cardNumberSelectedProduct.setVisibility(View.INVISIBLE);

        imageViewFavorite = findViewById(R.id.imageViewFavorite);
        imageViewUnFavorite = findViewById(R.id.imageViewUnFavorite);
        imageViewFavorite.setVisibility(View.GONE);
        imageViewUnFavorite.setVisibility(View.GONE);

        token = PreferencesHelpers.loadStringData(SpecifyProduct.this, "token");

        loadProductFromApi();
        adjustNumberSelectedProduct();
        addToCart();
        backAction();
    }

    private void loadProductFromApi() {
        Intent intent = getIntent();
        productId = intent.getIntExtra("productId", 0);
        Call<ProductResponse> productResponseCall;
        if (token == null || token.equals("")) {
            productResponseCall = ApiClient.getProductService().getProductById(productId);
        } else {
            productResponseCall = ApiClient.getProductService().getProductByIdWithToken("Bearer " + token, productId);
        }
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    product = response.body().getData();
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

                    getSimilarProducts();
                    if (product.getFavorite() == null) {
                        imageViewFavorite.setVisibility(View.GONE);
                        imageViewUnFavorite.setVisibility(View.GONE);
                    } else if (product.getFavorite()) {
                        imageViewFavorite.setVisibility(View.VISIBLE);
                    } else {
                        imageViewUnFavorite.setVisibility(View.VISIBLE);
                    }
                    addFavoriteProduct();
                    deleteFavoriteProduct();

                    loadingConstraintLayout.setVisibility(View.GONE);
                    cardNumberSelectedProduct.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
            }
        });
    }

    private void getSimilarProducts() {
        if (product.getCategories().size() != 0) {
            Call<ProductListResponse> productListResponseCall = ApiClient
                    .getProductService()
                    .getSimilarProductByCategoryId(product.getCategories().get(0).getId(), product.getId());
            productListResponseCall.enqueue(new Callback<ProductListResponse>() {
                @Override
                public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Product> products = (ArrayList<Product>) response.body().getData();
                        recyclerViewSimilarProduct.setAdapter(new ProductSimilarItemAdapter(SpecifyProduct.this, products));
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SpecifyProduct.this, LinearLayoutManager.HORIZONTAL, false);
                        recyclerViewSimilarProduct.setLayoutManager(layoutManager);

                        loadingConstraintLayout.setVisibility(View.GONE);
                        cardNumberSelectedProduct.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ProductListResponse> call, Throwable t) {
                }
            });
        }
    }

    private void addToCart() {
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (token == "") {
                    startActivity(new Intent(SpecifyProduct.this, Login.class));
                    finish();
                } else {
                    if (product.getQuantity() <= 0) {
                        Toast.makeText(SpecifyProduct.this, "Out of stocks!", Toast.LENGTH_SHORT).show();
                    } else {
                        AddToCartRequest addToCartRequest = new AddToCartRequest();
                        addToCartRequest.setProductId(productId);
                        addToCartRequest.setQuantity(numberProduct);
                        Call<AddToCartResponse> addToCartResponseCall = ApiClient.getOrderService().addProductToCart("Bearer " + token, addToCartRequest);
                        addToCartResponseCall.enqueue(new Callback<AddToCartResponse>() {
                            @Override
                            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                                if (response.isSuccessful()) { //add to cart successful
                                    AddToCartResponse addToCartResponse = response.body();
                                    Toast.makeText(SpecifyProduct.this, addToCartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    // more action here if needed
                                } else {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                        Toast.makeText(SpecifyProduct.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException | IOException e) {
                                        Toast.makeText(SpecifyProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                            }
                        });
                    }
                }
            }
        });
    }

    private void adjustNumberSelectedProduct() {
        numberSelectedProduct = findViewById(R.id.txtNumberSelectedProduct);
        btnIncrease = findViewById(R.id.imageButtonIncreaseValue);
        btnDecrease = findViewById(R.id.imageButtonDecreaseValue);

        numberSelectedProduct.setText(StringHelpers.numberLessThanTenFormat(numberProduct));

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberProduct < upperLimitNumberProduct && numberProduct < product.getQuantity()) {
                    numberProduct++;
                    numberSelectedProduct.setText(StringHelpers.numberLessThanTenFormat(numberProduct));
                }
            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberProduct > lowerLimitNumberProduct) {
                    numberProduct--;
                    numberSelectedProduct.setText(StringHelpers.numberLessThanTenFormat(numberProduct));
                }
            }
        });
    }

    private void addFavoriteProduct() {
        imageViewUnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFavoriteRequest addFavoriteRequest = new AddFavoriteRequest();
                addFavoriteRequest.setProductId(productId);
                Call<AddFavoriteResponse> addFavoriteResponseCall = ApiClient.getProductService().addFavoriteProduct("Bearer " + token, addFavoriteRequest);
                addFavoriteResponseCall.enqueue(new Callback<AddFavoriteResponse>() {
                    @Override
                    public void onResponse(Call<AddFavoriteResponse> call, Response<AddFavoriteResponse> response) {
                        if (response.isSuccessful()) {
                            imageViewUnFavorite.setVisibility(View.GONE);
                            imageViewFavorite.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddFavoriteResponse> call, Throwable t) {
                    }
                });
            }
        });

    }

    private void deleteFavoriteProduct() {
        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFavoriteRequest deleteFavoriteRequest = new DeleteFavoriteRequest();
                deleteFavoriteRequest.setProductId(productId);
                Call<DeleteFavoriteResponse> deleteFavoriteResponseCall = ApiClient.getProductService().deleteFavoriteProduct("Bearer " + token, deleteFavoriteRequest);
                deleteFavoriteResponseCall.enqueue(new Callback<DeleteFavoriteResponse>() {
                    @Override
                    public void onResponse(Call<DeleteFavoriteResponse> call, Response<DeleteFavoriteResponse> response) {
                        if (response.isSuccessful()) {
                            imageViewUnFavorite.setVisibility(View.VISIBLE);
                            imageViewFavorite.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteFavoriteResponse> call, Throwable t) {
                    }
                });
            }
        });
    }

    private void backAction() {
        imageViewBack = findViewById(R.id.imageViewBackProduct);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}