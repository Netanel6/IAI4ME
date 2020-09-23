package com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.netanel.iaiforme.pojo.User;

import java.util.List;

public class FireStoreAllUsersRepository {

    private OnFireStoreTaskComplete onFireStoreTaskComplete;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");

    public FireStoreAllUsersRepository(OnFireStoreTaskComplete onFireStoreTaskComplete){
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    public void getUsersData() {
        userRef.orderBy("name").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    onFireStoreTaskComplete.userListDataAdded(task.getResult().toObjects(User.class));
                } else {
                    onFireStoreTaskComplete.onError(task.getException());
                }

            }
        });
    }

    public interface OnFireStoreTaskComplete {
        void userListDataAdded(List<User> userList);
        void onError(Exception e);
    }
}
