package fu.prm391.sampl.project.view.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.account.Login;

public class Intro2 extends AppCompatActivity {

    private Button btnContinue2;
    private TextView txtSkipintro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);
        btnContinue2 = findViewById(R.id.btnContinue2);
//        txtSkipintro = findViewById(R.id.txtSkipintro);

        btnContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Intro2.this, Intro3.class);
                    startActivity(intent);

            }
        });
//        txtSkipintro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intro2.this, Login.class);
//                startActivity(intent);
//            }
//        });
    }
}