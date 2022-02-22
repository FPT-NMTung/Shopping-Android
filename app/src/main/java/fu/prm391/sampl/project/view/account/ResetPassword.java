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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.user.reset_password.ResetPassRequest;
import fu.prm391.sampl.project.model.user.reset_password.ResetPassResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    private EditText password, verifyCode;
    private TextView txtBack;
    private Button btnAccept;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        moveToOtherActivities();
        resetPassAction();
    }

    private void resetPassAction() {
        password = findViewById(R.id.editTextPassResetPass);
        verifyCode = findViewById(R.id.editTextVerifyCodeResetPass);
        btnAccept = findViewById(R.id.btnAcceptResetPass);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAccept.setEnabled(false);
                if (TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(verifyCode.getText().toString())) {
                    Toast.makeText(ResetPassword.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    btnAccept.setEnabled(true);
                } else {
                    // reset pass
                    resetPass();
                }
            }
        });
    }

    private void resetPass() {
        ResetPassRequest resetPassRequest = new ResetPassRequest();
        resetPassRequest.setEmail(email);
        resetPassRequest.setPassword(password.getText().toString());
        resetPassRequest.setCode(verifyCode.getText().toString());

        Call<ResetPassResponse> resetPassResponseCall = ApiClient.getUserService().resetPass(resetPassRequest);
        resetPassResponseCall.enqueue(new Callback<ResetPassResponse>() {
            @Override
            public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {

                if (response.isSuccessful()) {
                    ResetPassResponse resetPassResponse = response.body();
                    Toast.makeText(ResetPassword.this, resetPassResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPassword.this, Login.class));
                    finish();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(ResetPassword.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(ResetPassword.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    btnAccept.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResetPassResponse> call, Throwable t) {
                btnAccept.setEnabled(true);
            }
        });
    }

    private void moveToOtherActivities() {
        // Login
        txtBack = findViewById(R.id.txtBackResetPass);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPassword.this, ForgotPassword.class));
                finish();
            }
        });
    }
}