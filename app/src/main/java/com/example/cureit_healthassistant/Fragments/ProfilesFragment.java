package com.example.cureit_healthassistant.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cureit_healthassistant.R;
import com.example.cureit_healthassistant.Screens.AddDoctor;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private TextView profileEmail,profileAge,profileName,profileGender,profileTitle;
    private TextView profileDrEmail,profileDrYOP,profileDrName,profileDrGender,profileDrSpecialization,profileDataDelete;
    private CircleImageView profilePic;
    private Uri imagePath;
    private int i=0;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference,documentReference2;
    private ImageView addDoctor;
    private Dialog profileDialog;
    private CardView profileDeleteUser;
    public ProfilesFragment() {
    }

    public static ProfilesFragment newInstance(String param1, String param2) {
        ProfilesFragment fragment = new ProfilesFragment();
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
        profileName = view.findViewById(R.id.profileNameValue);
        profileEmail = view.findViewById(R.id.profileEmailValue);
        profileAge = view.findViewById(R.id.profileAgeValue);
        profileGender = view.findViewById(R.id.profileGenderValue);
        profileTitle = view.findViewById(R.id.profileHeading);
        profilePic = view.findViewById(R.id.ProfilePic);
        profileDrName = view.findViewById(R.id.profileFDRNameValue);
        profileDrEmail = view.findViewById(R.id.profileFDREmailValue);
        profileDrYOP = view.findViewById(R.id.profileyopValue);
        profileDrGender = view.findViewById(R.id.profileFDRGenderValue);
        profileDrSpecialization = view.findViewById(R.id.profileSpecializationValue);
        profileDataDelete = view.findViewById(R.id.profileDeleteUserDataTV);
        addDoctor = view.findViewById(R.id.updateFamilyDoctor);
        profileDeleteUser = view.findViewById(R.id.profileDeleteUser);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("user").document(firebaseAuth.getCurrentUser().getUid());
        documentReference2 = firebaseFirestore.collection("familyDoctor").document(firebaseAuth.getCurrentUser().getUid());
        Log.e("OutOfIF",""+firebaseAuth.getCurrentUser().getUid());
        if (firebaseAuth.getCurrentUser()!=null&&firebaseFirestore.collection("user")!=null){
            documentReference.addSnapshotListener((value, error) -> {
                profileName.setText(value.getString("FullName"));
                Log.e("Error: ",""+value.get("FullName"));
                profileEmail.setText(value.getString("Email"));
                profileTitle.setText("Namaste 🙏 "+value.getString("UserName").split(" ")[0]);
                profileGender.setText(value.getString("Gender"));
                profileAge.setText(value.getString("Age"));
            });
            documentReference2.addSnapshotListener((value, error) -> {
                assert value != null;
                profileDrName.setText(value.getString("DoctorName"));
                profileDrEmail.setText(value.getString("DoctorEmail"));
                profileDrYOP.setText(value.getString("DoctorYOP"));
                profileDrGender.setText(value.getString("DoctorGender"));
                profileDrSpecialization.setText(value.getString("DoctorSpecialization"));
            });
        }
        addDoctor.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),AddDoctor.class));
        });
        profileDeleteUser.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Confirm");
            builder.setMessage("Do you confirm deleting your data ?\nThis is irreversible action");
            builder.setIcon(R.drawable.baseline_info_24);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_SHORT).show();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential("user@example.com", "password1234");
                user.reauthenticate(credential)
                        .addOnCompleteListener(task -> user.delete()
                                .addOnCompleteListener(task2 -> {
                                    Toast.makeText(getActivity(), "User data deleted successfully", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }));
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                Toast.makeText(getActivity(), "Thank you ! for staying with us", Toast.LENGTH_SHORT).show();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        return view;
    }
}