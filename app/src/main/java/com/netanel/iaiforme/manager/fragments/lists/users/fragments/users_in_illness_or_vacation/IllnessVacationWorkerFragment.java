package com.netanel.iaiforme.manager.fragments.lists.users.fragments.users_in_illness_or_vacation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netanel.iaiforme.R;

public class IllnessVacationWorkerFragment extends Fragment {

    public IllnessVacationWorkerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_illness_vacation_worker, container, false);
    }
}