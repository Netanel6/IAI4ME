package com.netanel.iaiforme.worker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.User;
import com.netanel.iaiforme.shared.profile.ProfileFragment;
import com.netanel.iaiforme.shared.home.HomeFragment;
import com.netanel.iaiforme.signup_signin.SigninActivity;
import com.netanel.iaiforme.worker.fragments.day_schedule.DayScheduleFragment;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class WorkerMainActivity extends AppCompatActivity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userid = user.getUid();
    private CollectionReference userNameRef = FirebaseFirestore.getInstance().collection("Users");
    TextView userNameTv, personalNumberTv;
    NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_main);

        setUpNavDrawer();
        setUpFirstFrag();
        getUserName();
    }


    public void setUpNavDrawer() {

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home_worker:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_day_schedule:
                        selectedFragment = new DayScheduleFragment();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.nav_logout:
                        moveFromTo(SigninActivity.class);
                        FirebaseAuth.getInstance().signOut();
                        break;


                }

                if (selectedFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    drawerLayout.closeDrawers();

                }
                return true;
            }
        });

    }

    public void setUpFirstFrag() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    //Retrieve current logged in users' name
    public void getUserName() {

        View header = navigationView.getHeaderView(0);
        userNameTv = header.findViewById(R.id.user_name_nav_header);
        ImageView workerProfilePic = header.findViewById(R.id.worker_profile_pic);
        userNameRef.document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                userNameTv.setText(user.getName() + " " + user.getLast());

                Picasso
                        .get()
                        .load(user.getProfilePicUrl())
                        .transform(new RoundedCornersTransformation(200, 0, RoundedCornersTransformation.CornerType.ALL))
                        .into(workerProfilePic);
            }
        });
    }

    public void moveFromTo(Class toClass) {
        Intent intent = new Intent(WorkerMainActivity.this, toClass);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {

        Toast.makeText(this, "לחץ על הפינה הימנית העליונה כדי להתנתק", Toast.LENGTH_SHORT).show();
    }

}