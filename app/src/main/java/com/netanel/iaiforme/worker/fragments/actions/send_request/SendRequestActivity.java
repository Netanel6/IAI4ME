package com.netanel.iaiforme.worker.fragments.actions.send_request;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.shared.illness_vacation.IllnessFragment;
import com.netanel.iaiforme.shared.illness_vacation.VacationFragment;

public class SendRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);


        //Bottom navigation
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment selectedFragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.nav_vacation:
                                selectedFragment = new VacationFragment();
                                break;
                            case R.id.nav_illness:
                                selectedFragment = new IllnessFragment();
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

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_send_request);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
//        bottomNav.setSelectedItemId(R.id.nav_vacation);

    }


}