package fu.prm391.sampl.project.view.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.MainActivity;

public class IntroApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_app);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(IntroApp.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}