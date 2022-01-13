package fu.prm391.sampl.project.remote;

import java.util.List;

import fu.prm391.sampl.project.model.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SOService {

    @GET("products")
    Call<ProductResponse> getAllProduct();
}