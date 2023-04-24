package com.example.cureit_healthassistant.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cureit_healthassistant.Adapters.DoctorTypeAdapter;
import com.example.cureit_healthassistant.Models.DoctorTypeModel;
import com.example.cureit_healthassistant.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.homeDrVisitRCV);
        ArrayList<DoctorTypeModel> list = new ArrayList<>();
        list.add(new DoctorTypeModel("Anesthesiology\n","ANES"));
        list.add(new DoctorTypeModel("Dermatology\n","DERM"));
        list.add(new DoctorTypeModel("Emergency\nMedicine","EM"));
        list.add(new DoctorTypeModel("Family\nMedicine","FM"));
        list.add(new DoctorTypeModel("Internal\nMedicine","IM"));
        list.add(new DoctorTypeModel("Neurology\n","NEURO"));
        list.add(new DoctorTypeModel("Obstetrics\n","OB"));
        list.add(new DoctorTypeModel("Gynecology\n","GYN"));
        list.add(new DoctorTypeModel("Ophthalmology\n","OPHTH"));
        list.add(new DoctorTypeModel("Orthopedic\nSurgery","ORTHO"));
        list.add(new DoctorTypeModel("Otolaryngology\n","ENT"));
        list.add(new DoctorTypeModel("Pathology\n","PATH"));
        list.add(new DoctorTypeModel("Pediatrics\n","PEDS"));
        list.add(new DoctorTypeModel("Physical \nMedicine","PM&R"));
        list.add(new DoctorTypeModel("Rehabilitation\n","PM&R"));
        list.add(new DoctorTypeModel("Psychiatry\n","PSYCH"));
        list.add(new DoctorTypeModel("Radiology\n","RAD"));
        list.add(new DoctorTypeModel("Surgery\n","SURG"));
        list.add(new DoctorTypeModel("Allergy\nImmunology","AI"));
        list.add(new DoctorTypeModel("Cardiology\n","CARD"));
        list.add(new DoctorTypeModel("Colon\nRectal Surgery","CRS"));
        list.add(new DoctorTypeModel("Critical\nCare\n Medicine","CCM"));
        list.add(new DoctorTypeModel("Endocrinology\nDiabetes\n Metabolism ","ENDO"));
        list.add(new DoctorTypeModel("Gastroenterology\n","GI"));
        list.add(new DoctorTypeModel("Geriatric\nMedicine","GERI"));
        list.add(new DoctorTypeModel("Hematology\n","HEM"));
        list.add(new DoctorTypeModel("Infectious\nDisease","ID"));
        list.add(new DoctorTypeModel("Nephrology\n","NEPHRO"));
        list.add(new DoctorTypeModel("Oncology\n","ONC"));
        list.add(new DoctorTypeModel("Rheumatology\n","RHEUM"));
        DoctorTypeAdapter adapter = new DoctorTypeAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        return view;
    }
}