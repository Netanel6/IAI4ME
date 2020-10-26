package com.netanel.iaiforme.manager.fragments.lists.users.fragments.users_in_illness_or_vacation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.netanel.iaiforme.R;

public class IllnessVacationWorkerFragment extends Fragment {
    // TODO: 20/10/2020 Reduce from allUsers to AvailableUsers
    public IllnessVacationWorkerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_illness_vacation_worker, container, false);
    }
}