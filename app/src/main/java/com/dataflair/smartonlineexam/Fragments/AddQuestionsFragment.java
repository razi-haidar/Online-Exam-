package com.dataflair.smartonlineexam.Fragments;
/**
 * In this Fragment we take Question and answers and we will update it to the firebase
 */

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
import com.dataflair.smartonlineexam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AddQuestionsFragment extends Fragment {

    EditText questionNumberEditTxt, questionEditTxt, option1EditTxt, option2EditTxt, option3EditTxt, option4EditTxt, correctAnswerEditTxt;
    Button addQuestionBtn;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AddQuestionsFragment() {
        // Required empty public constructor
    }

    public static AddQuestionsFragment newInstance(String param1, String param2) {
        AddQuestionsFragment fragment = new AddQuestionsFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_questions, container, false);

        questionNumberEditTxt = (EditText) view.findViewById(R.id.QuestionNumberEditTxt);
        questionEditTxt = (EditText) view.findViewById(R.id.QuestionEditTxt);
        option1EditTxt = (EditText) view.findViewById(R.id.Option1EditTxt);
        option2EditTxt = (EditText) view.findViewById(R.id.Option1EditTxt);
        option3EditTxt = (EditText) view.findViewById(R.id.Option1EditTxt);
        option4EditTxt = (EditText) view.findViewById(R.id.Option1EditTxt);
        correctAnswerEditTxt = (EditText) view.findViewById(R.id.AnswerEditTxt);

        addQuestionBtn = (Button) view.findViewById(R.id.AddQuestinsBtn);

        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Gets The string from the editText
                String QuestionNumber = questionNumberEditTxt.getText().toString();
                String Question = questionEditTxt.getText().toString();
                String Option1 = option1EditTxt.getText().toString();
                String Option2 = option2EditTxt.getText().toString();
                String Option3 = option3EditTxt.getText().toString();
                String Option4 = option4EditTxt.getText().toString();
                String Answer = correctAnswerEditTxt.getText().toString();

                if (QuestionNumber.isEmpty() || Question.isEmpty() || Option1.isEmpty() || Option2.isEmpty() || Option3.isEmpty() || Option4.isEmpty() || Answer.isEmpty()) {
                    Toast.makeText(getContext(), "Please,Fill all the details", Toast.LENGTH_SHORT).show();
                }else {

                    Add_Question_Method(QuestionNumber,Question,Option1,Option2,Option3,Option4,Answer);
                }
            }
        });

        return view;
    }

    private void Add_Question_Method(String questionNumber, String question, String option1, String option2, String option3, String option4, String answer) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("questions");

        //Hashmap to store the Question details and setting it to fireabse
        HashMap<String, Object> user_details = new HashMap<>();

        user_details.put("question", question);
        user_details.put("option1", option1);
        user_details.put("option2", option2);
        user_details.put("option3", option3);
        user_details.put("option4", option4);
        user_details.put("answer", answer);


        //updating the Questions in firebase
        myRef.child(questionNumber).updateChildren(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(),"Question added Successfully",Toast.LENGTH_SHORT).show();

                    questionNumberEditTxt.setText("");
                    questionEditTxt.setText("");
                    option1EditTxt.setText("");
                    option2EditTxt.setText("");
                    option3EditTxt.setText("");
                    option4EditTxt.setText("");
                    correctAnswerEditTxt.setText("");
                }
            }
        });
    }
}