package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.order.OrderResponse;
import fu.prm391.sampl.project.model.order.add_to_cart.AddToCartRequest;
import fu.prm391.sampl.project.model.order.add_to_cart.AddToCartResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderService {

    @GET("order/histories")
    Call<OrderResponse> getOrdersHistory(@Header("Authorization") String token);

    @POST("order/add")
    Call<AddToCartResponse> addProductToCart(@Header("Authorization") String token, @Body AddToCartRequest addToCartRequest);
}
