package in.xparticle.instarupee.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import in.xparticle.instarupee.MainActivity;
import in.xparticle.instarupee.ManageProfileActivity;
import in.xparticle.instarupee.R;
import in.xparticle.instarupee.utils.AppSession;


public class ProfileFragment extends Fragment {


    LinearLayout manageProfile,help,aboutUs,logout;
    AppSession appSession;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manageProfile = view.findViewById(R.id.manageProfile);
        help = view.findViewById(R.id.help);
        appSession =new  AppSession(getActivity());
        aboutUs = view.findViewById(R.id.aboutUs);
        logout = view.findViewById(R.id.logout);

        manageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageProfileFun();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutFromThisDevice();
            }
        });
    }

    private void logoutFromThisDevice() {

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure to Logout")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        appSession.setLogin(false);
                        appSession.setFirstName(null);
                        appSession.setFirstName(null);
                        appSession.setState(null);
                        appSession.setCity(null);
                        appSession.setPhoneNumber(null);
                        appSession.setPassword(null);
                        appSession.setEmail(null);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }

    private void manageProfileFun() {
        Intent intent = new Intent(getActivity(), ManageProfileActivity.class);
        startActivity(intent);
    }
}