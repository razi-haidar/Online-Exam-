package com.dataflair.smartonlineexam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dataflair.smartonlineexam.MainActivity;
import com.dataflair.smartonlineexam.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity {

    TextView marksTxt;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Accessing the marks from previous activity using key
        Bundle extras = getIntent().getExtras();
        String marks = extras.getString("marks");


        marksTxt = (TextView) findViewById(R.id.Markstxt);
        //hashmap to store the marks init
        HashMap<String, Object> marks_details = new HashMap<>();
        marks_details.put("marks", marks);

        //Getting the user id from Googlesignin
        String userId = GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getId();

        //updating the user marks in firebase
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).updateChildren(marks_details);

        //Showing the marks to the user in Textview
        marksTxt.setText(marks);
    }

    @Override
    public void onBackPressed() {
        //onBackpress user should navigate to the MainActivity.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}