package fu.prm391.sampl.project.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.user.LoginResponse;
import fu.prm391.sampl.project.model.user.User;
import fu.prm391.sampl.project.model.user.UserResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.account.Login;
import fu.prm391.sampl.project.view.intro.Intro1;
import fu.prm391.sampl.project.view.intro.IntroApp;
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
    private ImageView arrowLogout;
    private View someid5;
    private Button btnverifyprofiles;
    private TextView labelVerified;
    private ImageView verifyimage;
    private TextView profilesName;
    private ConstraintLayout profilelayout;

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

        //If token empty when open app display will bring to login

        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void getUserInformation()

    {
//        profilesName = getView().findViewById(R.id.profilesName);
//        Call<UserResponse> userResponseCall = ApiClient.getUserService().getUserInformation("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywiZW1haWwiOiJubXR1bmdvZmZpY2lhbEBnbWFpbC5jb20iLCJpYXQiOjE2NDQyMzM1OTl9.X7sI6-AIyKQHNj6-vlBHuuplFmTEkLnL5zkZfn5Dnzs");
//        userResponseCall.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
////                if (response.isSuccessful()) {
////                    User user = response.body().getData();
////                    profilesName.setText(user.getFirstName()+" "+user.getLastName());
////
////
////
////                } else {
////
////                }
//            }
//
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getUserInformation();
        View view = inflater.inflate(R.layout.fragment_profiles, container, false);
        someid5 = view.findViewById(R.id.some_id5);
        btnverifyprofiles = view.findViewById(R.id.btnverifyprofiles);
        btnverifyprofiles.setVisibility(View.INVISIBLE);
        labelVerified = view.findViewById(R.id.labelVerified);
        labelVerified.setText("Unverify");
        verifyimage = view.findViewById(R.id.verifyimage);
        verifyimage.setImageResource(R.drawable.unverified);
        arrowLogout = view.findViewById(R.id.arrowLogout);
        profilesName = view.findViewById(R.id.profilesName);
        Call<UserResponse> userResponseCall = ApiClient.getUserService().getUserInformation("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywiZW1haWwiOiJubXR1bmdvZmZpY2lhbEBnbWFpbC5jb20iLCJpYXQiOjE2NDQyMzM1OTl9.X7sI6-AIyKQHNj6-vlBHuuplFmTEkLnL5zkZfn5Dnzs");
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
//                    User user = response.body().getData();
//                    profilesName.setText(user.getFirstName()+" "+user.getLastName());



                } else {

                }
            }


            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        String token = PreferencesHelpers.loadStringData(getContext(),"token");
//        if (token == ""){
//            profilelayout = view.findViewById((R.id.profilelayout));
//            profilelayout.setVisibility(View.INVISIBLE);
//
//            startActivity(new Intent(getContext(), Login.class));
//
//        }else{

//            profilelayout.setVisibility(View.VISIBLE);
//        }
        someid5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlert = new MaterialAlertDialogBuilder(getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog);
                materialAlert.setTitle("ALERT");
                materialAlert.setMessage("Are you Sure want to Logout");
                materialAlert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BottomNavigationView bottomNavigationView;
                        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                        bottomNavigationView.setSelectedItemId(R.id.home2);
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
        // Inflate the layout for this fragment
        return view;




    }
}