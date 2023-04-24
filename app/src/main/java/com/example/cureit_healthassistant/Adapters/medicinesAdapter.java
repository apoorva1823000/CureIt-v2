package com.example.cureit_healthassistant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cureit_healthassistant.Models.medicineModel;
import com.example.cureit_healthassistant.R;
import com.example.cureit_healthassistant.Screens.CuresActivity;

import java.util.ArrayList;

public class medicinesAdapter extends RecyclerView.Adapter<medicinesAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<medicineModel> medicinesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public medicinesAdapter(ArrayList<medicineModel> medicinesArrayList, Context context) {
        this.medicinesArrayList = medicinesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public medicinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View view = LayoutInflater.from(context).inflate(R.layout.medicines_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull medicinesAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        medicineModel model = medicinesArrayList.get(position);
        holder.name.setText(model.getName());
        holder.doctor.setText(model.getDoctor());
        holder.description.setText(model.getDescription());
        holder.medicalCard.setOnClickListener(v -> {
            Intent intent = new Intent(context, CuresActivity.class);
            intent.putExtra("Name",model.getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return medicinesArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView name;
        private final TextView doctor;
        private final TextView description;
        private final CardView medicalCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            name = itemView.findViewById(R.id.medicineDiseaseName);
            doctor = itemView.findViewById(R.id.medicineDiseaseDoctorName);
            description = itemView.findViewById(R.id.medicineDiseaseDescription);
            medicalCard = itemView.findViewById(R.id.medicineCard);
        }
    }
}
