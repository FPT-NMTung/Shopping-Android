package fu.prm391.sampl.project.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.MainActivity;

public class Register extends AppCompatActivity {

    private TextView txtHaveAnAccount;
    private EditText email;
    private EditText password;
    private EditText rePassword;
    private EditText phone;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        moveToOtherActivities();
        register();
    }

    private void register() {
        email = findViewById(R.id.editTextEmailRegister);
        password = findViewById(R.id.editTextPassRegister);
        rePassword = findViewById(R.id.editTextRePassRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Register action
            }
        });
    }

    private void moveToOtherActivities() {
        // Login
        txtHaveAnAccount = findViewById(R.id.txtHaveAnAccount);
        txtHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}