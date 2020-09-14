package com.netanel.iaiforme.manager.fragments.lists.users.users_bc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.netanel.iaiforme.pojo.User;

import java.util.List;

public class UserListViewModel extends ViewModel implements FireStoreUsersRepository.OnFireStoreTaskComplete {
    private FireStoreUsersRepository fireStoreRepository = new FireStoreUsersRepository(this);

    private MutableLiveData<List<User>> userListViewModel = new MutableLiveData<>();


    public LiveData<List<User>> getUserListViewModel() {
        return userListViewModel;
    }

    public UserListViewModel() {
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
