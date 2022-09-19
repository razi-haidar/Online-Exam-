package com.dataflair.smartonlineexam;
/*
 *   This Activity is used to load all the fragments and to show the data in fragments
 *   In this Activity we use Framelayout to show the Fragments  and
 *   BottomNavigation for navigation between fragments
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dataflair.smartonlineexam.Activities.AdminActivity;
import com.dataflair.smartonlineexam.Activities.LoginActivity;
import com.dataflair.smartonlineexam.Activities.UserRoleActivity;
import com.dataflair.smartonlineexam.Fragments.ExamFragment;
import com.dataflair.smartonlineexam.Fragments.HomeFragment;
import com.dataflair.smartonlineexam.Fragments.ProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning framelayout resource file to show appropriate fragment using address
        frameLayout = (FrameLayout) findViewById(R.id.FragmentContainer);
        //Assigining Bottomnavigaiton Menu
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationView);
        Menu menuNav = bottomNavigationView.getMenu();
        //Setting the default fragment as HomeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new HomeFragment()).commit();
        //Calling the bottoNavigationMethod when we click on any menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //checking user already logined or not
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser==null)
        {
            //if user not signed in then we will redirect this activity to LoginActivity
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            //Checks for user Role and starts the appropriate activity
            String id = GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getId();
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users").child(id).child("role");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String data=snapshot.getValue().toString();
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();

                    if(data.equals("student"))
                    {
                        Intent Facultyintent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(Facultyintent);
                        finish();

                    }
                    if(data.equals("admin"))
                    {
                        Intent Facultyintent=new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(Facultyintent);
                        finish();

                    }else{
                        Intent Facultyintent=new Intent(getApplicationContext(), UserRoleActivity.class);
                        startActivity(Facultyintent);
                        finish();
                    }


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                    //Assigining Fragment as Null
                    Fragment fragment = null;
                    switch (item.getItemId()) {

                        //Shows the Appropriate Fragment by using id as address
                        case R.id.home_menu:
                            fragment = new HomeFragment();
                            break;
                        case R.id.exam_menu:
                            fragment = new ExamFragment();
                            break;
                        case R.id.profile_menu:
                            fragment = new ProfileFragment();
                            break;

                    }
                    //Sets the selected Fragment into the Framelayout
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, fragment).commit();
                    return true;
                }
            };



}

