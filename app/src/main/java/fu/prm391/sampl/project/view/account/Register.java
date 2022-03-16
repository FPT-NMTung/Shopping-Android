package fu.prm391.sampl.project.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.StringHelpers;
import fu.prm391.sampl.project.model.user.register.RegisterRequest;
import fu.prm391.sampl.project.model.user.register.RegisterResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private TextView txtHaveAnAccount, txtBack;
    private EditText email, password, rePassword;
    private Button btnRegister;
    private ImageView btnShowPass, btnShowRePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        moveToOtherActivities();
        registerAction();
        showHidePass();
    }

    private void registerAction() {
        email = findViewById(R.id.editTextEmailRegister);
        password = findViewById(R.id.editTextPassRegister);
        rePassword = findViewById(R.id.editTextRePassRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegister.setEnabled(false);
                if (TextUtils.isEmpty(email.getText().toString().trim())
                        || TextUtils.isEmpty(password.getText().toString().trim())
                        || TextUtils.isEmpty(rePassword.getText().toString().trim())) {
                    Toast.makeText(Register.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    btnRegister.setEnabled(true);
                } else if (!StringHelpers.isValidEmail(email.getText().toString().trim())) {
                    Toast.makeText(Register.this, "Your email is invalid!", Toast.LENGTH_SHORT).show();
                    btnRegister.setEnabled(true);
                } else if (!password.getText().toString().equals(rePassword.getText().toString())) {
                    Toast.makeText(Register.this, "Password & Re-Password must be the same!", Toast.LENGTH_SHORT).show();
                    btnRegister.setEnabled(true);
                } else {
                    // proceed register
                    register();
                }
            }
        });
    }

    private void register() {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setEmail(email.getText().toString().trim());
        registerRequest.setPassword(password.getText().toString().trim());

        Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()) { // register success
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(Register.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Register.this, Login.class));
                    finish();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(Register.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    btnRegister.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                btnRegister.setEnabled(true);
            }
        });
    }

    private void moveToOtherActivities() {
        // Login
        txtHaveAnAccount = findViewById(R.id.txtHaveAnAccountRegister);
        txtHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
        // way 2
        txtBack = findViewById(R.id.txtBackRegister);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
    }

    public void showHidePass() {
        btnShowPass = findViewById(R.id.btnShowPassRegister);
        btnShowRePass = findViewById(R.id.btnShowRePassRegister);

        btnShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    ((ImageView) (view)).setImageResource(R.drawable.ic_visibility_off);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ((ImageView) (view)).setImageResource(R.drawable.ic_visibility);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                password.setSelection(password.length());
            }
        });

        btnShowRePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    ((ImageView) (view)).setImageResource(R.drawable.ic_visibility_off);
                    rePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ((ImageView) (view)).setImageResource(R.drawable.ic_visibility);
                    rePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                rePassword.setSelection(rePassword.length());
            }
        });
    }
}