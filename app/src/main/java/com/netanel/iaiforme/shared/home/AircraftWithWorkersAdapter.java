package com.netanel.iaiforme.shared.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.Aircraft;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AircraftWithWorkersAdapter extends RecyclerView.Adapter<AircraftWithWorkersAdapter.AircraftViewHolder> {
    private List<Aircraft> aircraftWithWorkersList = new ArrayList<>();

    public void setAircraftWithWorkersList(List<Aircraft> aircraftWithWorkersList) {
        this.aircraftWithWorkersList = aircraftWithWorkersList;
    }

    @NonNull
    @Override
    public AircraftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_cell_day_schedule, parent, false);
        return new AircraftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AircraftViewHolder holder, int position) {
        Aircraft aircraft = aircraftWithWorkersList.get(position);
        holder.tvExitDate.setText("תאריך יציאת המטוס: " + aircraft.getDate());
        holder.tvLastUpdated.setText("רשימה עודכנה בתאריך: " + aircraft.getTimeDate());
        holder.tvAcName.setText(aircraft.getName());
        holder.tvAcModel.setText(aircraft.getModel());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < aircraft.getUserArrayList().size(); i++) {
            builder.append(i+1).append(". ");
            builder.append(aircraft.getUserArrayList().get(i));
            builder.append("\n");
        }
        holder.tvWorkerList.setText(builder.toString());
        Picasso.get().load(aircraft.getPaka()).into(holder.ivPaka);
    }

    @Override
    public int getItemCount() {
        return aircraftWithWorkersList.size();
    }

    public static class AircraftViewHolder extends RecyclerView.ViewHolder {
        TextView  tvAcName , tvAcModel , tvExitDate, tvLastUpdated, tvWorkerList;
        ImageView ivPaka;
        public AircraftViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExitDate = itemView.findViewById(R.id.tv_exit_date);
            tvAcName = itemView.findViewById(R.id.tv_ac_name);
            tvAcModel = itemView.findViewById(R.id.tv_ac_model);
            tvLastUpdated = itemView.findViewById(R.id.tv_last_updated);
            ivPaka = itemView.findViewById(R.id.ac_paka);
            tvWorkerList = itemView.findViewById(R.id.tv_date_worker_list);
        }
    }
}
