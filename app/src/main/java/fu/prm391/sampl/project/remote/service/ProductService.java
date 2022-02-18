package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.product.get_list_product.ProductListResponse;
import fu.prm391.sampl.project.model.product.get_product_by_id.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {

    @GET("products")
    Call<ProductListResponse> getAllProduct();

    @GET("product/get-top-trending")
    Call<ProductListResponse> getTopTrendingProduct();

    @GET("product/get-top-discount")
    Call<ProductListResponse> getTopDiscountProduct();

    @GET("product/get-top-newest")
    Call<ProductListResponse> getNewArrivalsProduct();

    @GET("product/get-by-category")
    Call<ProductListResponse> getProductByCategoryId(@Query("categoryId") int categoryId);

    @GET("product/search")
    Call<ProductListResponse> searchProducts(@Query("query") String query,
                                             @Query("limit") int limit);

    @GET("product/get")
    Call<ProductResponse> getProductByID(@Query("id") int id);
}