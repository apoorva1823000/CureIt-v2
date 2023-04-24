package com.example.cureit_healthassistant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cureit_healthassistant.Models.DoctorTypeModel;
import com.example.cureit_healthassistant.R;

import java.util.ArrayList;

public class DoctorTypeAdapter extends RecyclerView.Adapter<DoctorTypeAdapter.viewHolder>{
    ArrayList<DoctorTypeModel> list;
    Context context;

    public DoctorTypeAdapter(ArrayList<DoctorTypeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(com.example.cureit_healthassistant.R.layout.doctortype,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DoctorTypeModel model = list.get(position);
        holder.DoctorTypeTextView.setText(model.getDrTypeName());
        holder.DoctorTypeInitials.setText(model.getDrTypeInitials());
        holder.DoctorTypeCard.setOnClickListener(v -> {
            String name = holder.DoctorTypeTextView.getText().toString();
            Uri uri = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",name).build();
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            context.startActivity(intent);
        });
        holder.DoctorTypeTextView.setOnClickListener(v -> {
            String name = holder.DoctorTypeTextView.getText().toString();
            Uri uri = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",name).build();
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView DoctorTypeTextView,DoctorTypeInitials;
        CardView DoctorTypeCard;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            DoctorTypeTextView = itemView.findViewById(com.example.cureit_healthassistant.R.id.DrTypeTextView);
            DoctorTypeCard = itemView.findViewById(R.id.cardView);
            DoctorTypeInitials = itemView.findViewById(R.id.DrTypeInitials);
        }
    }
}
