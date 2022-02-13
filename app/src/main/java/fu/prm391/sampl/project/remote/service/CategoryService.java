package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.category.CategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("category/get-top-category-product")
    Call<CategoryResponse> getTop4Categories();

    @GET("categories")
    Call<CategoryResponse> getAllCategories();
}
