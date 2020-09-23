package com.netanel.iaiforme.shared.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.Aircraft;
import com.netanel.iaiforme.pojo.Noti;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView tvTitleDate;
    private AircraftWithWorkersAdapter aircraftWithWorkersAdapter = new AircraftWithWorkersAdapter();
    private ManagerNotiAdapter managerNotiAdapter = new ManagerNotiAdapter();
    RecyclerView rvDaySchedule;
    RecyclerView rvManagerNoti;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference selectedAcWithWorkers = db.collection("AircraftList");
    private CollectionReference managerNotiRef = db.collection("Fcm");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpDate(view);
        setUpDayScheduleRecyclerView(view);
        setUpManagerNotiRecyclerView(view);

    }

    //Current date
    public void setUpDate(View view) {
        tvTitleDate = view.findViewById(R.id.tv_title_date);
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String time = timeFormat.format(new Date());
        tvTitleDate.setText(date + " " + time);
    }

    //Notifications recycler view
    public void setUpManagerNotiRecyclerView(View view) {
        rvManagerNoti = view.findViewById(R.id.rv_notifications);
        managerNotiRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Noti> noti = queryDocumentSnapshots.toObjects(Noti.class);
                rvManagerNoti.setLayoutManager(new LinearLayoutManager(getActivity(),
                        RecyclerView.HORIZONTAL, false));
                managerNotiAdapter.setNotiArrayList(noti);
                rvManagerNoti.setAdapter(managerNotiAdapter);
                DividerItemDecoration itemDecoration2 = new DividerItemDecoration(rvManagerNoti.getContext(),
                        DividerItemDecoration.HORIZONTAL);
                rvManagerNoti.addItemDecoration(itemDecoration2);
                managerNotiAdapter.notifyDataSetChanged();
            }
        });
    }

    //Day schedule recycler view
    public void setUpDayScheduleRecyclerView(View view) {
        rvDaySchedule = view.findViewById(R.id.rv_aircraft_list);

        selectedAcWithWorkers.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Aircraft> aircraft = queryDocumentSnapshots.toObjects(Aircraft.class);
                LayoutManager lm = new LinearLayoutManager(getActivity(),
                        RecyclerView.VERTICAL, true);

                rvDaySchedule.setLayoutManager(lm);
                aircraftWithWorkersAdapter.setAircraftWithWorkersList(aircraft);
                rvDaySchedule.setAdapter(aircraftWithWorkersAdapter);

                rvDaySchedule.getLayoutManager()
                        .smoothScrollToPosition(rvDaySchedule, new RecyclerView.State(),
                                aircraftWithWorkersAdapter.getItemCount());

                DividerItemDecoration itemDecoration = new DividerItemDecoration(rvDaySchedule.getContext(),
                        DividerItemDecoration.VERTICAL);
                rvDaySchedule.addItemDecoration(itemDecoration);
            }

        });
    }

}
