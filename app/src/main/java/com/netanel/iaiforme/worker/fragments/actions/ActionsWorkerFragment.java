package com.netanel.iaiforme.worker.fragments.actions;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.User;
import com.netanel.iaiforme.worker.fragments.actions.send_request.SendRequestActivity;

public class ActionsWorkerFragment extends Fragment {
    Button sendRequestBtn;
    private final CollectionReference userName = FirebaseFirestore.getInstance().collection("Users");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String userid = user.getUid();
    TextView tvName;

    public ActionsWorkerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actions_worker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserName(view);
        sendRequestBtn = view.findViewById(R.id.send_request_btn);
        sendRequestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity() , SendRequestActivity.class);
            startActivity(intent);
        });
    }

    //Retrieve current logged in users' name
    public void getUserName(View view){
        tvName = view.findViewById(R.id.tv_name);
        userName.document(userid).get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            tvName.setText(user.getName() + " " + user.getLast());
        });
    }
}