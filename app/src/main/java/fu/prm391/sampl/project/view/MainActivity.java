package fu.prm391.sampl.project.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.view.account.Login;
import fu.prm391.sampl.project.view.intro.Intro1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        firstRunActivity();
        Button button = findViewById(R.id.btnTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        NavController navController = Navigation.findNavController(this,  R.id.fragmentContainerView);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void firstRunActivity() {
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(MainActivity.this, Intro1.class));
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
    }
}