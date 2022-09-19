package com.dataflair.smartonlineexam.Activities;

/*
 *   This Activity is used to load all the fragments and to show the data in fragments
 *   In this Activity we use FrameLayout to show the Fragments  and
 *   BottomNavigation for navigation between fragments
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dataflair.smartonlineexam.Fragments.AddNotificationFragment;
import com.dataflair.smartonlineexam.Fragments.AddQuestionsFragment;
import com.dataflair.smartonlineexam.Fragments.AdminProfileFragment;
import com.dataflair.smartonlineexam.Fragments.ExamFragment;
import com.dataflair.smartonlineexam.Fragments.HomeFragment;
import com.dataflair.smartonlineexam.Fragments.ProfileFragment;
import com.dataflair.smartonlineexam.Fragments.studentScoreFragment;
import com.dataflair.smartonlineexam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Assigning framelayout resource file to show appropriate fragment using address
        frameLayout = (FrameLayout) findViewById(R.id.FragmentContainerAdmin);
        //Assigining Bottomnavigaiton Menu
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationViewAdmin);
        Menu menuNav = bottomNavigationView.getMenu();
        //Setting the default fragment as HomeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainerAdmin, new AddNotificationFragment()).commit();
        //Calling the bottoNavigationMethod when we click on any menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                    //Assigining Fragment as Null
                    Fragment fragment = null;
                    switch (item.getItemId()) {

                        //Shows the Appropriate Fragment by using id as address
                        case R.id.AddNotificationsMenu:
                            fragment = new AddNotificationFragment();
                            break;
                        case R.id.AddQuestionsMenu:
                            fragment = new AddQuestionsFragment();
                            break;
                        case R.id.StudentsScoreMenu:
                            fragment = new studentScoreFragment();
                            break;

                        case R.id.AdminProifleMenu:
                            fragment = new AdminProfileFragment();
                            break;

                    }
                    //Sets the selected Fragment into the Framelayout
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainerAdmin, fragment).commit();
                    return true;
                }
            };



}