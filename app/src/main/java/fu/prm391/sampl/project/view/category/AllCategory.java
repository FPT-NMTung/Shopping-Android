package fu.prm391.sampl.project.view.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.category.Category;
import fu.prm391.sampl.project.adapter.category.CategoryAllAdapter;
import fu.prm391.sampl.project.model.category.CategoryResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategory extends AppCompatActivity {

    private ImageView imageViewBack;
    private RecyclerView recyclerViewAllCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        imageViewBack = findViewById(R.id.imageViewBackCategory);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllCategory.this, MainActivity.class));
                finish();
            }
        });

        recyclerViewAllCategory = findViewById(R.id.recyclerViewAllCategory);
        Call<CategoryResponse> categoryResponseCall = ApiClient.getCategoryService().getAllCategories();
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = (ArrayList<Category>) response.body().getData();
                    recyclerViewAllCategory.setAdapter(new CategoryAllAdapter(AllCategory.this, categories));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(AllCategory.this, LinearLayoutManager.VERTICAL, false);
                    recyclerViewAllCategory.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
            }
        });
    }
}