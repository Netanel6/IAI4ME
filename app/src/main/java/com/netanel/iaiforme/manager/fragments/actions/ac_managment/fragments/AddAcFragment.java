package com.netanel.iaiforme.manager.fragments.actions.ac_managment.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.Aircraft;

import java.util.Calendar;


public class AddAcFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference aircraftRef = db.collection("AircraftList");
    private EditText etAcName, etAcModel;
    Button btnAddAc, btnDatePicker;
    TextView tvSetDate;
    private String exitDate;

    public AddAcFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_ac, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
        addAcToList();


    }

    //Views setup
    public void setUpViews(View view) {
        etAcName = view.findViewById(R.id.et_ac_name);
        etAcModel = view.findViewById(R.id.et_ac_model);
        btnAddAc = view.findViewById(R.id.add_ac_btn);
        btnDatePicker = view.findViewById(R.id.date_picker_ac_m);
        tvSetDate = view.findViewById(R.id.date_set_tv);
    }

    //Add aircraft to the list
    public void addAcToList() {

        btnAddAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acName = etAcName.getText().toString();
                String acModel = etAcModel.getText().toString();

                Aircraft aircraft = new Aircraft(acName, acModel, exitDate);
                aircraftRef.add(aircraft).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        String id = documentReference.getId();
                        aircraftRef.document(id).update("id" , id);
                    }
                });
                Toast.makeText(getContext(), "פרטי מטוס נוספו בהצלחה!", Toast.LENGTH_SHORT).show();
                etAcName.setText("");
                etAcModel.setText("");
                tvSetDate.setText("");
            }

        });


        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    //Initialize datePicker
    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        exitDate = dayOfMonth + "." + (month + 1) + "." + year;
        btnDatePicker.setText("בחר תאריך חדש");
        tvSetDate.setText("תאריך היציאה למטוס שנבחר הוא: " + exitDate);
    }


}