package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.product.ProductListResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("products")
    Call<ProductListResponse> getAllProduct();

    @GET("product/get-top-trending")
    Call<ProductListResponse> getTopTrendingProduct();

    @GET("product/get-top-discount")
    Call<ProductListResponse> getTopDiscountProduct();

    @GET("product/get-top-newest")
    Call<ProductListResponse> getNewArrivalsProduct();
}