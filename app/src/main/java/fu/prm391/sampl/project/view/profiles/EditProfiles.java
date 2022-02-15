package fu.prm391.sampl.project.view.profiles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.fragment.Profiles;
import fu.prm391.sampl.project.view.product.NewArrivalProduct;

public class EditProfiles extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView btnBack,cover;
    EditText firstName, lastName,emailAddress,phoneNumber;
    Spinner gender;
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiles);
        cover = findViewById(R.id.coverImg);
        fab =findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EditProfiles.this)

//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
        btnBack = findViewById(R.id.imageViewBackEditProfile);
        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);
        emailAddress = findViewById(R.id.editTextTextEmailAddress);
        phoneNumber = findViewById(R.id.editTextPhone);

        // spinner gender
        gender = findViewById(R.id.spinnerGender);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Gender, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);

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
        emailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAddress.setText("");
            }
        });
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber.setText("");
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int positon, long l) {
        String text = parent.getItemAtPosition(positon).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        cover.setImageURI(uri);
        fab.setVisibility(View.INVISIBLE);
    }
}