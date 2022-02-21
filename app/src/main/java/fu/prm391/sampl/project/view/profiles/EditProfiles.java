package fu.prm391.sampl.project.view.profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.user.UpdateUserInfoRequest;
import fu.prm391.sampl.project.model.user.UpdateUserInfoResponse;
import fu.prm391.sampl.project.model.user.User;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfiles extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView btnBack, cover;
    private EditText firstName, lastName, phoneNumber;
    private Spinner gender;
    private FloatingActionButton fab;
    private Button btnSave;
    private TextView emailAddress;
    private String encodedImage;
    private String stringUri;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiles);

        // findViewById
        cover = findViewById(R.id.coverImg);
        fab = findViewById(R.id.floatingActionButton);
        btnBack = findViewById(R.id.imageViewBackEditProfile);
        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);
        emailAddress = findViewById(R.id.textViewtEmailAddressEditProfile);
        phoneNumber = findViewById(R.id.textPhoneEditProfile);
        btnSave = findViewById(R.id.btnSaveEditProfiles);

        // spinner for select gender
        gender = findViewById(R.id.spinnerGender);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Gender, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);

        getInfoFromProfiles();

        // btnSave action
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(false);
                // check format edit text fill
                if (TextUtils.isEmpty(emailAddress.getText().toString().trim())
                        || TextUtils.isEmpty(firstName.getText().toString().trim())
                        || TextUtils.isEmpty(lastName.getText().toString().trim()) || TextUtils.isEmpty(phoneNumber.getText().toString().trim())) {
                    Toast.makeText(EditProfiles.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                } else if (phoneNumber.length() != 10) {
                    Toast.makeText(EditProfiles.this, "Wrong format phone number", Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                } else {
                    // proceed save
                    updateProfileAction();
                }
            }
        });

        // upload image from phone
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EditProfiles.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(150, 150)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(294);
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

    // get info from profile and put
    private void getInfoFromProfiles() {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("userInfo");
        emailAddress.setText(user.getEmail());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhone());
        Picasso.get().load(user.getAvatar()).fit().into(cover);
        // check and set gender
        if (user.getGender() == 0) {
            gender.setSelection(2);
        } else {
            gender.setSelection(user.getGender() - 1);
        }
    }

    private void updateProfileAction() {
        String token = PreferencesHelpers.loadStringData(EditProfiles.this, "token");
        UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest();
        updateUserInfoRequest.setFirstName(firstName.getText().toString().trim());
        updateUserInfoRequest.setLastName(lastName.getText().toString().trim());
        updateUserInfoRequest.setGender(gender.getSelectedItemPosition() + 1);
        updateUserInfoRequest.setPhone(phoneNumber.getText().toString().trim());

        if (encodedImage == null) {
            updateUserInfoRequest.setImage(stringUri);
        } else {
            updateUserInfoRequest.setImage("data:image/jpeg;base64," + encodedImage);
        }

        // call Api
        Call<UpdateUserInfoResponse> updateUserInfoResponseCall = ApiClient.getUserService().updateUserInformation("Bearer " + token, updateUserInfoRequest);
        updateUserInfoResponseCall.enqueue(new Callback<UpdateUserInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateUserInfoResponse> call, Response<UpdateUserInfoResponse> response) {
                if (response.isSuccessful()) {
                    UpdateUserInfoResponse updateUserInfoResponse = response.body();
                    Toast.makeText(EditProfiles.this, updateUserInfoResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(EditProfiles.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(EditProfiles.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<UpdateUserInfoResponse> call, Throwable t) {
                btnSave.setEnabled(true);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 294 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            cover.setImageURI(imageUri);
            stringUri = imageUri.toString();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodeImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //Encode Bitmap in base64
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

}