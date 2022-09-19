package com.dataflair.smartonlineexam.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dataflair.smartonlineexam.Activities.ResultsActivity;
import com.dataflair.smartonlineexam.Model.Model;
import com.dataflair.smartonlineexam.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

/**
 * This fragment is to show the questions for test
 * We will show the question from the database in this fragment
 * we will add timer for the test
 */
public class ExamFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView timerTxt;
    TextView questionText;
    Button option1Btn, option2Btn, option3Btn, option4Btn, nextQuestionBtn, submitTestBtn;
    DatabaseReference databaseReference;
    int QuestinNumber = 1;
    int Marks = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExamFragment() {
        // Required empty public constructor
    }

    public static ExamFragment newInstance(String param1, String param2) {
        ExamFragment fragment = new ExamFragment();
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
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        questionText = (TextView) view.findViewById(R.id.Questiontxt);
        option1Btn = (Button) view.findViewById(R.id.Option1Btn);
        option2Btn = (Button) view.findViewById(R.id.Option2Btn);
        option3Btn = (Button) view.findViewById(R.id.Option3Btn);
        option4Btn = (Button) view.findViewById(R.id.Option4Btn);
        nextQuestionBtn = (Button) view.findViewById(R.id.NextQuestionBtn);
        submitTestBtn = (Button) view.findViewById(R.id.SubmitTestBtn);


        timerTxt = (TextView) view.findViewById(R.id.Timertxt);

        //method to update the questions
        update_question();

        //method to start timer
        startTimer();

        return view;
    }

    private void update_question() {

        //databaaseReference to get the path of questions in firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("questions").child(String.valueOf(QuestinNumber));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                Model QuestionsData = snapshot.getValue(Model.class);



                //setting the data in activity from firebase
                questionText.setText(QuestionsData.getQuestion());
                option1Btn.setText(QuestionsData.getOption1());
                option2Btn.setText(QuestionsData.getOption2());
                option3Btn.setText(QuestionsData.getOption3());
                option4Btn.setText(QuestionsData.getOption4());

                String Correctanswer = QuestionsData.getAnswer().toString().toLowerCase().trim();

                //setting the default color to gray
                option1Btn.setBackgroundColor(Color.GRAY);
                option2Btn.setBackgroundColor(Color.GRAY);
                option3Btn.setBackgroundColor(Color.GRAY);
                option4Btn.setBackgroundColor(Color.GRAY);

                //implementation of OnClickListener to check user clicked on correct answer or not
                option1Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String option1 = option1Btn.getText().toString().toLowerCase().trim();
                        if (option1.equals(Correctanswer)) {
                            //Increase the marks by 1 value and set other button colors as red
                            Marks = Marks + 1;
                            option1Btn.setBackgroundColor(Color.GREEN);
                            option2Btn.setBackgroundColor(Color.RED);
                            option3Btn.setBackgroundColor(Color.RED);
                            option4Btn.setBackgroundColor(Color.RED);

                        } else {
                            checkAnswer(Correctanswer);
                        }
                        //updates the next question
                        update_nextQuestion();
                    }
                });

                option2Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String option2 = option2Btn.getText().toString().toLowerCase().trim();
                        if (option2.equals(Correctanswer)) {
                            Marks = Marks + 1;
                            option1Btn.setBackgroundColor(Color.RED);
                            option2Btn.setBackgroundColor(Color.GREEN);
                            option3Btn.setBackgroundColor(Color.RED);
                            option4Btn.setBackgroundColor(Color.RED);
                        } else {
                            checkAnswer(Correctanswer);
                        }
                        update_nextQuestion();
                    }
                });


                option3Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String option3 = option3Btn.getText().toString().toLowerCase().trim();
                        if (option3.equals(Correctanswer)) {
                            Marks = Marks + 1;
                            option1Btn.setBackgroundColor(Color.RED);
                            option2Btn.setBackgroundColor(Color.RED);
                            option3Btn.setBackgroundColor(Color.GREEN);
                            option4Btn.setBackgroundColor(Color.RED);
                        } else {
                            checkAnswer(Correctanswer);
                        }

                        update_nextQuestion();

                    }
                });

                option4Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String option4 = option4Btn.getText().toString().toLowerCase().trim();
                        if (option4.equals(Correctanswer)) {
                            Marks = Marks + 1;
                            option1Btn.setBackgroundColor(Color.RED);
                            option2Btn.setBackgroundColor(Color.RED);
                            option3Btn.setBackgroundColor(Color.RED);
                            option4Btn.setBackgroundColor(Color.GREEN);
                        } else {
                            checkAnswer(Correctanswer);
                        }

                        update_nextQuestion();
                    }
                });

                nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        update_nextQuestion();
                    }
                });

                submitTestBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ResultsActivity.class);
                        String Score = String.valueOf(Marks);
                        intent.putExtra("marks", Score);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void update_nextQuestion() {
        //checks the current question number is less than last question or not
        if (QuestinNumber < 5) {
            QuestinNumber = QuestinNumber + 1;
            update_question();
        } else {
            Toast.makeText(getContext(), "Your Test is Completed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ResultsActivity.class);
            String Score = String.valueOf(Marks);
            System.out.println();
            intent.putExtra("marks", Score);
            startActivity(intent);
        }
    }

    private void checkAnswer(String CorrectAnswer) {

        //Access  text form all the  buttons
        String option1 = option1Btn.getText().toString().toLowerCase().trim();
        String option2 = option2Btn.getText().toString().toLowerCase().trim();
        String option3 = option3Btn.getText().toString().toLowerCase().trim();
        String option4 = option4Btn.getText().toString().toLowerCase().trim();

        //checking the text from button is equal to CorrectAnswer or not
        //if yes we set that button as green or else red
        if (option1.equals(CorrectAnswer)) {
            option1Btn.setBackgroundColor(Color.GREEN);
            option2Btn.setBackgroundColor(Color.RED);
            option3Btn.setBackgroundColor(Color.RED);
            option4Btn.setBackgroundColor(Color.RED);
        } else if (option2.equals(CorrectAnswer)) {
            option1Btn.setBackgroundColor(Color.RED);
            option2Btn.setBackgroundColor(Color.GREEN);
            option3Btn.setBackgroundColor(Color.RED);
            option4Btn.setBackgroundColor(Color.RED);
        } else if (option3.equals(CorrectAnswer)) {
            option1Btn.setBackgroundColor(Color.RED);
            option2Btn.setBackgroundColor(Color.RED);
            option3Btn.setBackgroundColor(Color.GREEN);
            option4Btn.setBackgroundColor(Color.RED);
        }
        if (option4.equals(CorrectAnswer)) {
            option1Btn.setBackgroundColor(Color.RED);
            option2Btn.setBackgroundColor(Color.RED);
            option3Btn.setBackgroundColor(Color.RED);
            option4Btn.setBackgroundColor(Color.GREEN);
        }
    }

    private void startTimer() {
        final CountDownTimer count = new CountDownTimer(60 * 1000 + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minute = seconds / 60;
                seconds = seconds % 60;
                if (seconds < 5) {
                    timerTxt.setTextColor(Color.RED);
                    timerTxt.setText(String.format("%02d", minute) + ":" + String.format("%02d", seconds));
                } else {
                    timerTxt.setText(String.format("%02d", minute) + ":" + String.format("%02d", seconds));
                    timerTxt.setTextColor(Color.GRAY);
                }

            }

            @Override
            public void onFinish() {
                //Toast to show time up message
                Toast.makeText(getContext(), "Time Up", Toast.LENGTH_SHORT);

                //Redirects the Activity to Results Activity after timeup
                Intent intent = new Intent(getContext(), ResultsActivity.class);
                String Score = String.valueOf(Marks);
                intent.putExtra("marks", Score);
                startActivity(intent);
            }

        }.start();
    }
}