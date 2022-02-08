package fu.prm391.sampl.project.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fu.prm391.sampl.project.R;

import fu.prm391.sampl.project.model.user.ForgotPassRequest;
import fu.prm391.sampl.project.model.user.ForgotPassResponse;
import fu.prm391.sampl.project.model.user.LoginResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPassword extends AppCompatActivity {

    private TextView txtRememberedPassword;
    private EditText email;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //move to login
        txtRememberedPassword = findViewById(R.id.txtRememberedPassword);
        txtRememberedPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this, Login.class));
                finish();
            }
        });

        //send email
        email = findViewById(R.id.editTextEmailForgotPass);
        btnSend = findViewById(R.id.btnSendForgotPass);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send forgot pass action

                ForgotPassRequest forgotPassRequest = new ForgotPassRequest();
                forgotPassRequest.setEmail(email.getText().toString());

                Call<ForgotPassResponse> forgotPassResponseCall = ApiClient.getUserService().sendForgotPass(forgotPassRequest);
                forgotPassResponseCall.enqueue(new Callback<ForgotPassResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPassResponse> call, Response<ForgotPassResponse> response) {
                        if(response.isSuccessful()) {
                            ForgotPassResponse forgotPassResponse = response.body();
                            Toast.makeText(ForgotPassword.this, forgotPassResponse.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ForgotPassword.this, "Send Email failed!", Toast.LENGTH_LONG).show();
                        }
                    }


                Intent intent = new Intent(ForgotPassword.this, VerificationEmailCode.class);
                startActivity(intent);

                    @Override
                    public void onFailure(Call<ForgotPassResponse> call, Throwable t) {
                        Toast.makeText(ForgotPassword.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}