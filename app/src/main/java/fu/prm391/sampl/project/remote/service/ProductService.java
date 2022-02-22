package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.address.delete_address.DeleteAddressRequest;
import fu.prm391.sampl.project.model.address.delete_address.DeleteAddressResponse;
import fu.prm391.sampl.project.model.order.delete_order.DeleteOrderRequest;
import fu.prm391.sampl.project.model.product.favorite_product.add_favorite.AddFavoriteRequest;
import fu.prm391.sampl.project.model.product.favorite_product.delete_favorite.DeleteFavoriteRequest;
import fu.prm391.sampl.project.model.product.get_list_product.ProductListResponse;
import fu.prm391.sampl.project.model.product.get_product_by_id.ProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
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

    @GET("favorites")
    Call<ProductResponse> getFavoriteProducts(@Header("Authorization") String token);

    @POST("favorite/add")
    Call<AddFavoriteRequest> addFavoriteProduct(@Header("Authorization") String token,
                                                @Body AddFavoriteRequest addFavoriteRequest);

    @HTTP(method = "DELETE", path = "favorite/delete", hasBody = true)
    Call<DeleteFavoriteRequest> deleteFavoriteProduct(@Header("Authorization") String token,
                                                      @Body DeleteFavoriteRequest deleteFavoriteRequest);
}