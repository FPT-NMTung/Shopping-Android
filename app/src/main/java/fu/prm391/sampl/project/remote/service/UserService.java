package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.user.ForgotPassRequest;
import fu.prm391.sampl.project.model.user.ForgotPassResponse;
import fu.prm391.sampl.project.model.user.LoginRequest;
import fu.prm391.sampl.project.model.user.LoginResponse;
import fu.prm391.sampl.project.model.user.RegisterRequest;
import fu.prm391.sampl.project.model.user.RegisterResponse;
import fu.prm391.sampl.project.model.user.ResetPassRequest;
import fu.prm391.sampl.project.model.user.ResetPassResponse;
import fu.prm391.sampl.project.model.user.UpdateUserInfoRequest;
import fu.prm391.sampl.project.model.user.UpdateUserInfoResponse;
import fu.prm391.sampl.project.model.user.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    @POST("user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("user/sign-up")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("user/send-email-forgot-password")
    Call<ForgotPassResponse> sendForgotPass(@Body ForgotPassRequest forgotPassRequest);

    @PATCH("user/reset-password")
    Call<ResetPassResponse> resetPass(@Body ResetPassRequest resetPassRequest);

    @GET("user/get-user-info")
    Call<UserResponse> getUserInformation(@Header("Authorization") String token);

    @PUT("user/update-information")
    Call<UpdateUserInfoResponse> updateUserInformation(@Header("Authorization") String token,
                                                       @Body UpdateUserInfoRequest updateUserInfoRequest);
}
