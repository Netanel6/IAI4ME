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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users.AvailableUserListViewModel;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users.AvailableUserListsAdapter;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.available_users.AvailableWorkersFragment;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.users_in_illness_or_vacation.IllnessVacationWorkerFragment;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked.AllUserListViewModel;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked.AllUserListsAdapter;
import com.netanel.iaiforme.manager.fragments.lists.users.fragments.all_users_before_checked.AllWorkerListsFragment;
import com.netanel.iaiforme.pojo.User;
import com.netanel.iaiforme.signup_signin.SigninActivity;

import java.util.List;

public class WorkerListsActivity extends AppCompatActivity {
    AllUserListsAdapter allUsersAdapter;
    AvailableUserListsAdapter availableUsersAdapter;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onStart() {
        super.onStart();

        setUpViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_lists);
        getSupportActionBar().setTitle("רשימות");
        setUpUpperNavigationView();
        setUpViewModel();

    }


    //ViewModels of All , Avaialble , IllenssVacation fragments
    public void setUpViewModel() {
        AllUserListViewModel allUserListViewModel = new ViewModelProvider(this).get(AllUserListViewModel.class);
        allUserListViewModel.getUserListViewModel().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                allUsersAdapter = new AllUserListsAdapter(userList);
            }
        });

        AvailableUserListViewModel availableUserListViewModel = new ViewModelProvider(this).get(AvailableUserListViewModel.class);
        availableUserListViewModel.getUserListViewModel().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                availableUsersAdapter = new AvailableUserListsAdapter(userList);
            }
        });
    }


    //Menu inflater
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
    public void setUpUpperNavigationView() {

        //Bottom navigation
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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

                    }
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