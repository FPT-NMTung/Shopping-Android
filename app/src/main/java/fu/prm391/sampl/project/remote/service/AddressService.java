package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.address.create_new_address.CreateNewAddressRequest;
import fu.prm391.sampl.project.model.address.create_new_address.CreateNewAddressResponse;
import fu.prm391.sampl.project.model.address.get_all_address.GetAllAddressResponse;
import fu.prm391.sampl.project.model.address.get_district.GetDistrictResponse;
import fu.prm391.sampl.project.model.address.get_province.GetProvinceResponse;
import fu.prm391.sampl.project.model.address.get_ward.GetWardResponse;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressRequest;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddressService {

    @GET("address/get-all")
    Call<GetAllAddressResponse> getAllAddress(@Header("Authorization") String token);

    @PATCH("address/update-default")
    Call<UpdateDefaultAddressResponse> updateDefaultAddress(@Header("Authorization") String token, @Body UpdateDefaultAddressRequest request);

    @GET("address/province")
    Call<GetProvinceResponse> getProvinceAddress();

    @GET("address/district")
    Call<GetDistrictResponse> getDistrictAddress(@Query("id") int id);

    @GET("address/ward")
    Call<GetWardResponse> getWardAddress(@Query("id") int id);

    @POST("address/create")
    Call<CreateNewAddressResponse> createNewAddress(@Header("Authorization") String token, @Body CreateNewAddressRequest createNewAddressRequest);
}
