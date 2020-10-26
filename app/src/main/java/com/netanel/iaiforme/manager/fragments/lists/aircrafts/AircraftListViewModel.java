package com.netanel.iaiforme.manager.fragments.lists.aircrafts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.netanel.iaiforme.pojo.Aircraft;
import java.util.List;

public class AircraftListViewModel extends ViewModel implements
        FireStoreAircraftRepository.OnFireStoreTaskComplete {

    private final FireStoreAircraftRepository fireStoreRepository =
            new FireStoreAircraftRepository(this);
    private final MutableLiveData<List<Aircraft>> aircraftListViewModel = new MutableLiveData<>();

    public LiveData<List<Aircraft>> getAircraftListViewModel() {
        return aircraftListViewModel;
    }

    public AircraftListViewModel() {
        fireStoreRepository.getAircraftData();
    }

    @Override
    public void aircraftListDataAdded(List<Aircraft> aircraftList) {
        aircraftListViewModel.setValue(aircraftList);
    }

    @Override
    public void removerAircraft(Aircraft aircraft) {
        fireStoreRepository.removeAircraft(aircraft);
    }

    @Override
    public void onError(Exception e) {

    }
}
