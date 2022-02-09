package fu.prm391.sampl.project.view.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.account.Login;

public class Intro1 extends AppCompatActivity {

    private Button btnContinue;
    private TextView txtSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);
        // continue
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro1.this, Intro2.class));
                finish();
            }
        });
        // skip
        txtSkip = findViewById(R.id.txtSkipIntro1);
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro1.this, MainActivity.class));
                finish();
            }
        });
    }
}