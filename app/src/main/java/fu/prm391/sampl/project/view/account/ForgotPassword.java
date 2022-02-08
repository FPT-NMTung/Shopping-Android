package fu.prm391.sampl.project.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.intro.Intro2;
import fu.prm391.sampl.project.view.intro.Intro3;

public class ForgotPassword extends AppCompatActivity {

    private TextView txtRememberedPassword;
    private EditText email;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        txtRememberedPassword = findViewById(R.id.txtRememberedPassword);
        txtRememberedPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        email = findViewById(R.id.editTextEmailForgotPass);
        btnSend = findViewById(R.id.btnSendForgotPass);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send forgot pass action
                //Khong biet gi het


                Intent intent = new Intent(ForgotPassword.this, VerificationEmailCode.class);
                startActivity(intent);
            }
        });
    }
}