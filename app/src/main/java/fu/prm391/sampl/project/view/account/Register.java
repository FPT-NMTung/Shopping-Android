package fu.prm391.sampl.project.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.user.RegisterRequest;
import fu.prm391.sampl.project.model.user.RegisterResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private TextView txtHaveAnAccount;
    private EditText email;
    private EditText password;
    private EditText rePassword;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        moveToOtherActivities();
        registerAction();
    }

    private void registerAction() {
        email = findViewById(R.id.editTextEmailRegister);
        password = findViewById(R.id.editTextPassRegister);
        rePassword = findViewById(R.id.editTextRePassRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(rePassword.getText().toString())) {
                    Toast.makeText(Register.this, "All fields are required!", Toast.LENGTH_LONG).show();
                } else if(!password.getText().toString().equals(rePassword.getText().toString())) {
                    Toast.makeText(Register.this, "Password & Re-Password must be the same!", Toast.LENGTH_LONG).show();
                } else {
                    // proceed register
                    register();
                }

            }
        });
    }

    private void register() {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setEmail(email.getText().toString());
        registerRequest.setPassword(password.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if(response.isSuccessful()) { // register success
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(Register.this, registerResponse.getMessage(), Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Register.this, Login.class));
                            finish();
                        }
                    }, 500);
                } else {
                    Toast.makeText(Register.this, "Register failed\nCheck your input fields!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Register.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void moveToOtherActivities() {
        // Login
        txtHaveAnAccount = findViewById(R.id.txtHaveAnAccount);
        txtHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
    }
}