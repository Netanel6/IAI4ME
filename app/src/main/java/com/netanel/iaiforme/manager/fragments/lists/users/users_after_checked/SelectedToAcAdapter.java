package com.netanel.iaiforme.manager.fragments.lists.users.users_after_checked;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class SelectedToAcAdapter extends RecyclerView.Adapter<SelectedToAcAdapter.SelectedToAcViewHolder> {
    List<User> userList ;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public SelectedToAcAdapter() {

    }

    @NonNull
    @Override
    public SelectedToAcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cell_selected_worker, parent, false);
        return new SelectedToAcViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull SelectedToAcViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvLast.setText(user.getLast());
        holder.tvPhone.setText(user.getPhone());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPersonalNumber.setText(user.getPersonalNumber());

        Picasso
                .get()
                .load(user.getProfilePicUrl())
                .transform(new RoundedCornersTransformation(200, 0, RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT))
                .into(holder.userPic);


    }

    @Override
    public int getItemCount() {
        if (userList == null) {
            return 0;
        } else {
            return userList.size();

        }
    }

    static class SelectedToAcViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvName, tvLast, tvPhone, tvEmail, tvPersonalNumber;
        private ImageView userPic;
        public SelectedToAcViewHolder(View itemView) {
            super(itemView);
            userPic = itemView.findViewById(R.id.user_pic_mw);
            tvName = itemView.findViewById(R.id.tv_name_mw);
            tvLast = itemView.findViewById(R.id.tv_last_mw);
            tvPhone = itemView.findViewById(R.id.tv_phone_mw);
            tvPersonalNumber = itemView.findViewById(R.id.tv_personal_number_mw);
            tvEmail = itemView.findViewById(R.id.tv_email_mw);
        }

    }
}
