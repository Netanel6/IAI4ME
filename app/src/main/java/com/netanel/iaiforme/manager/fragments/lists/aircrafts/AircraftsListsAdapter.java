package com.netanel.iaiforme.manager.fragments.lists.aircrafts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.Aircraft;
import com.netanel.iaiforme.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class AircraftsListsAdapter extends RecyclerView.Adapter<AircraftsListsAdapter.AircraftViewHolder> {

    private static List<Aircraft> aircraftList;
    private static OnItemClick onitemClick;
    private static ArrayList<User> userList = new ArrayList<>();
    private static int AIRCRAFT_SINGLE_CELL = 0;

    public AircraftsListsAdapter() {
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        AircraftsListsAdapter.aircraftList = aircraftList;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    @NonNull
    @Override
    public AircraftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //adds space to the last item in the recycler view
        int type = viewType == AIRCRAFT_SINGLE_CELL ? R.layout.aircraft_single_cell : R.layout.single_cell_last_aircraft;

        View v = LayoutInflater.from(parent.getContext()).inflate(type, parent, false);
        return new AircraftViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        int LAST_AIRCRAFT_SINGLE_CELL = 1;
        return position == (getItemCount() - 1) ? LAST_AIRCRAFT_SINGLE_CELL : AIRCRAFT_SINGLE_CELL;

    }

    @Override
    public void onBindViewHolder(@NonNull AircraftViewHolder holder, int position) {
        Aircraft aircraft = aircraftList.get(position);
        holder.tvName.setText(aircraft.getName());
        holder.tvModel.setText(aircraft.getModel());
        holder.tvDate.setText(aircraft.getDate());
    }

    @Override
    public int getItemCount() {
        if (aircraftList == null) {
            return 0;
        } else {
            return aircraftList.size();

        }

    }

    public static class AircraftViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvModel, tvDate;
        int counter = 1;

        public AircraftViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_ac_name);
            tvModel = itemView.findViewById(R.id.tv_ac_model);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (counter == 1) {
                        itemView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.greenSignIn));
                        if (onitemClick != null && position != RecyclerView.NO_POSITION) {
                            onitemClick.addAircraftWithUsers(aircraftList.get(position), counter);
                            onitemClick.getAdapterPosition(position);
                        }
                    } else if (counter == 2) {
                        itemView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.redSignIn));
                        if (onitemClick != null && position != RecyclerView.NO_POSITION) {
                            onitemClick.addAircraftWithUsers(aircraftList.get(position), counter);
                            onitemClick.getAdapterPosition(position);
                        }
                    }
                    counter++;
                }
            });
            tvDate = itemView.findViewById(R.id.tv_date_worker_list);
        }
    }

    public interface OnItemClick {
        void addAircraftWithUsers(Aircraft aircraft, int counter);

        void getAdapterPosition(int position);
    }

    public void setAircraftWithUsers(OnItemClick onitemClick) {
        AircraftsListsAdapter.onitemClick = onitemClick;
    }

}