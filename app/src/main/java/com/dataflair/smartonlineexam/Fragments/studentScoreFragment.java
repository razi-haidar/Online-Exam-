package com.dataflair.smartonlineexam.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dataflair.smartonlineexam.Adapters.NotificationAdapter;
import com.dataflair.smartonlineexam.Adapters.ScoreAdapter;
import com.dataflair.smartonlineexam.Model.Model;
import com.dataflair.smartonlineexam.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * In this Fragment we will show the score of individual user
 * we will use RecyclerView to show the Students Score
 */
public class studentScoreFragment extends Fragment {

    RecyclerView scoreRecyclerView;
    ScoreAdapter scoreAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public studentScoreFragment() {
        // Required empty public constructor
    }

    public static studentScoreFragment newInstance(String param1, String param2) {
        studentScoreFragment fragment = new studentScoreFragment();
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
       View view= inflater.inflate(R.layout.fragment_student_score, container, false);

        //Assigning the Recyclerview to diapsly notificaitons
        scoreRecyclerView = (RecyclerView) view.findViewById(R.id.StudentScoreRecyclerView);
        scoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Model.class)
                        .build();



        scoreAdapter = new ScoreAdapter(options);
        scoreRecyclerView.setAdapter(scoreAdapter);

       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        scoreAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
        scoreAdapter.stopListening();
    }
}