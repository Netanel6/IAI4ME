package com.netanel.iaiforme.shared.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.actions.ActionsManagerFragment;
import com.netanel.iaiforme.pojo.Noti;
import com.netanel.iaiforme.pojo.User;
import com.netanel.iaiforme.shared.EnterExitFragment;
import com.netanel.iaiforme.worker.fragments.actions.ActionsWorkerFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private BottomSheetBehavior mBottomSheetBehavior;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ProgressBar uploadPicProgress;
    private Uri imageFbUri;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("UsersProfilePic");
    private CollectionReference uploadPicFireStoreRef = FirebaseFirestore.getInstance().collection("Users");
    User currentUser;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();
    CollectionReference userName = FirebaseFirestore.getInstance().collection("Users");
    ImageView profileImage;
    TextView tvUserName;
    Button setProfilePicBtn;
    ImageView selectedPicImageView;
    Button  actionsBtn , enterExitBtn;

    private ManagerNotiAdapter managerNotiAdapter = new ManagerNotiAdapter();
    RecyclerView rvManagerNoti;
    private CollectionReference managerNotiRef = db.collection("Fcm");

    private CollectionReference userRef = db.collection("Users");

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkUserStatus();
        setUpViews(view);
        setBottomSheetProfilePicture(view);
        getCurrentUserInfo();
        setUpManagerNotiRecyclerView(view);


        enterExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBetweenFragment(new EnterExitFragment());
            }
        });

    }

    public void setUpViews(View view) {
        tvUserName = view.findViewById(R.id.tv_name_profile);
        profileImage = view.findViewById(R.id.profile_image);
        actionsBtn = view.findViewById(R.id.actions_btn);
        enterExitBtn = view.findViewById(R.id.enter_exit_btn);
    }


    //Open bottom sheet and set profile picture
    public void setBottomSheetProfilePicture(View view) {
        selectedPicImageView = view.findViewById(R.id.profile_pic_select);
        LinearLayout mCustomBottomSheet = view.findViewById(R.id.custom_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mCustomBottomSheet);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "העלאת תמונה", Toast.LENGTH_SHORT).show();
                if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
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

        setProfilePicBtn = view.findViewById(R.id.set_profile_pic);
        uploadPicProgress = view.findViewById(R.id.upload_picture_progress_bar);
        setProfilePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPic();
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
                    .transform(new RoundedCornersTransformation(200, 0, RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT))
                    .into(selectedPicImageView);

        }
    }


    //Upload picture to FireBase Storage & update URL in current user that logged in
    private void uploadPic() {

        if (imageFbUri != null) {
            StorageReference fileRef = storageReference.child(userid);
            fileRef.putFile(imageFbUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            uploadPicProgress.setProgress(0);
                                        }
                                    }, 3000);

                                    handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                        }
                                    }, 1000);
                                    Toast.makeText(getContext(), "תמונה הועלתה בהצלחה", Toast.LENGTH_SHORT).show();

                                    uploadPicFireStoreRef.document(userid).update("profilePicUrl", uri.toString());

                                    changeBetweenFragment(new ProfileFragment());
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    uploadPicProgress.setProgress((int) progress);
                    uploadPicProgress.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(getContext(), "לא נבחרה תמונה", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCurrentUserInfo() {

        //User name
        userName.document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
                tvUserName.setText(currentUser.getName() + " " + currentUser.getLast());

                //User Image
                Picasso
                        .get()
                        .load(currentUser.getProfilePicUrl())
                        .transform(new RoundedCornersTransformation(200, 0, RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT))
                        .into(profileImage);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Notifications recycler view
    public void setUpManagerNotiRecyclerView(View view) {
        rvManagerNoti = view.findViewById(R.id.rv_notifications);
        managerNotiRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Noti> noti = queryDocumentSnapshots.toObjects(Noti.class);
                rvManagerNoti.setLayoutManager(new LinearLayoutManager(getActivity(),
                        RecyclerView.VERTICAL, false));
                managerNotiAdapter.setNotiArrayList(noti);
                rvManagerNoti.setAdapter(managerNotiAdapter);
                DividerItemDecoration itemDecoration2 = new DividerItemDecoration(rvManagerNoti.getContext(),
                        DividerItemDecoration.VERTICAL);
                rvManagerNoti.addItemDecoration(itemDecoration2);
                managerNotiAdapter.notifyDataSetChanged();
            }
        });
    }

    //Check user status (Worker or Manager)
    public void checkUserStatus(){
        userRef.document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user.getStatus().equals("Worker")){

                    actionsBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            changeBetweenFragment(new ActionsWorkerFragment());
                        }
                    });
                }else if (user.getStatus().equals("Manager")){

                    actionsBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            changeBetweenFragment(new ActionsManagerFragment());
                        }
                    });
                }

            }
        });
    }

    //Changes between fragments
    public void changeBetweenFragment(Fragment selectedFragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
        fragmentTransaction.commit();
    }

}
