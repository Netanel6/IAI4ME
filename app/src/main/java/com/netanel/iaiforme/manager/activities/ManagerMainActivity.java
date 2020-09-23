package com.netanel.iaiforme.manager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.lists.users.WorkerListsActivity;
import com.netanel.iaiforme.signup_signin.SigninActivity;
import com.netanel.iaiforme.shared.home.HomeFragment;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked.AllUserListsAdapter;
import com.netanel.iaiforme.shared.ProfileFragment;

public class ManagerMainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    AllUserListsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_manager_main);

        setUpBottomNavigationView();


    }



    //Bottom navigationView
    public void setUpBottomNavigationView() {

        //Bottom navigation
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment selectedFragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.nav_profile:
                                selectedFragment = new ProfileFragment();
                                break;
                            case R.id.nav_home_manager:
                                selectedFragment = new HomeFragment();
                                break;
                            case R.id.nav_lists:
                                Intent intent = new Intent(ManagerMainActivity.this , WorkerListsActivity.class);
                                startActivity(intent);
                                break;
                        }

                        if (selectedFragment != null) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, selectedFragment)
                                    .commit();
                        }

                        return true;

                    }
                };


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_manager_activity);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_home_manager);

    }

    //Menu inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager_profile_menu_without_search, menu);

        return true;
    }

    //Options - upper left
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.disconnect) {
            disconnect();
        }
        return true;
    }

    //Disconnect from app
    public void disconnect() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ManagerMainActivity.this, SigninActivity.class);
        startActivity(intent);
        firebaseAuth.signOut();
        Toast.makeText(this, "התנתקת מהמערכת!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "לחץ על הפינה השמאלית העליונה כדי להתנתק", Toast.LENGTH_SHORT).show();


    }
}
