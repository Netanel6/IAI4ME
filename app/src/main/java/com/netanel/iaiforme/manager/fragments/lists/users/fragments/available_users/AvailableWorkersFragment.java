package com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.User;
import java.util.ArrayList;
import java.util.List;


public class AvailableWorkersFragment extends Fragment {
    private final AvailableUserListsAdapter adapter = new AvailableUserListsAdapter();
    static List<User> selectedToAcList;

    public AvailableWorkersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedToAcList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_available_workers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
    }

    public void setUpRecyclerView(View view) {
        TextView workerCount = view.findViewById(R.id.tv_worker_count);
        RecyclerView recyclerView = view.findViewById(R.id.rv_worker_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        int count = adapter.getItemCount();
        workerCount.setText(getString(R.string.number_of_workers) + String.valueOf(count));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

    }
}