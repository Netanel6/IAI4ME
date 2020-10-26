package com.netanel.iaiforme.shared.profile;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.Noti;
import java.util.ArrayList;
import java.util.List;

public class ManagerNotiAdapter extends RecyclerView.Adapter<ManagerNotiAdapter.ManagerNotiViewHolder> {
    private List<Noti> notiArrayList = new ArrayList<>();

    public void setNotiArrayList(List<Noti> notiArrayList) {
        this.notiArrayList = notiArrayList;
    }

    @NonNull
    @Override
    public ManagerNotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_cell_manager_noti, parent, false);
        return new ManagerNotiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerNotiViewHolder holder, int position) {
        Noti noti = notiArrayList.get(position);
        holder.tvNotiDate.setText("נשלח בתאריך: " + noti.getDate());
        holder.tvNotiTitle.setText(noti.getTitle());
        holder.tvNotiTitle.setTextColor(Color.BLUE);
        holder.tvNotiDescription.setText(noti.getDescription());
        holder.itemView.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                .setTitle(noti.getTitle()).
                setMessage(noti.getDescription())
                .setPositiveButton("אישור", (dialog, which) -> {
                }).show());
    }

    @Override
    public int getItemCount() {
        return notiArrayList.size();
    }

    public static class ManagerNotiViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotiDate, tvNotiTitle, tvNotiDescription;

        public ManagerNotiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotiDate = itemView.findViewById(R.id.tv_noti_date);
            tvNotiTitle = itemView.findViewById(R.id.tv_noti_title);
            tvNotiDescription = itemView.findViewById(R.id.tv_noti_description);
        }
    }
}
