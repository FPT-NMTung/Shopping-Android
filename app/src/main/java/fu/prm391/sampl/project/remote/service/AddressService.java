package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AddressService {

    @GET("address/get-all")
    Call<GetAllAddressResponse> getAllAddress(@Header("Authorization") String token);
}
