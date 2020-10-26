package com.netanel.iaiforme.manager.fragments.actions.ac_managment.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.activities.ManagerMainActivity;
import com.netanel.iaiforme.manager.fragments.lists.aircrafts.AircraftListViewModel;
import com.netanel.iaiforme.manager.fragments.lists.aircrafts.AircraftsListsAdapter;
import com.netanel.iaiforme.pojo.Aircraft;

public class UpdateDeleteAcFragment extends Fragment {

    private final AircraftsListsAdapter adapter = new AircraftsListsAdapter();
    private final CollectionReference aircraftDeleteRef = FirebaseFirestore.getInstance().collection("AircraftList");

    public UpdateDeleteAcFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_delete_ac, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
        aircraftViewModel();
    }

    //AircraftList ViewModel
    public void aircraftViewModel(){
        AircraftListViewModel aircraftListViewModel = new ViewModelProvider(this).get(AircraftListViewModel.class);
        aircraftListViewModel.getAircraftListViewModel().observe(getViewLifecycleOwner(), aircraftList -> {
            adapter.setAircraftList(aircraftList);
            adapter.notifyDataSetChanged();
        });
    }

    //Setup recyclerView
    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_aircraft_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(getContext())
                        .setTitle("מחיקת מטוס").
                        setMessage("בלחיצת אישור המטוס יימחק מהרשימה\nהאם אתה מאשר?")
                        .setNegativeButton("ביטול", (dialog, which) -> {
                            Toast.makeText(getContext(), "השינויים לא נשמרו", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }).
                        setPositiveButton("אישור", (dialog, which) -> {
                            aircraftDeleteRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Aircraft aircraft = documentSnapshot.toObject(Aircraft.class);
                                    aircraft.setId(documentSnapshot.getId());

                                    if (queryDocumentSnapshots.getDocuments().get(viewHolder.getAdapterPosition()).getId().equals(aircraft.getId())) {
                                        aircraftDeleteRef.document(aircraft.getId()).delete();
                                        Toast.makeText(getActivity(), "מטוס " + aircraft.getName() + " "
                                                + aircraft.getModel() + "\n נמחק בהצלחה מהרשימה", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                           Intent intent = new Intent(getActivity() , ManagerMainActivity.class);
                            startActivity(intent);
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}