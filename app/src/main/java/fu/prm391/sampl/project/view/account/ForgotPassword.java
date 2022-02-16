package fu.prm391.sampl.project.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fu.prm391.sampl.project.R;

import fu.prm391.sampl.project.model.user.forgot_password.ForgotPassRequest;
import fu.prm391.sampl.project.model.user.forgot_password.ForgotPassResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPassword extends AppCompatActivity {

    private TextView txtRememberedPassword, txtBack;
    private EditText email;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        moveToOtherActivities();
        //send email
        sendEmailForgotPassAction();
    }

    private void sendEmailForgotPassAction() {
        email = findViewById(R.id.editTextEmailForgotPass);
        btnSend = findViewById(R.id.btnSendForgotPass);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSend.setEnabled(false);
                // Send forgot pass
                ForgotPassRequest forgotPassRequest = new ForgotPassRequest();
                forgotPassRequest.setEmail(email.getText().toString());

                Call<ForgotPassResponse> forgotPassResponseCall = ApiClient.getUserService().sendForgotPass(forgotPassRequest);
                forgotPassResponseCall.enqueue(new Callback<ForgotPassResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPassResponse> call, Response<ForgotPassResponse> response) {

                        if (response.isSuccessful()) {
                            ForgotPassResponse forgotPassResponse = response.body();
                            Toast.makeText(ForgotPassword.this, forgotPassResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPassword.this, ResetPassword.class);
                            intent.putExtra("email", email.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(ForgotPassword.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                Toast.makeText(ForgotPassword.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                            btnSend.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPassResponse> call, Throwable t) {
                        btnSend.setEnabled(true);
                    }
                });
            }
        });
    }

    private void moveToOtherActivities() {
        //move to login
        txtRememberedPassword = findViewById(R.id.txtRememberedPassword);
        txtRememberedPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this, Login.class));
                finish();
            }
        });
        // way 2
        txtBack = findViewById(R.id.txtBackForgotPass);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this, Login.class));
                finish();
            }
        });
    }
}