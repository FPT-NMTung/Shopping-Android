package fu.prm391.sampl.project.remote.service;

import fu.prm391.sampl.project.model.user.LoginRequest;
import fu.prm391.sampl.project.model.user.LoginResponse;
import fu.prm391.sampl.project.model.user.RegisterRequest;
import fu.prm391.sampl.project.model.user.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("user/sign-up")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);
}
