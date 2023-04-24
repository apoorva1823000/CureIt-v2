package com.example.cureit_healthassistant.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cureit_healthassistant.Adapters.medicinesAdapter;
import com.example.cureit_healthassistant.Models.medicineModel;
import com.example.cureit_healthassistant.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MedicineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView medicinesRV;
    private ArrayList<medicineModel> medicineModelArrayList;
    private medicinesAdapter medicinesAdapter;
    private FirebaseFirestore db;
    private ProgressBar loadingPB;
    private ProgressDialog progressDialog;

    public MedicineFragment() {}

    public static MedicineFragment newInstance(String param1, String param2) {
        MedicineFragment fragment = new MedicineFragment();
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
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        medicinesRV = view.findViewById(R.id.MedicinesRV);
        loadingPB = view.findViewById(R.id.MedicinesProgressBar);
        db = FirebaseFirestore.getInstance();
        medicineModelArrayList = new ArrayList<>();
        medicinesAdapter = new medicinesAdapter(medicineModelArrayList,getContext());
//        medicinesRV.setHasFixedSize(true);
        medicinesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        medicinesRV.setAdapter(medicinesAdapter);
        db.collection("Medicines").orderBy("name", Query.Direction.ASCENDING).get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()){
                                loadingPB.setVisibility(View.GONE);
                                if (progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list){
                                    medicineModel model = d.toObject(medicineModel.class);
                                    medicineModelArrayList.add(model);
                                }
                                medicinesAdapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to retrieve data "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });
//        EventChangeListener();
        return view;
    }

//    private void EventChangeListener() {
//        db.collection("Medicines").orderBy("diseaseName", Query.Direction.ASCENDING)
//                .addSnapshotListener((value, error) -> {
//                    if (error!=null){
//                        if (progressDialog.isShowing()){
//                            progressDialog.dismiss();
//                        }
//                        Toast.makeText(getActivity(), "Error in retrieving data"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    for (DocumentChange dc:value.getDocumentChanges()){
//                        if (dc.getType()== DocumentChange.Type.ADDED){
//                            medicineModelArrayList.add(dc.getDocument().toObject(medicineModel.class));
//                        }
//                    }
//                    medicinesAdapter.notifyDataSetChanged();
//                    if (progressDialog.isShowing()){
//                        progressDialog.dismiss();
//                    }
//                });
//    }
}