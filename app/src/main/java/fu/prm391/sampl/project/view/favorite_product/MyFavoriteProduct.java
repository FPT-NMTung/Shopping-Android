package fu.prm391.sampl.project.view.favorite_product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.product.ProductLinearVerticalItemAdapter;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.favorite_product.delete_favorite.DeleteFavoriteRequest;
import fu.prm391.sampl.project.model.product.favorite_product.delete_favorite.DeleteFavoriteResponse;
import fu.prm391.sampl.project.model.product.get_list_product.ProductListResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFavoriteProduct extends AppCompatActivity {

    private ImageView imageViewBack;
    private RecyclerView recyclerViewMyFavorites;
    private ConstraintLayout loadingLayout, noFavoriteConstraintLayout;
    private String token;
    private ArrayList<Product> products;
    private ProductLinearVerticalItemAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_product);

        token = PreferencesHelpers.loadStringData(MyFavoriteProduct.this, "token");

        loadingLayout = findViewById(R.id.loadingConstraintLayoutMyFavorites);
        loadingLayout.setVisibility(View.VISIBLE);
        noFavoriteConstraintLayout = findViewById(R.id.noFavoriteProductConstraintLayout);
        noFavoriteConstraintLayout.setVisibility(View.GONE);

        recyclerViewMyFavorites = findViewById(R.id.recyclerViewMyFavorites);

        backAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearListInsideRecycleView();
        loadListFavoriteProducts();
    }

    private void loadListFavoriteProducts() {
        Call<ProductListResponse> productListResponseCall = ApiClient.getProductService().getFavoriteProducts("Bearer " + token);
        productListResponseCall.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    products = (ArrayList<Product>) response.body().getData();
                    productsAdapter = new ProductLinearVerticalItemAdapter(MyFavoriteProduct.this, products);
                    recyclerViewMyFavorites.setAdapter(productsAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyFavoriteProduct.this, LinearLayoutManager.VERTICAL, false);
                    recyclerViewMyFavorites.setLayoutManager(layoutManager);

                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(favoriteProductItemTouchHelper);
                    itemTouchHelper.attachToRecyclerView(recyclerViewMyFavorites);

                    loadingLayout.setVisibility(View.GONE);
                    if (products.size() == 0) {
                        noFavoriteConstraintLayout.setVisibility(View.VISIBLE);
                    } else {
                        noFavoriteConstraintLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
            }
        });
    }

    private void clearListInsideRecycleView() {
        recyclerViewMyFavorites.setAdapter(new ProductLinearVerticalItemAdapter(MyFavoriteProduct.this, new ArrayList<>()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyFavoriteProduct.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewMyFavorites.setLayoutManager(layoutManager);
    }

    ItemTouchHelper.SimpleCallback favoriteProductItemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            Product product = deleteItem(position);
            DeleteFavoriteRequest deleteFavoriteRequest = new DeleteFavoriteRequest();
            deleteFavoriteRequest.setProductId(product.getId());
            Call<DeleteFavoriteResponse> deleteFavoriteResponseCall = ApiClient.getProductService().deleteFavoriteProduct("Bearer " + token, deleteFavoriteRequest);
            deleteFavoriteResponseCall.enqueue(new Callback<DeleteFavoriteResponse>() {
                @Override
                public void onResponse(Call<DeleteFavoriteResponse> call, Response<DeleteFavoriteResponse> response) {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        Toast.makeText(MyFavoriteProduct.this, message, Toast.LENGTH_SHORT).show();
                        if (products.size() == 0) {
                            noFavoriteConstraintLayout.setVisibility(View.VISIBLE);
                        } else {
                            noFavoriteConstraintLayout.setVisibility(View.GONE);
                        }
                    } else {
                        revertItem(position, product);
                        Toast.makeText(MyFavoriteProduct.this, "Delete failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteFavoriteResponse> call, Throwable t) {
                    revertItem(position, product);
                    Toast.makeText(MyFavoriteProduct.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private void revertItem(int position, Product product) {
        products.add(position, product);
        productsAdapter.notifyItemInserted(position);
        recyclerViewMyFavorites.setAdapter(productsAdapter);
    }

    private Product deleteItem(int position) {
        Product product = products.get(position);
        products.remove(position);
        productsAdapter.notifyItemRemoved(position);
        recyclerViewMyFavorites.setAdapter(productsAdapter);
        return product;
    }

    private void backAction() {
        imageViewBack = findViewById(R.id.imageViewBackMyFavorites);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}