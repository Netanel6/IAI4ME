package com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.pojo.User;
import java.util.List;

public class FireStoreAllUsersRepository {
    private final OnFireStoreTaskComplete onFireStoreTaskComplete;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference userRef = db.collection("Users");

    public FireStoreAllUsersRepository(OnFireStoreTaskComplete onFireStoreTaskComplete){
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    public void getUsersData() {
        userRef.orderBy("name").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFireStoreTaskComplete.userListDataAdded(task.getResult().toObjects(User.class));
            } else {
                onFireStoreTaskComplete.onError(task.getException());
            }
        });
    }

    public interface OnFireStoreTaskComplete {
        void userListDataAdded(List<User> userList);
        void onError(Exception e);
    }
}
