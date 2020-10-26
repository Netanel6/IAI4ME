package com.netanel.iaiforme.manager.fragments.lists.users.users_after_checked;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.lists.aircrafts.AircraftsListsFragment;
import com.netanel.iaiforme.pojo.User;
import java.util.ArrayList;

public class SelectedToAcFragment extends Fragment {
    private RecyclerView recyclerView;
    private final FirebaseDatabase realTimeDb = FirebaseDatabase.getInstance();
    private final SelectedToAcAdapter adapter = new SelectedToAcAdapter();
    public static ArrayList<User> userList;

    public SelectedToAcFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selected_to_ac, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        workersToAcListRef();
        setupRecyclerView(view);
        moveToAcListWithWorkers(view);
    }

    //SetUp recycler view
    public void setupRecyclerView(View view) {

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    //Workers to aircraft list ref
    public void workersToAcListRef() {
        DatabaseReference selectedToAcRef = realTimeDb.getReference().child("SelectedToAcList");
        selectedToAcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    userList.add(user);
                    adapter.setUserList(userList);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //Move to aircraft list with workers
    public void moveToAcListWithWorkers(View view) {
        FloatingActionButton moveToSelectedBtn = view.findViewById(R.id.to_ac_with_workers_btn);

        moveToSelectedBtn.setOnClickListener(v -> {

            Fragment fragment = new AircraftsListsFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();


        });
    }
}