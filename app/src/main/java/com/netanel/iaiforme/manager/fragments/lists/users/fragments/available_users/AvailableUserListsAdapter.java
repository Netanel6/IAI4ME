package com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class AvailableUserListsAdapter extends RecyclerView.Adapter<AvailableUserListsAdapter.UserViewHolder> implements Filterable {

    private static List<User> userList = new ArrayList<>();
    private static List<User> userNewList = new ArrayList<>();
    private static OnCheckItem checkItem;
    private static int REGULAR_WORKER_CELL = 0;

    public AvailableUserListsAdapter() {
    }

    //Search View User Array List Fragment
    public AvailableUserListsAdapter(List<User> userList2) {
        AvailableUserListsAdapter.userList = userList2;
        userNewList = new ArrayList<>(userList2);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //adds space to the last item in the recycler view
        int type = viewType == REGULAR_WORKER_CELL ? R.layout.single_cell_worker : R.layout.single_cell_last_worker;

        View v = LayoutInflater.from(parent.getContext()).inflate(type, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        int LAST_WORKER_CELL = 1;
        return position == (getItemCount() - 1) ? LAST_WORKER_CELL : REGULAR_WORKER_CELL;

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName() + " " + user.getLast());
        holder.tvPhone.setText(user.getPhone());
        holder.tvPersonalNumber.setText(user.getPersonalNumber());

        Picasso
                .get()
                .load(user.getProfilePicUrl())
                .transform(new RoundedCornersTransformation(200, 0, RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT))
                .into(holder.userPic);
}

    @Override
    public int getItemCount() {

        return userList == null ? 0 : userList.size();

    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<User> filteredList = new ArrayList<>();
            if (charSequence == null && charSequence.length() == 0) {
                filteredList.addAll(userNewList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (User user : userNewList) {

                    if (user.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(user);
                        notifyDataSetChanged();

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            userList.clear();
            userList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPhone, tvPersonalNumber;
        private ImageView userPic;
        CheckBox checkBox;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_last_mw);
            tvPhone = itemView.findViewById(R.id.tv_phone_mw);
            tvPersonalNumber = itemView.findViewById(R.id.tv_personal_number_mw);
            userPic = itemView.findViewById(R.id.user_pic_mw);

            checkBox = itemView.findViewById(R.id.chkbox_worker_mw);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (((CheckBox) v).isChecked()) {
                        if (checkItem != null && position != RecyclerView.NO_POSITION) {
                            checkItem.addNewUser(userList.get(position));
                        }
                    } else if (!((CheckBox) v).isChecked()) {
                        if (checkItem != null) {
                            checkItem.removeNewUser(userList.get(position));
                        }
                    }
                }
            });
        }
    }

    public interface OnCheckItem {

        void addNewUser(User user);
        void removeNewUser(User user);
    }

    public void setUserAddedToAc(OnCheckItem checkItem) {
        AvailableUserListsAdapter.checkItem = checkItem;
    }
}
