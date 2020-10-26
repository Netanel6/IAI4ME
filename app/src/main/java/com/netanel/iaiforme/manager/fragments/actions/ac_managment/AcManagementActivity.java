package com.netanel.iaiforme.manager.fragments.actions.ac_managment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.actions.ac_managment.fragments.AddAcFragment;
import com.netanel.iaiforme.manager.fragments.actions.ac_managment.fragments.UpdateDeleteAcFragment;

public class AcManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_ac_managment);
        setUpBottomNavigationView();
    }

    //Bottom navigationView
    private void setUpBottomNavigationView() {
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                menuItem -> {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_add_ac:
                            selectedFragment = new AddAcFragment();
                            break;
                        case R.id.nav_delete_ac:
                            selectedFragment = new UpdateDeleteAcFragment();
                            break;
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }
                    return true;
                };
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_ac_managment);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_add_ac);
    }
}