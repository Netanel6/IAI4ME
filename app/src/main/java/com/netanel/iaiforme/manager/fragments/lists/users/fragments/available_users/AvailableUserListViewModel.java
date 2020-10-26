package com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.netanel.iaiforme.pojo.User;
import java.util.List;

public class AvailableUserListViewModel extends ViewModel implements FireStoreAvailableUsersRepository.OnFireStoreTaskComplete {

    private final MutableLiveData<List<User>> userListViewModel = new MutableLiveData<>();

    public LiveData<List<User>> getUserListViewModel() {
        return userListViewModel;
    }

    public AvailableUserListViewModel() {
        FireStoreAvailableUsersRepository fireStoreRepository = new FireStoreAvailableUsersRepository(this);
        fireStoreRepository.getUsersData();
    }

    @Override
    public void userListDataAdded(List<User> userList) {
        userListViewModel.setValue(userList);
    }

    @Override
    public void onError(Exception e) {
    }
}
