package fu.prm391.sampl.project.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.view.intro.Intro1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        firstRunActivity();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackgroundColor(Color.WHITE);

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void firstRunActivity() {
        Boolean isFirstRun = PreferencesHelpers.getBooleanData(MainActivity.this, "isFirstRun", true);
        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(MainActivity.this, Intro1.class));
        }
        PreferencesHelpers.editBooleanData(MainActivity.this, "isFirstRun", false);
//        way 2:
//        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
//                .getBoolean("isFirstRun", true);
//        if (isFirstRun) {
//            //show start activity
//            startActivity(new Intent(MainActivity.this, Intro1.class));
//        }
//        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
//                .putBoolean("isFirstRun", false).commit();
    }
}