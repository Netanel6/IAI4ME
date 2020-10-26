package com.netanel.iaiforme.manager.fragments.actions;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.actions.ac_managment.AcManagementActivity;
import com.netanel.iaiforme.manager.fragments.actions.send_fcm.SendFcmActivity;
import com.netanel.iaiforme.pojo.User;
import com.netanel.iaiforme.worker.fragments.actions.send_request.SendRequestActivity;

public class ActionsManagerFragment extends Fragment implements View.OnClickListener {

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String userid = user.getUid();
    Button acManagmentBtn, sendFcmBtn , sendRequestBtn;
    TextView tvName;
    private CollectionReference userNameRef = FirebaseFirestore.getInstance().collection("Users");

    public ActionsManagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actions_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        getUserName(view);
    }

    //Setup views
    public void setupViews(View view){
        acManagmentBtn = view.findViewById(R.id.manage_ac_btn);
        sendFcmBtn = view.findViewById(R.id.send_fcm_btn);
        sendRequestBtn = view.findViewById(R.id.send_request_btn);
        acManagmentBtn.setOnClickListener(this);
        sendFcmBtn.setOnClickListener(this);
        sendRequestBtn.setOnClickListener(this);
    }

    //Implementation of OnClickListener
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manage_ac_btn:
                goToClass(AcManagementActivity.class);
                break;
            case R.id.send_fcm_btn:
                goToClass(SendFcmActivity.class);
                break;
            case R.id.send_request_btn:
                goToClass(SendRequestActivity.class);
                break;
        }
    }

    //Retrieve current logged in users' name
    public void getUserName(View view){
        tvName = view.findViewById(R.id.tv_name);
        userNameRef.document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                tvName.setText(user.getName() + " " + user.getLast());
            }
        });
    }

    public void goToClass(Class goTo) {
        Intent intent = new Intent(getActivity(), goTo);
        startActivity(intent);

    }

}
