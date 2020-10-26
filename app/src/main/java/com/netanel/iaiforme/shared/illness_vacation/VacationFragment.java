package com.netanel.iaiforme.shared.illness_vacation;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.DateFromTo;
import com.netanel.iaiforme.pojo.User;

import java.util.Calendar;

public class VacationFragment extends Fragment {
    TextView fromDate, toDate, titleSelectDate;
    Button sendVacationDays;
    private DatePickerDialog datePickerDialog;
    private String dateString;
    Calendar calendar;
    private String toDateString, fromDateString;
    private DateFromTo dateFromTo;
    private final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private final CollectionReference userRefVacation = FirebaseFirestore.getInstance().collection("Users");
    String userName = "";
    String userLast = "";
    private final DocumentReference userRefDocument = userRefVacation.document(uid);
    private final CollectionReference requestVacationRef = FirebaseFirestore.getInstance().collection("Vacation");

    public VacationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vacation, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        dateFromTo = new DateFromTo();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        pickDates();
    }

    public void setupViews(View view) {
        titleSelectDate = view.findViewById(R.id.title_select_date);
        fromDate = view.findViewById(R.id.tv_from_date);
        toDate = view.findViewById(R.id.tv_to_date);
        sendVacationDays = view.findViewById(R.id.send_vacation_btn);
    }

    //Initialize datePicker
    public void showDatePickerDialog() {
        datePickerDialog = new DatePickerDialog(
                getContext(),
                datePickerListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void pickDates() {
        fromDate.setOnClickListener(v -> {
            showDatePickerDialog();

            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "OK", (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            DatePicker datePicker = datePickerDialog
                                    .getDatePicker();
                            datePickerListener.onDateSet(datePicker,
                                    datePicker.getYear(),
                                    datePicker.getMonth(),
                                    datePicker.getDayOfMonth());
                            fromDate.setText("החופש יתחיל בתאריך: " + dateString);
                            dateFromTo.setDateFrom(dateString);
                            fromDateString = dateFromTo.getDateFrom();
                        }
                    });
        });
        toDate.setOnClickListener(v -> {
            showDatePickerDialog();
            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "OK", (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            DatePicker datePicker = datePickerDialog
                                    .getDatePicker();
                            datePickerListener.onDateSet(datePicker,
                                    datePicker.getYear(),
                                    datePicker.getMonth(),
                                    datePicker.getDayOfMonth());
                            toDate.setText("החופש יסתיים בתאריך: " + dateString);
                            dateFromTo.setDateTo(dateString);
                            toDateString = dateFromTo.getDateTo();
                        }
                    });
        });

        sendVacationDays.setOnClickListener(v -> {
            if (toDateString == null | fromDateString == null) {
                Toast.makeText(getContext(), "לא הוכנס אחד מהתאריכים!", Toast.LENGTH_SHORT).show();
            } else {
                Snackbar snackbar = Snackbar.make(v,
                        "בקשת חופשה נשלחה למנהל", Snackbar.LENGTH_LONG);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                userRefVacation.document(uid).get().addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    userName = user.getName();
                    userLast = user.getLast();

                    dateFromTo = new DateFromTo(uid, fromDateString, toDateString);
                    userRefDocument.collection("Vacation").document(uid).set(dateFromTo);
                    dateFromTo = new DateFromTo(uid, userName, userLast, fromDateString, toDateString);
                    requestVacationRef.document(uid).set(dateFromTo);
                });
                snackbar.show();
            }
        });
    }

    final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            titleSelectDate.setText("לחץ על אחד מהתאריכים שנבחרו כדי לבחור תאריך חדש");
            dateString = dayOfMonth + "." + (month + 1) + "." + year;
        }
    };
}