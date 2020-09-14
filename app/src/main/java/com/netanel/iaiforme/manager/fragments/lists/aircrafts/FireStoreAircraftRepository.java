package com.netanel.iaiforme.manager.fragments.lists.aircrafts;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.netanel.iaiforme.pojo.Aircraft;

import java.util.List;

public class FireStoreAircraftRepository {

    private OnFireStoreTaskComplete onFireStoreTaskComplete;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference aircraftRef = db.collection("AircraftList");


    public FireStoreAircraftRepository(OnFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    public void removeAircraft(Aircraft aircraft) {

        aircraftRef.document(aircraft.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    onFireStoreTaskComplete.removerAircraft(aircraft);
                }else {
                    onFireStoreTaskComplete.onError(task.getException());
                }
            }
        });
        /*
        aircraftRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot :queryDocumentSnapshots){
                    String document = snapshot.toObject(Aircraft.class).getId();
                    db.document(document).delete();
                }

            }
        });*/


    }

    public void getAircraftData() {
        aircraftRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    onFireStoreTaskComplete.aircraftListDataAdded(task.getResult().toObjects(Aircraft.class));

                } else {
                    onFireStoreTaskComplete.onError(task.getException());
                }
            }
        });

    }


    public interface OnFireStoreTaskComplete {
        void aircraftListDataAdded(List<Aircraft> aircraftList);
        void removerAircraft(Aircraft aircraft);
        void onError(Exception e);
    }
}
