package fu.prm391.sampl.project.view.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.account.Login;

public class Intro3 extends AppCompatActivity {

    private Button btnContinue;
    private TextView txtSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);
        //continue
        btnContinue = findViewById(R.id.btnContinue3);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro3.this, MainActivity.class));
                finish();
            }
        });
        // skip
        txtSkip = findViewById(R.id.txtSkipIntro3);
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro3.this, MainActivity.class));
                finish();
            }
        });
    }
}