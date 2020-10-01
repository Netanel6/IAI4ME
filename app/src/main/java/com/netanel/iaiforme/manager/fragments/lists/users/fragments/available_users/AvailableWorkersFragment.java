package com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.lists.users.users_after_checked.SelectedToAcFragment;
import com.netanel.iaiforme.pojo.User;

import java.util.ArrayList;
import java.util.List;


public class AvailableWorkersFragment extends Fragment {
    private AvailableUserListsAdapter adapter = new AvailableUserListsAdapter();
    FirebaseDatabase realTimeDb = FirebaseDatabase.getInstance();
    DatabaseReference selectedToAcRef;
    static List<User> selectedToAcList;



    public AvailableWorkersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedToAcList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_workers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
      /*
      addOrRemoveWorker();
        moveToSelectedWorkers(view);
*/
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

    public void addOrRemoveWorker() {
        adapter.setUserAddedToAc(new AvailableUserListsAdapter.OnCheckItem() {
            @Override
            public void addNewUser(User user) {
                selectedToAcList.add(user);
            }

            @Override
            public void removeNewUser(User user) {
                selectedToAcList.remove(user);

            }
        });
    }

    public void moveToSelectedWorkers(View view) {
        FloatingActionButton moveToSelectedBtn = view.findViewById(R.id.to_ac_with_workers_btn);

        moveToSelectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SelectedToAcFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

                selectedToAcRef = realTimeDb.getReference().child("SelectedToAcList");
                selectedToAcRef.setValue(selectedToAcList);

                Snackbar snackbar = Snackbar.make(v,
                        "העובדים שבחרת נוספו לרשימה", Snackbar.LENGTH_LONG);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);

                snackbar.show();
//                Toast.makeText(getActivity(), "העובדים שבחרת נוספו לרשימה", Toast.LENGTH_LONG).show();

            }
        });
    }
}