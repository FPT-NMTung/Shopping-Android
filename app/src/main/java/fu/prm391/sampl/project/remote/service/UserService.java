package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.user.ForgotPassRequest;
import fu.prm391.sampl.project.model.user.ForgotPassResponse;
import fu.prm391.sampl.project.model.user.LoginRequest;
import fu.prm391.sampl.project.model.user.LoginResponse;
import fu.prm391.sampl.project.model.user.RegisterRequest;
import fu.prm391.sampl.project.model.user.RegisterResponse;
import fu.prm391.sampl.project.model.user.VerifyEmailRequest;
import fu.prm391.sampl.project.model.user.VerifyEmailResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("user/sign-up")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("user/send-email-forgot-password")
    Call<ForgotPassResponse> sendForgotPass(@Body ForgotPassRequest forgotPassRequest);

    @PATCH("user/reset-password")
    Call<VerifyEmailResponse> verifyEmail(@Body VerifyEmailRequest verifyEmailRequest);
}
