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
import fu.prm391.sampl.project.model.user.ResetPassRequest;
import fu.prm391.sampl.project.model.user.ResetPassResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    private EditText email, password, verifyCode;
    private TextView txtBack;
    private Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        moveToOtherActivities();
        resetPassAction();
    }

    private void resetPassAction() {
        email = findViewById(R.id.editTextEmailResetPass);
        password = findViewById(R.id.editTextPassResetPass);
        verifyCode = findViewById(R.id.editTextVerifyCodeResetPass);
        btnAccept = findViewById(R.id.btnAcceptResetPass);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString())
                        || TextUtils.isEmpty(password.getText().toString())
                        || TextUtils.isEmpty(verifyCode.getText().toString())) {
                    Toast.makeText(ResetPassword.this, "All fields are required!", Toast.LENGTH_LONG).show();
                } else {
                    // reset pass
                    ResetPassRequest resetPassRequest = new ResetPassRequest();
                    resetPassRequest.setEmail(email.getText().toString());
                    resetPassRequest.setPassword(password.getText().toString());
                    resetPassRequest.setCode(verifyCode.getText().toString());

                    Call<ResetPassResponse> resetPassResponseCall = ApiClient.getUserService().resetPass(resetPassRequest);
                    resetPassResponseCall.enqueue(new Callback<ResetPassResponse>() {
                        @Override
                        public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {

                            if (response.isSuccessful()) {
                                ResetPassResponse resetPassResponse = response.body();
                                Toast.makeText(ResetPassword.this, resetPassResponse.getMessage(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPassword.this, Login.class));
                                finish();
                            } else {
                                Toast.makeText(ResetPassword.this, "Reset Password failed!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetPassResponse> call, Throwable t) {
                            Toast.makeText(ResetPassword.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
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