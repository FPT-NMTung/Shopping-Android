package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.product.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("products")
    Call<ProductResponse> getAllProduct();

    @GET("product/get-top-trending")
    Call<ProductResponse> getTopTrendingProduct();

    @GET("product/get-top-discount")
    Call<ProductResponse> getTopDiscountProduct();

    @GET("product/get-top-newest")
    Call<ProductResponse> getNewArrivalsProduct();
}