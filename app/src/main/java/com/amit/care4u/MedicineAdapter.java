package com.amit.care4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    public List<MedicineModel> medicineModels = new ArrayList<MedicineModel>();
    Context context;

    public MedicineAdapter(Context context, List<MedicineModel> medicineModels) {
        this.medicineModels = medicineModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listEvent = layoutInflater.inflate(R.layout.medicine_card, parent, false);
        return new ViewHolder(listEvent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView med_name_text, med_time_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.med_name_text = (TextView) itemView.findViewById(R.id.med_name_text);
            this.med_time_text = (TextView) itemView.findViewById(R.id.med_time_text);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MedicineModel medicineModel = medicineModels.get(position);
        int viewType = holder.getItemViewType();
        holder.med_name_text.setText(medicineModel.getMed_name());
        holder.med_time_text.setText(medicineModel.getMed_timing());
    }

    public int getItemCount() {
        return medicineModels.size();
    }
}

