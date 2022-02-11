package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressRequest;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface AddressService {

    @GET("address/get-all")
    Call<GetAllAddressResponse> getAllAddress(@Header("Authorization") String token);

    @PATCH("address/update-default")
    Call<UpdateDefaultAddressResponse> updateDefaultAddress(@Header("Authorization") String token, @Body UpdateDefaultAddressRequest request);
}
