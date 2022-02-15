package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.order.OrderResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface OrderService {

    @GET("order/histories")
    Call<OrderResponse> getOrdersHistory(@Header("Authorization") String token);
}
