package com.dataflair.smartonlineexam.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dataflair.smartonlineexam.Activities.AdminActivity;
import com.dataflair.smartonlineexam.Activities.UserRoleActivity;
import com.dataflair.smartonlineexam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AddNotificationFragment extends Fragment {


    EditText notificationNumber,notificationTitle,notificationDescription;
    Button addNotificationBtn;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AddNotificationFragment() {
        // Required empty public constructor
    }

    public static AddNotificationFragment newInstance(String param1, String param2) {
        AddNotificationFragment fragment = new AddNotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_notification, container, false);

        notificationNumber=(EditText)view.findViewById(R.id.NotificationNumberEditTxt);
        notificationTitle=(EditText)view.findViewById(R.id.NotificationTitleEditTxt);
        notificationDescription=(EditText)view.findViewById(R.id.NotificationDescriptionEditTxt);

        addNotificationBtn=(Button)view.findViewById(R.id.AddNotificationBtn);

        addNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NotificaitonNumber=notificationNumber.getText().toString();
                String NotificationTitle=notificationTitle.getText().toString();
                String NotificaitonDescription=notificationDescription.getText().toString();

                if(NotificaitonNumber.isEmpty() || NotificationTitle.isEmpty() || NotificaitonDescription.isEmpty()){
                    Toast.makeText(view.getContext(),"Please,Enter all the details",Toast.LENGTH_SHORT).show();
                }else{
                    Add_Notificaiton_Method(NotificaitonNumber,NotificationTitle,NotificaitonDescription);
                }
            }
        });


        return view;
    }

    private void Add_Notificaiton_Method(String notificaitonNumber, String notificationTitle, String notificaitonDescription) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("notification");

        //Hashmap to store the userdetails and setting it to fireabse
        HashMap<String, Object> user_details = new HashMap<>();




        user_details.put("notificationDesc", notificaitonDescription);
        user_details.put("notificationTitle", notificationTitle);


        //updating the user details in firebase
        myRef.child(notificaitonNumber).updateChildren(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {


                    //navigating to the main activity after user successfully add Notification
                    Intent intent = new Intent(getContext(), AdminActivity.class);
                    //Clears older activities and tasks
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

    }
}