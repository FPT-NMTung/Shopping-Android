package fu.prm391.sampl.project.view.profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.user.active_account.ActiveAccountRequest;
import fu.prm391.sampl.project.model.user.active_account.ActiveAccountResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveAccount extends AppCompatActivity {

    private TextView txtBack;
    private EditText etVerifyCode;
    private Button btnAccept;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_account);

        token = PreferencesHelpers.loadStringData(ActiveAccount.this, "token");

        etVerifyCode = findViewById(R.id.editTextCodeActiveAccount);
        btnAccept = findViewById(R.id.btnAcceptActiveAccount);

        acceptAction();
        backAction();
    }

    private void acceptAction() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAccept.setEnabled(false);
                if (TextUtils.isEmpty(etVerifyCode.getText().toString().trim())) {
                    Toast.makeText(ActiveAccount.this, "Verify Code is required!", Toast.LENGTH_SHORT).show();
                    btnAccept.setEnabled(true);
                } else {
                    activeAccount();
                }
            }
        });
    }

    private void activeAccount() {
        ActiveAccountRequest activeAccountRequest = new ActiveAccountRequest();
        activeAccountRequest.setCode(etVerifyCode.getText().toString().trim());
        Call<ActiveAccountResponse> activeAccountResponseCall = ApiClient.getUserService().activeAccount("Bearer " + token, activeAccountRequest);
        activeAccountResponseCall.enqueue(new Callback<ActiveAccountResponse>() {
            @Override
            public void onResponse(Call<ActiveAccountResponse> call, Response<ActiveAccountResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Toast.makeText(ActiveAccount.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(ActiveAccount.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(ActiveAccount.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    btnAccept.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ActiveAccountResponse> call, Throwable t) {
                btnAccept.setEnabled(true);
            }
        });
    }

    private void backAction() {
        txtBack = findViewById(R.id.txtBackActiveAccount);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}