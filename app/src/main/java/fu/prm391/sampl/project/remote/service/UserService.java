package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.user.UpdateUserInfoRequest;
import fu.prm391.sampl.project.model.user.UpdateUserInfoResponse;
import fu.prm391.sampl.project.model.user.active_account.ActiveAccountRequest;
import fu.prm391.sampl.project.model.user.active_account.ActiveAccountResponse;
import fu.prm391.sampl.project.model.user.change_password.ChangePasswordRequest;
import fu.prm391.sampl.project.model.user.change_password.ChangePasswordResponse;
import fu.prm391.sampl.project.model.user.forgot_password.ForgotPassRequest;
import fu.prm391.sampl.project.model.user.forgot_password.ForgotPassResponse;
import fu.prm391.sampl.project.model.user.login.LoginRequest;
import fu.prm391.sampl.project.model.user.login.LoginResponse;
import fu.prm391.sampl.project.model.user.register.RegisterRequest;
import fu.prm391.sampl.project.model.user.register.RegisterResponse;
import fu.prm391.sampl.project.model.user.reset_password.ResetPassRequest;
import fu.prm391.sampl.project.model.user.reset_password.ResetPassResponse;
import fu.prm391.sampl.project.model.user.UserResponse;
import fu.prm391.sampl.project.model.user.send_email_active_account.EmailActiveAccountResponse;
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

    @POST("user/send-email-active-account")
    Call<EmailActiveAccountResponse> sendEmailActiveAccount(@Header("Authorization") String token);


    @PATCH("user/active")
    Call<ActiveAccountResponse> activeAccount(@Header("Authorization") String token,
                                              @Body ActiveAccountRequest activeAccountRequest);

    @PATCH("user/change-password")
    Call<ChangePasswordResponse> changePassword(@Header("Authorization") String token,
                                               @Body ChangePasswordRequest changePasswordRequest);
}
