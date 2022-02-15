package fu.prm391.sampl.project.view.profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.fragment.Profiles;
import fu.prm391.sampl.project.view.product.NewArrivalProduct;

public class EditProfiles extends AppCompatActivity {
    ImageView btnBack;
    EditText firstName, lastName,emailAddress,gender,phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiles);
        btnBack = findViewById(R.id.imageViewBackEditProfile);
        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);

        // remove text on edittext when click
        lastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastName.setText("");
            }
        });
        firstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName.setText("");
            }
        });
        // BtnBack action
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}