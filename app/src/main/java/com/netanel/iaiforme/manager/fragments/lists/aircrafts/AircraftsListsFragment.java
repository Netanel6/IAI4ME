package com.netanel.iaiforme.manager.fragments.lists.aircrafts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.activities.ManagerMainActivity;
import com.netanel.iaiforme.manager.fragments.lists.users.users_after_checked.SelectedToAcFragment;
import com.netanel.iaiforme.pojo.Aircraft;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AircraftsListsFragment extends Fragment {

    private AircraftsListsAdapter adapter = new AircraftsListsAdapter();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference selectedAcWithWorkersFS = db.collection("AircraftList");
    public Aircraft aircraftWithWorkers = new Aircraft();


    public AircraftsListsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aircraft_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        airCraftListViewModel();
        setUpRecyclerView(view);


    }

    //Aircraft ViewModel
    public void airCraftListViewModel() {
        AircraftListViewModel aircraftListViewModel = new ViewModelProvider(this).get(AircraftListViewModel.class);
        aircraftListViewModel.getAircraftListViewModel().observe(getViewLifecycleOwner(), new Observer<List<Aircraft>>() {
            @Override
            public void onChanged(List<Aircraft> aircraftList) {
                adapter.setAircraftList(aircraftList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //SetUp recyclerView
    public void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_aircraft_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(itemDecoration);
        adapter.setAircraftWithUsers(new AircraftsListsAdapter.OnItemClick() {
            @Override
            public void addAircraftWithUsers(Aircraft aircraft, int counter) {
                if (counter == 1) {

                    Snackbar snackbar = Snackbar.make(getView(),
                            "העובדים התווספו בהצלחה למטוס:\n"
                                    + aircraft.getName() + " " + aircraft.getModel() + "\t \t \t \t \t \t \t"
                            , Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.greenSignIn));
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                } else {

                    Snackbar snackbar = Snackbar.make(getView(),
                            "בוטלה בחירת מטוס:\n"
                                    + aircraft.getName() + " " + aircraft.getModel() + "\t \t \t \t \t \t \t"
                            , Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.redSignIn));
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.show();
                }
            }


            //Update docuemnt
            @Override
            public void getAdapterPosition(int position) {

                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
                String date = dateFormat.format(new Date());
                DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String time = timeFormat.format(new Date());
                String timeDate = date + " " + time;

                FloatingActionButton moveToacBtn = view.findViewById(R.id.send_schedule_to_user);
                moveToacBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "onClick: " + aircraftWithWorkers.getId());


                        selectedAcWithWorkersFS.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Aircraft aircraft = documentSnapshot.toObject(Aircraft.class);
                                    aircraft.setId(documentSnapshot.getId());

                                    if (queryDocumentSnapshots.getDocuments().get(position).getId().equals(aircraft.getId())) {
                                        selectedAcWithWorkersFS.document(aircraft.getId()).update("userArrayList", SelectedToAcFragment.userList);
                                        selectedAcWithWorkersFS.document(aircraft.getId()).update("timeDate", timeDate);
                                        Aircraft aircraft1 = new Aircraft(aircraft.getName(), aircraft.getModel());

                                        Snackbar snackbar = Snackbar.make(getView(),
                                                "העובדים התווספו בהצלחה למטוס:\n"
                                                        + aircraft1.getName() + " " + aircraft1.getModel() + "\t \t \t \t \t \t \t"
                                                , Snackbar.LENGTH_LONG);
                                        snackbar.setTextColor(getResources().getColor(R.color.greenSignIn));
                                        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                                        snackbar.show();
/*
                                        Fragment fragment = new HomeFragment();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                                        fragmentTransaction.commit();
                                        */

                                        Intent intent = new Intent(getActivity() , ManagerMainActivity.class);
                                        startActivity(intent);

                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
    }
}