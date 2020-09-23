package com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.netanel.iaiforme.pojo.User;

import java.util.List;

public class AllUserListViewModel extends ViewModel implements FireStoreAllUsersRepository.OnFireStoreTaskComplete {
    private FireStoreAllUsersRepository fireStoreRepository = new FireStoreAllUsersRepository(this);

    private MutableLiveData<List<User>> userListViewModel = new MutableLiveData<>();


    public LiveData<List<User>> getUserListViewModel() {
        return userListViewModel;
    }

    public AllUserListViewModel() {
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
