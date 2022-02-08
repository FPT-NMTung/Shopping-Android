package fu.prm391.sampl.project.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.model.user.LoginRequest;
import fu.prm391.sampl.project.model.user.LoginResponse;
import fu.prm391.sampl.project.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private TextView txtCreateAccount, txtForgotPassword, txtSkip;
    private EditText email, password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        moveToOtherActivities();
        loginAction();
    }

    private void loginAction() {
        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPassLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login Action
                if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(Login.this, "Email & Password are required!", Toast.LENGTH_LONG).show();
                } else {
                    // proceed to login
                    login();
                }
            }
        });
    }

    private void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) { // login success
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    //save token to preference
                    PreferencesHelpers.saveStringData(Login.this, "token", loginResponse.getToken());
                    //check that user have logged in
                    PreferencesHelpers.getBooleanData(Login.this, "isLoggedIn", true);
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else { // login failed
                    Toast.makeText(Login.this, "Login failed\nCheck your input fields!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void moveToOtherActivities() {
        // Register
        txtCreateAccount = findViewById(R.id.txtCreateAccount);
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });
        // ForgotPassword
        txtForgotPassword = findViewById(R.id.txtForgotPassLogin);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
                finish();
            }
        });
        // MainActivity
        txtSkip = findViewById(R.id.txtSkipLogin);
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
        });
    }
}