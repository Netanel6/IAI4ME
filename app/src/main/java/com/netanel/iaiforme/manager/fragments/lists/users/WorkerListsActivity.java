package com.netanel.iaiforme.manager.fragments.lists.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users.AvailableUserListsAdapter;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users.AvailableWorkersFragment;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.users_in_illness_or_vacation.IllnessVacationWorkerFragment;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked.AllUserListsAdapter;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked.AllWorkerListsFragment;
import com.netanel.iaiforme.signup_signin.SigninActivity;


public class WorkerListsActivity extends AppCompatActivity {
   private final AllUserListsAdapter allUsersAdapter = new AllUserListsAdapter();
    private final AvailableUserListsAdapter availableUsersAdapter = new AvailableUserListsAdapter();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_lists);
        showBackBtn();
        getSupportActionBar().setTitle("רשימות");
        setupUpperNavigationView();
    }

    public void showBackBtn(){

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
            if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
            }
        });
    }

    //Menu inflater + Search item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager_profile_menu_with_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                allUsersAdapter.getFilter().filter(newText);
                availableUsersAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    //upper navigationView
    public void setupUpperNavigationView() {

        //Bottom navigation
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                menuItem -> {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_available:
                            selectedFragment = new AvailableWorkersFragment();
                            break;
                        case R.id.nav_illness_vacation:
                            selectedFragment = new IllnessVacationWorkerFragment();
                            break;
                        case R.id.nav_lists_general:
                            selectedFragment = new AllWorkerListsFragment();
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

        BottomNavigationView bottomNav = findViewById(R.id.upper_nav_worker_list);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_lists_general);
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
        Intent intent = new Intent(WorkerListsActivity.this, SigninActivity.class);
        startActivity(intent);
        firebaseAuth.signOut();
        Toast.makeText(this, "התנתקת מהמערכת!", Toast.LENGTH_SHORT).show();
        finish();
    }

}