package fu.prm391.sampl.project.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.user.User;
import fu.prm391.sampl.project.model.user.UserResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.account.Login;
import fu.prm391.sampl.project.view.address.ProfileShippingAddress;
import fu.prm391.sampl.project.view.profiles.EditProfiles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profiles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profiles extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View viewLogOut, viewShippingAddress;
    private Button btnVerifyProfiles, btnEditProfiles;
    private TextView labelVerified, profilesName, emailProfiles;
    private ImageView verifyImage, imageProfiles;
    private ActivityResultLauncher<Intent> launcher;

    public Profiles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Profiles newInstance(String param1, String param2) {
        Profiles fragment = new Profiles();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profiles, container, false);

        String token = PreferencesHelpers.loadStringData(getContext(), "token");
        if (token == "") {
            startActivity(new Intent(getContext(), Login.class));
            getActivity().finish();
        }
        // create launcher

        labelVerified = view.findViewById(R.id.labelVerifiedProfile);
        verifyImage = view.findViewById(R.id.imageVerifiedProfile);
        emailProfiles = view.findViewById(R.id.txtEmailProfiles);
        profilesName = view.findViewById(R.id.txtNameProfiles);
        imageProfiles = view.findViewById(R.id.imageProfiles);
        btnVerifyProfiles = view.findViewById(R.id.btnVerifyProfiles);
        btnEditProfiles = view.findViewById(R.id.btnEditProfiles);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == 201) {
                    Intent data = result.getData();
                    profilesName.setText(data.getStringExtra("userName"));
//                    emailProfiles.setText(data.getStringExtra("emailAddress"));
                    String stringUri = data.getStringExtra("profileImage");
//                    Picasso.get().lo
                    Picasso.get().load(stringUri).fit().into(imageProfiles);
                }
            }
        });
        // set invisible when api have not called
        profilesName.setVisibility(View.INVISIBLE);
        emailProfiles.setVisibility(View.INVISIBLE);
        labelVerified.setVisibility(View.INVISIBLE);
        verifyImage.setVisibility(View.INVISIBLE);
        btnVerifyProfiles.setVisibility(View.INVISIBLE);
        btnEditProfiles.setVisibility(View.INVISIBLE);


        Call<UserResponse> userResponseCall = ApiClient.getUserService().getUserInformation("Bearer " + token);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {

                    User user = response.body().getData();
                    // set visible when api call successful
                    profilesName.setVisibility(View.VISIBLE);
                    emailProfiles.setVisibility(View.VISIBLE);
                    labelVerified.setVisibility(View.VISIBLE);
                    verifyImage.setVisibility(View.VISIBLE);
                    btnVerifyProfiles.setVisibility(View.VISIBLE);
                    btnEditProfiles.setVisibility(View.VISIBLE);
                    // check username
                    if (user.getFirstName() == null && user.getLastName() == null) {
                        profilesName.setText("Unknown");
                    } else if (user.getFirstName() == null && user.getLastName() != null) {
                        profilesName.setText(user.getLastName());
                    } else if (user.getFirstName() != null && user.getLastName() == null) {
                        profilesName.setText(user.getFirstName());
                    } else {
                        profilesName.setText(user.getFirstName() + " " + user.getLastName());
                    }

                    // set default image
                    if (user.getAvatar() != null) {
                        Picasso.get().load(user.getAvatar()).fit().into(imageProfiles);
                    } else {
                        imageProfiles.setImageResource(R.drawable.icon_person);
                    }
                    // set email
                    emailProfiles.setText(user.getEmail());
                    // set verified user
                    if (user.isActive()) {
                        btnVerifyProfiles.setVisibility(View.INVISIBLE);
                        verifyImage.setImageResource(R.drawable.verified);
                        labelVerified.setText("Verified");
                    } else {
                        verifyImage.setImageResource(R.drawable.unverified);
                        labelVerified.setText("UnVerified");
                        btnVerifyProfiles.setVisibility(View.VISIBLE);
                    }

                    // verify Profile Action

                    btnVerifyProfiles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // verify Profile Action
                        }
                    });

                    // edit profile action

                    btnEditProfiles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), EditProfiles.class);
                            intent.putExtra("userInfo", user);
                            launcher.launch(intent);
//                        startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
        viewShippingAddress = view.findViewById(R.id.viewShippingAddressProfiles);
        viewShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileShippingAddress.class);
                startActivity(intent);
//                getActivity().finish();
            }
        });
        viewLogOut = view.findViewById(R.id.viewLogoutProfile);
        viewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlert = new MaterialAlertDialogBuilder(getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog);
                materialAlert.setTitle("ALERT");
                materialAlert.setMessage("Are you Sure want to Logout");
                materialAlert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // move to home navigation
                        BottomNavigationView bottomNavigationView;
                        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                        bottomNavigationView.setSelectedItemId(R.id.home2);
                        // delete token
                        PreferencesHelpers.removeSinglePreference(getContext(), "token");
                    }
                });
                materialAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                materialAlert.show();
            }
        });

        return view;


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Intent intent = getActivity().getIntent();
//        getActivity().finish();
//        startActivity(intent);
//
//    }

}