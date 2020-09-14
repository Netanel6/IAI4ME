package com.netanel.iaiforme.shared.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
                .inflate(R.layout.manager_noti_single_cell, parent, false);
        return new ManagerNotiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerNotiViewHolder holder, int position) {
        Noti noti = notiArrayList.get(position);
        holder.tvNotiTitle.setText(noti.getTitle());
        holder.tvNotiDescription.setText(noti.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(noti.getTitle()).
                        setMessage(noti.getDescription())
                        .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return notiArrayList.size();
    }

    public static class ManagerNotiViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotiTitle, tvNotiDescription;

        public ManagerNotiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotiTitle = itemView.findViewById(R.id.tv_noti_title);
            tvNotiDescription = itemView.findViewById(R.id.tv_noti_description);
        }
    }
}
