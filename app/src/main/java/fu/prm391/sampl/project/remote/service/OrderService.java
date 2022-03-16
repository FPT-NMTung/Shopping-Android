package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.order.OrderResponse;
import fu.prm391.sampl.project.model.order.add_to_cart.AddToCartRequest;
import fu.prm391.sampl.project.model.order.add_to_cart.AddToCartResponse;
import fu.prm391.sampl.project.model.order.check_out.CheckOutOrderRequest;
import fu.prm391.sampl.project.model.order.check_out.CheckOutOrderResponse;
import fu.prm391.sampl.project.model.order.decrease_quantity.DecreaseQuantityRequest;
import fu.prm391.sampl.project.model.order.decrease_quantity.DecreaseQuantityResponse;
import fu.prm391.sampl.project.model.order.delete_order.DeleteOrderRequest;
import fu.prm391.sampl.project.model.order.delete_order.DeleteOrderResponse;
import fu.prm391.sampl.project.model.order.get_all_order.GetAllOrderResponse;
import fu.prm391.sampl.project.model.order.increase_quantity.IncreaseQuantityRequest;
import fu.prm391.sampl.project.model.order.increase_quantity.IncreaseQuantityResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface OrderService {

    @POST("order/add")
    Call<AddToCartResponse> addProductToCart(@Header("Authorization") String token, @Body AddToCartRequest addToCartRequest);

    @GET("orders")
    Call<GetAllOrderResponse> getAllOrder(@Header("Authorization") String token);

    @PATCH("order/increase-quantity")
    Call<IncreaseQuantityResponse> increaseQuantityOrder(@Header("Authorization") String token, @Body IncreaseQuantityRequest request);

    @PATCH("order/decrease-quantity")
    Call<DecreaseQuantityResponse> decreaseQuantityOrder(@Header("Authorization") String token, @Body DecreaseQuantityRequest request);

    @HTTP(method = "DELETE", path = "order/delete", hasBody = true)
    Call<DeleteOrderResponse> deleteOrder(@Header("Authorization") String token, @Body DeleteOrderRequest request);

    @POST("order/check-out")
    Call<CheckOutOrderResponse> checkout(@Header("Authorization") String token, @Body CheckOutOrderRequest request);

    @GET("order/histories")
    Call<OrderResponse> getOrdersHistoryByStatus(@Header("Authorization") String token,
                                                 @Query("status") int status);
}
