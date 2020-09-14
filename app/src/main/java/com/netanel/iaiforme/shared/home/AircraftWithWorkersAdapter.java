package com.netanel.iaiforme.shared.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.Aircraft;

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
                .inflate(R.layout.day_schedule_single_cell, parent, false);
        return new AircraftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AircraftViewHolder holder, int position) {

        Aircraft aircraft = aircraftWithWorkersList.get(position);
        holder.tvDateAdded.setText(aircraft.getDate());
        holder.timeDate.setText(aircraft.getTimeDate());
        holder.tvAcName.setText(aircraft.getName());
        holder.tvAcModel.setText(aircraft.getModel());

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < aircraft.getUserArrayList().size(); i++) {
            builder.append(i+1 + ". ");
            builder.append(aircraft.getUserArrayList().get(i));
            builder.append("\n");
        }
        holder.tvWorkerList.setText(builder.toString());


    }

    @Override
    public int getItemCount() {
        return aircraftWithWorkersList.size();
    }

    public class AircraftViewHolder extends RecyclerView.ViewHolder {
        TextView  tvAcName , tvAcModel , tvDateAdded , timeDate , tvWorkerList;

        public AircraftViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDateAdded = itemView.findViewById(R.id.tv_date_added);
            tvAcName = itemView.findViewById(R.id.tv_ac_name);
            tvAcModel = itemView.findViewById(R.id.tv_ac_model);
            timeDate = itemView.findViewById(R.id.current_time);
            tvWorkerList = itemView.findViewById(R.id.tv_date_worker_list);
        }
    }
}
