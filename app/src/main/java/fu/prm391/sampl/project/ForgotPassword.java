package fu.prm391.sampl.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
            }
        });

        email = findViewById(R.id.editTextEmailForgotPass);
        btnSend = findViewById(R.id.btnSendForgotPass);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send forgot pass action
            }
        });
    }
}