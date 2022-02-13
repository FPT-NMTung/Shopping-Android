package fu.prm391.sampl.project.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.user.User;
import fu.prm391.sampl.project.model.user.UserResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.account.Login;
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

    private View viewLogOut;
    private Button btnVerifyProfiles, btnEditProfiles;
    private TextView labelVerified, profilesName, emailProfiles;
    private ImageView verifyImage, imageProfiles;

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

        labelVerified = view.findViewById(R.id.labelVerifiedProfile);
        verifyImage = view.findViewById(R.id.imageVerifiedProfile);
        emailProfiles = view.findViewById(R.id.txtEmailProfiles);
        profilesName = view.findViewById(R.id.txtNameProfiles);
        imageProfiles = view.findViewById(R.id.imageProfiles);

        // verify Profile Action
        btnVerifyProfiles = view.findViewById(R.id.btnVerifyProfiles);
        btnVerifyProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verify Profile Action
            }
        });

        // edit profile action
        btnEditProfiles = view.findViewById(R.id.btnEditProfiles);
        btnEditProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edit profile action
            }
        });

        Call<UserResponse> userResponseCall = ApiClient.getUserService().getUserInformation("Bearer " + token);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    User user = response.body().getData();
                    // check username
                    if (user.getFirstName() == null && user.getLastName() == null) {
                        profilesName.setText("Your Username");
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
                } else {
                    // case response error
                    // need to add after merge
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
}