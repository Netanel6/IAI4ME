package com.netanel.iaiforme.manager.fragments.lists.aircrafts;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.pojo.Aircraft;
import java.util.List;

public class FireStoreAircraftRepository {

    private final OnFireStoreTaskComplete onFireStoreTaskComplete;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference aircraftRef = db.collection("AircraftList");

    public FireStoreAircraftRepository(OnFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    public void removeAircraft(Aircraft aircraft) {

        aircraftRef.document(aircraft.getId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFireStoreTaskComplete.removerAircraft(aircraft);
            }else {
                onFireStoreTaskComplete.onError(task.getException());
            }
        });
          }

    public void getAircraftData() {
        aircraftRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFireStoreTaskComplete.aircraftListDataAdded(task.getResult().toObjects(Aircraft.class));

            } else {
                onFireStoreTaskComplete.onError(task.getException());
            }
        });

    }


    public interface OnFireStoreTaskComplete {
        void aircraftListDataAdded(List<Aircraft> aircraftList);
        void removerAircraft(Aircraft aircraft);
        void onError(Exception e);
    }
}
