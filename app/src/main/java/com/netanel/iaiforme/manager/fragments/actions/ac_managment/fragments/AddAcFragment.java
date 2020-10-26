package com.netanel.iaiforme.manager.fragments.actions.ac_managment.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.Aircraft;
import com.squareup.picasso.Picasso;
import java.util.Calendar;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import static android.app.Activity.RESULT_OK;


public class AddAcFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference aircraftRef = db.collection("AircraftList");
    private Aircraft aircraft = new Aircraft();
    private EditText etAcName, etAcModel;
    Button btnAddAc, btnDatePicker, btnAddPaka, btnSavePaka;
    TextView tvSetDate;
    private String exitDate;
    ImageView selectedPakaImageView;
    private BottomSheetBehavior<LinearLayout> mBottomSheetBehavior;
    private ProgressBar uploadPakaProgressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageFbUri;
    private final StorageReference storagePakaReference = FirebaseStorage.getInstance().getReference("AircraftPaka");
    private final CollectionReference uploadPicFireStoreRef = FirebaseFirestore.getInstance().collection("AircraftList");

    public AddAcFragment() {
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
        uploadPic();
        setDateAndAcBtn();
        setBottomSheetPaka(view);
    }

    public void setDateAndAcBtn() {
        btnAddAc.setOnClickListener(this::addAcToList);
        btnDatePicker.setOnClickListener(v -> showDatePickerDialog());
    }

    //Views setup
    public void setUpViews(View view) {
        etAcName = view.findViewById(R.id.et_ac_name);
        etAcModel = view.findViewById(R.id.et_ac_model);
        btnDatePicker = view.findViewById(R.id.date_picker_ac_m);
        tvSetDate = view.findViewById(R.id.date_set_tv);
        btnAddPaka = view.findViewById(R.id.add_paka_ac_m);
        btnSavePaka = view.findViewById(R.id.set_ac_paka);
        uploadPakaProgressBar = view.findViewById(R.id.upload_paka_progress_bar);
        btnAddAc = view.findViewById(R.id.add_ac_btn);
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

    //Open bottom sheet and set profile picture
    public void setBottomSheetPaka(View view) {
        selectedPakaImageView = view.findViewById(R.id.paka_select_image_view);
        LinearLayout mCustomBottomSheet = view.findViewById(R.id.custom_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mCustomBottomSheet);
        btnAddPaka.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "העלאת פק''ע", Toast.LENGTH_SHORT).show();
            if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    openFileChooser();
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    //Open Android file chooser
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //Retrieves file from Android file chooser
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            imageFbUri = data.getData();

            Picasso
                    .get()
                    .load(imageFbUri)
                    .transform(new RoundedCornersTransformation(200, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(selectedPakaImageView);
        }
    }

    //Upload picture to FireBase Storage & update URL in current user that logged in
    private void uploadPic() {
        btnSavePaka.setOnClickListener(v -> {
            if (imageFbUri != null) {
                StorageReference fileRef = storagePakaReference.child(etAcName.getText().toString() + " " + etAcModel.getText().toString());
                fileRef.putFile(imageFbUri)
                        .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {

                            Handler handler = new Handler();
                            handler.postDelayed(() -> uploadPakaProgressBar.setProgress(0), 3000);

                            handler = new Handler();
                            handler.postDelayed(() -> mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED), 1000);
                            Toast.makeText(getContext(), "תמונה הועלתה בהצלחה", Toast.LENGTH_SHORT).show();

                            aircraft.setPaka(uri.toString());
                            uploadPicFireStoreRef.document().update("paka", aircraft.getPaka());

                        })).addOnFailureListener(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show()).addOnProgressListener(taskSnapshot -> {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadPakaProgressBar.setProgress((int) progress);
                            uploadPakaProgressBar.setVisibility(View.VISIBLE);
                        });
            } else {
                Toast.makeText(getContext(), "לא נבחרה תמונה", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Add aircraft to the list
    public void addAcToList(View v) {

        String acName = etAcName.getText().toString();
        String acModel = etAcModel.getText().toString();
        aircraft.setName(acName);
        aircraft.setModel(acModel);

        if (aircraft.getName() == null | aircraft.getModel() == null | exitDate == null) {
            Toast.makeText(getContext(), "לא הוכנסו אחד או יותר מפרטי המטוס", Toast.LENGTH_SHORT).show();
        } else {
            aircraft = new Aircraft(acName, acModel, exitDate, aircraft.getPaka());
            aircraftRef.add(aircraft).addOnSuccessListener(documentReference -> {

                String id = documentReference.getId();
                aircraftRef.document(id).update("id", id);
            });
            Snackbar snackbar = Snackbar.make(v,
                    "מטוס " + acName + " " + acModel + "התווסף בהצלחה לרשימה ", Snackbar.LENGTH_LONG);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.show();
            etAcName.setText("");
            etAcModel.setText("");
            tvSetDate.setText("");
        }


    }

}