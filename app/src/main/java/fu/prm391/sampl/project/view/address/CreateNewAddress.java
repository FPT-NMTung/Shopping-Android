package fu.prm391.sampl.project.view.address;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fu.prm391.sampl.project.R;

public class CreateNewAddress extends AppCompatActivity {

    private Button btnCnaBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_address);

        btnCnaBack = findViewById(R.id.btnCnaBack);

        btnCnaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}